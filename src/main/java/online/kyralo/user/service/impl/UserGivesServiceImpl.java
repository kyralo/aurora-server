package online.kyralo.user.service.impl;

import online.kyralo.user.dao.UserGivesMapper;
import online.kyralo.user.domain.UserGives;
import online.kyralo.user.service.UserGivesService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import static online.kyralo.common.constants.CommonConstants.*;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-30
 * \* Time: 16:40
 * \* Year: 2019
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserGivesServiceImpl implements UserGivesService {

    @Resource
    private UserGivesMapper mapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

//
//    @Override
//    public ResponseEntity<?> listsUserGives(String userId) {
//        if (userId == null) {
//            return ResponseEntity.badRequest().body("必要参数不能为空!");
//        } else {
//            List<GivesForComments> comments = mapper.listsUserGivesForComments(userId);
//            List<GivesForVideo> videos = mapper.listsUserGivesForVideo(userId);
//            Map<String, Object> map = new HashMap<>(2);
//            map.put("comments", comments);
//            map.put("videos", videos);
//
//            return ResponseEntity.ok(map);
//        }
//    }

    @Override
    public ResponseEntity<?> insert(UserGives userGives) {
        if (userGives.getUserId() == null || userGives.getType() == null || userGives.getTargetId() == null) {
            return ResponseEntity.badRequest().body("必要参数不能为空!");
        } else {
            if (mapper.insertSelective(userGives) == 1) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @Override
    synchronized public void redisGivesUpdate(UserGives userGives) {
        String hashKey = userGives.getType().equals(GIVES_MOVIE)
                ? GIVES_MOVIE_KEY_PREFIX + userGives.getId()
                : GIVES_COMMENTS_KEY_PREFIX + userGives.getId();
        try {
            redisTemplate.opsForHash().put("aurora_gives", hashKey, userGives);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull UserGives userGives) {
        String hashKey = userGives.getType().equals(GIVES_MOVIE)
                ? GIVES_MOVIE_KEY_PREFIX + userGives.getId()
                : GIVES_COMMENTS_KEY_PREFIX + userGives.getId();

        UserGives gives =
                (UserGives) redisTemplate.opsForHash().get("aurora_gives", hashKey);

        if (gives != null) {
            gives.setActionType(userGives.getActionType());
            redisGivesUpdate(gives);
        } else {
            UserGives gives1 =
                    mapper.selectByPrimaryKey(userGives.getId());
            if (gives1 != null) {
                gives1.setActionType(userGives.getActionType());
                redisGivesUpdate(gives1);
            }
        }
    }

}
