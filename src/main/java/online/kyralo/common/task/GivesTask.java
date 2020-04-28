package online.kyralo.common.task;

import online.kyralo.user.dao.UserGivesMapper;
import online.kyralo.user.domain.UserGives;
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
 * \* Date: 2020-04-19
 * \* Time: 9:35
 * \* Description: Gives 任务
 * \
 */

@Component
@EnableScheduling
public class GivesTask {

    private final Logger logger = LoggerFactory.getLogger(GivesTask.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private UserGivesMapper mapper;

    /**
     * 10分钟执行一次
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void updateGives(){

        Set<Object> keys = redisTemplate.opsForHash().keys("aurora_gives");

        keys.forEach(key -> {

            UserGives userGives =
                    (UserGives)redisTemplate.opsForHash().get("aurora_gives", key);

            if (userGives != null){
                UserGives build = UserGives.builder().
                        id(userGives.getId()).
                        actionType(userGives.getActionType()).
                        build();
                try {
                    mapper.updateByPrimaryKeySelective(build);
                }catch (Exception e){
                    logger.info(format.format(new Date()) +  " 点赞信息id: " + userGives.getId() + " 持有化到数据库出错");
                }
            }
        });
    }
}
