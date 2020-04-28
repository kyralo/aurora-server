package online.kyralo.user.service.impl;

import online.kyralo.user.dao.UserCommentMapper;
import online.kyralo.user.dao.UserGivesMapper;
import online.kyralo.user.domain.Comment;
import online.kyralo.user.domain.UserComment;
import online.kyralo.user.domain.UserGives;
import online.kyralo.user.service.UserCommentService;
import online.kyralo.user.service.UserGivesService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static online.kyralo.common.constants.CommonConstants.*;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:54
 * \* Year: 2019
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserCommentServiceImpl implements UserCommentService {

    @Resource
    private UserCommentMapper mapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserGivesMapper userGivesMapper;
    @Resource
    private UserGivesService userGivesService;

    private List<Comment> handlerCommentList(List<Comment> commentList, String userId){
        commentList.forEach(comment -> {

            UserComment comment1 =
                    (UserComment) redisTemplate.opsForHash().get("aurora_comments", comment.getId());

            if (comment1 != null){
                BeanUtils.copyProperties(comment1, comment);
            }else {
                UserComment userComment = new UserComment();
                BeanUtils.copyProperties(comment, userComment);
                redisTemplate.opsForHash().put("aurora_comments", comment.getId(), userComment);
            }

            comment.setAction(ACTION_NONE);

            if (userId != null){
                Example givesExample = new Example(UserGives.class);
                Example.Criteria givesCriteria = givesExample.createCriteria();
                givesCriteria.andEqualTo("targetId", comment.getId()).
                        andEqualTo("userId", userId).
                        andEqualTo("type", GIVES_COMMENTS);
                UserGives userGives =
                        userGivesMapper.selectOneByExample(givesExample);

                if (userGives != null){
                    String hashKey = GIVES_COMMENTS_KEY_PREFIX + userGives.getId();
                    // 缓存里的数据才是实时的
                    UserGives gives =
                            (UserGives)redisTemplate.opsForHash().get("aurora_gives", hashKey);
                    if (gives != null){
                        comment.setAction(gives.getActionType());
                    }else {
                        userGivesService.redisGivesUpdate(userGives);
                        comment.setAction(userGives.getActionType());

                        redisTemplate.opsForHash().put("aurora_gives", hashKey, userGives);

                    }
                }
            }

            comment.setLevel2CommentList(new ArrayList<>());

        });

        return commentList;

    }

    @Override
    public ResponseEntity<?> getLevel1Comments(String videoId, String userId) {
        List<Comment> commentList = mapper.listLevel1Comments(videoId);
        return ResponseEntity.ok(handlerCommentList(commentList, userId));
    }

    @Override
    public ResponseEntity<?> getLevel2Comments(String videoId, String ancestryId, String userId) {
        List<Comment> commentList= mapper.listLevel2Comments(videoId, ancestryId);
        return ResponseEntity.ok(handlerCommentList(commentList, userId));
    }

    /**
     * video_id,target_id, user_id, type, action_type
     */
    @Override
    public ResponseEntity<?> insert(UserComment userComment) {
        if ("".equals(userComment.getSendId()) && "".equals(userComment.getVideoId())) {
            return ResponseEntity.badRequest().body("信息不完整!");
        } else {
            if ("".equals(userComment.getCommentContent())) {
                return ResponseEntity.badRequest().body("评论内容不能为空!");
            } else {
                userComment.setId(UUID.randomUUID().toString().replace("-", ""));
                if (mapper.insertSelective(userComment) == 1) {
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        }
    }

    synchronized private void redisUpdate(UserComment userComment) {
        redisTemplate.opsForHash().put("aurora_comments", userComment.getId(), userComment);
    }

    private ResponseEntity<?> newComment(String commentId, String userId, Byte action){
        UserGives gives = UserGives.builder().actionType(action).targetId(commentId).
                userId(userId).actionType(action).type(GIVES_COMMENTS).build();
        if (userGivesMapper.insertSelective(gives) > 0) {

            userGivesService.redisGivesUpdate(gives);

            try {
                UserComment comment =
                        (UserComment) redisTemplate.opsForHash().get("aurora_comments", commentId);
                if (comment == null) {
                    comment = mapper.selectByPrimaryKey(commentId);
                }
                if (ACTION_LIKE.equals(action)) {
                    comment.setLikes(comment.getLikes() + 1);
                } else if (ACTION_DISLIKE.equals(action)) {
                    comment.setDislikes(comment.getDislikes() + 1);
                }
                redisUpdate(comment);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @Override
    public ResponseEntity<?> action(String commentId, String userId, Byte action) {
        Example example = new Example(UserGives.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("targetId", commentId).andEqualTo("type", GIVES_COMMENTS).andEqualTo("userId", userId);
        UserGives userGives = userGivesMapper.selectOneByExample(example);
        if (userGives == null) {
            return newComment(commentId, userId, action);
        }

        String hashKey = GIVES_COMMENTS_KEY_PREFIX + userGives.getId();
        UserGives redisUserGives =
                (UserGives)redisTemplate.opsForHash().get("aurora_gives", hashKey);

        if (redisUserGives != null){
            userGives = redisUserGives;
        }

        try {
            UserComment comment =
                    (UserComment) redisTemplate.opsForHash().get("aurora_comments", commentId);
            if (comment == null) {
                comment = mapper.selectByPrimaryKey(commentId);
            }
            if (ACTION_LIKE.equals(action)) {
                if (ACTION_LIKE.equals(userGives.getActionType())) {
                    comment.setLikes(Math.max(comment.getLikes() - 1, 0));
                    userGives.setActionType(ACTION_NONE);
                } else if (ACTION_DISLIKE.equals(userGives.getActionType())) {
                    comment.setLikes(comment.getLikes() + 1);
                    comment.setDislikes(Math.max(comment.getDislikes() - 1, 0));
                    userGives.setActionType(ACTION_LIKE);
                } else {
                    comment.setLikes(comment.getLikes() + 1);
                    userGives.setActionType(ACTION_LIKE);
                }
            } else if (ACTION_DISLIKE.equals(action)) {
                if (ACTION_DISLIKE.equals(userGives.getActionType())) {
                    comment.setDislikes(Math.max(comment.getDislikes() - 1, 0));
                    userGives.setActionType(ACTION_NONE);
                } else if (ACTION_LIKE.equals(userGives.getActionType())) {
                    comment.setDislikes(comment.getDislikes() + 1);
                    comment.setLikes(Math.max(comment.getLikes() - 1, 0));
                    userGives.setActionType(ACTION_DISLIKE);
                } else {
                    comment.setDislikes(comment.getDislikes() + 1);
                    userGives.setActionType(ACTION_DISLIKE);
                }
            }
            redisUpdate(comment);
            userGivesService.update(userGives);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
