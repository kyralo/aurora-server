package online.kyralo.common.task;

import online.kyralo.user.dao.UserCommentMapper;
import online.kyralo.user.domain.UserComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-04-18
 * \* Time: 9:32
 * \* Description: 评论的定时任务
 * \
 */

@Component
@EnableScheduling
public class CommentTask {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserCommentMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(CommentTask.class);

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 5分钟执行一次
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void updateComment() {
        Set<Object> keys = redisTemplate.opsForHash().keys("aurora_comments");
        keys.forEach(key -> {
            UserComment comment = (UserComment) redisTemplate.opsForHash().get("aurora_comments", key);
            if (comment != null) {
                UserComment userComment = UserComment.builder().
                        id(comment.getId()).
                        ancestryId(comment.getAncestryId()).
                        likes(comment.getLikes()).
                        dislikes(comment.getDislikes()).build();
                try{
                    mapper.updateByPrimaryKeySelective(userComment);
                }catch (Exception e){
                    logger.info(format.format(new Date()) + " 评论: " + comment.getId() + " 信息同步到数据库失败!");
                }
            }
        });
    }

}
