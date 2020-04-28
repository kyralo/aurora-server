package online.kyralo.common.task;

import online.kyralo.common.util.CommonUtil;
import online.kyralo.user.dao.UserCommentMapper;
import online.kyralo.user.domain.UserComment;
import online.kyralo.video.dao.VideoMapper;
import online.kyralo.video.domain.Video;
import online.kyralo.video.domain.VideoList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static online.kyralo.common.constants.VideoConstants.VIDEOS_HOT_KEY;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-04-18
 * \* Time: 9:34
 * \* Description: 视频相关的定时任务
 * \
 */

@Component
@EnableScheduling
public class VideoTask {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private VideoMapper mapper;
    @Resource
    private UserCommentMapper userCommentMapper;

    private final Logger logger = LoggerFactory.getLogger(VideoTask.class);

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 5分钟执行一次
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void updateVideo(){
        Set<Object> keys = redisTemplate.opsForHash().keys("aurora_videos");
        keys.forEach(key -> {
            try {
                VideoList videos =
                    (VideoList)redisTemplate.opsForHash().get("aurora_videos", key);
                if (videos != null){
                    Video video = Video.builder().
                            id(videos.getId()).
                            collections(videos.getCollections()).
                            dislikes(videos.getDislikes()).
                            likes(videos.getLikes()).build();
                        mapper.updateByPrimaryKeySelective(video);
                }
            }catch (Exception e){
                logger.info(format.format(new Date()) +  " 视频: " + key + " 信息同步到数据库失败!");
                e.printStackTrace();
            }
        });
    }

    /**
     * 1小时执行一次
     */
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void hotVideosPut(){
        List<VideoList> videos = mapper.listVideos();
        if (!videos.isEmpty()){
            videos.forEach(video -> {
                Example example = new Example(UserComment.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("videoId", video.getId());
                int count = userCommentMapper.selectCountByExample(example);

                double score = CommonUtil.scoreGenerate(video.getCreateTime(), video.getLikes(),
                        count, video.getDislikes(), video.getCollections());
                redisTemplate.opsForZSet().add(VIDEOS_HOT_KEY, video.getId(), score);
            });
        }

    }

}


