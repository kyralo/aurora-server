package online.kyralo.video.service.impl;

import online.kyralo.user.dao.UserCollectionMapper;
import online.kyralo.user.dao.UserGivesMapper;
import online.kyralo.user.domain.UserCollection;
import online.kyralo.user.domain.UserGives;
import online.kyralo.user.service.UserGivesService;
import online.kyralo.video.dao.VideoMapper;
import online.kyralo.video.domain.Video;
import online.kyralo.video.domain.VideoList;
import online.kyralo.video.service.VideoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static online.kyralo.common.constants.CommonConstants.*;
import static online.kyralo.common.constants.VideoConstants.VIDEOS_HOT_KEY;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:41
 * \* Year: 2019
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper mapper;
    @Resource
    private UserGivesMapper userGivesMapper;
    @Resource
    private UserCollectionMapper userCollectionMapper;

    @Resource
    private UserGivesService userGivesService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public ResponseEntity<?> recommendVideos() {
        List<VideoList> videos = mapper.listVideos();
        return ResponseEntity.ok(videos);
    }

    @Override
    public ResponseEntity<?> queryVideoById(String id, String userId) {

        if ("".equals(id)){
            return ResponseEntity.badRequest().build();
        }else {

            byte action = ACTION_NONE;
            boolean isCollection = false;


            if (userId != null){

                Example givesExample = new Example(UserGives.class);
                Example.Criteria givesCriteria = givesExample.createCriteria();
                givesCriteria.andEqualTo("targetId", id).
                        andEqualTo("userId", userId).
                        andEqualTo("type", GIVES_MOVIE);
                UserGives userGives =
                        userGivesMapper.selectOneByExample(givesExample);
                if (userGives != null){

                    String hashKey = GIVES_MOVIE_KEY_PREFIX + userGives.getId();

                    // 缓存里的数据才是实时的
                    UserGives gives =
                            (UserGives)redisTemplate.opsForHash().get("aurora_gives", hashKey);

                    if (gives != null){
                        action = gives.getActionType();
                    }else {
                        userGivesService.redisGivesUpdate(userGives);
                        action = userGives.getActionType();
                        redisTemplate.opsForHash().put("aurora_gives", hashKey, userGives);
                    }
                }

                Example collectionExample = new Example(UserCollection.class);
                Example.Criteria collectionCriteria = collectionExample.createCriteria();
                collectionCriteria.andEqualTo("userId", userId).
                        andEqualTo("videoId", id);
                UserCollection userCollection =
                        userCollectionMapper.selectOneByExample(collectionExample);
                if (userCollection != null){
                    isCollection = true;
                }

            }
            VideoList videoList;
            VideoList videos = (VideoList)redisTemplate.opsForHash().get("aurora_videos", id);
            if (videos == null){
                videoList = mapper.queryVideoById(id);
                if (videoList != null){

                    redisTemplate.opsForHash().put("aurora_videos", id, videoList);

                    videoList.setAction(action);
                    videoList.setCollected(isCollection);

                    return ResponseEntity.ok(videoList);
                }else {
                    return ResponseEntity.notFound().build();
                }
            }else {
                videoList = videos;
                videoList.setAction(action);
                videoList.setCollected(isCollection);
                return ResponseEntity.ok(videoList);
            }
        }
    }

    @Override
    public ResponseEntity<?> hotVideos(Integer start, Integer end) {
        Set<Object> objects =
                redisTemplate.opsForZSet().reverseRange(VIDEOS_HOT_KEY, start, end);

        List<VideoList> videoLists = new ArrayList<>();

        if (objects != null){
            objects.forEach( videoId -> videoLists.add(mapper.queryVideoById((String) videoId)));
        }

        return ResponseEntity.ok(videoLists);
    }

    @Override
    public ResponseEntity<?> listVideosByKind(Integer videoKindId) {
        if (videoKindId == null){
            return ResponseEntity.badRequest().build();
        }else {
            List<VideoList> videoLists = mapper.listVideosByKind(videoKindId);
            return ResponseEntity.ok(videoLists);
        }
    }

    @Override
    public ResponseEntity<?> listVideos() {
        List<VideoList> videos = mapper.listVideos();
        return ResponseEntity.ok(videos);
    }

    @Override
    public ResponseEntity<?> insert(Video video) {
        if ("".equals(video.getTitle()) ||
                "".equals(video.getVideoUrl()) ||
                "".equals(video.getCoverUrl()) ||
                "".equals(video.getAuthorId())){
            return ResponseEntity.badRequest().body("必要内容不能为空!");
        }else {
            video.setId(UUID.randomUUID().toString().replace("-",""));
            if (mapper.insertSelective(video) == 1){
                VideoList videoList = mapper.queryVideoById(video.getId());
                redisTemplate.opsForHash().put("aurora_videos", videoList.getId(), videoList);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    synchronized private void redisUpdate(VideoList videos){
        redisTemplate.opsForHash().put("aurora_videos", videos.getId(), videos);
    }

    @Override
    public ResponseEntity<?> actionCollection(String videoId, String userId){
        Example example = new Example(UserCollection.class);
        example.setDistinct(true);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("videoId",videoId).
                andEqualTo("userId", userId);
        UserCollection collection =
                userCollectionMapper.selectOneByExample(example);
        if (collection == null){
            try{
                collection = UserCollection.builder().
                        userId(userId).videoId(videoId).build();
                userCollectionMapper.insertSelective(collection);
                VideoList videos =
                        (VideoList) redisTemplate.opsForHash().get("aurora_videos", videoId);
                if (videos == null){
                    VideoList videoList = mapper.queryVideoById(videoId);
                    videoList.setCollections(videoList.getCollections() + 1);
                    redisUpdate(videoList);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
                videos.setCollections(videos.getCollections() + 1);
                redisUpdate(videos);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
        }
        try {
            VideoList videos = (VideoList) redisTemplate.opsForHash().get("aurora_videos", videoId);
            if (videos == null){
                VideoList videoList = mapper.queryVideoById(videoId);
                videoList.setCollections(Math.max(videoList.getCollections() - 1, 0));
                redisUpdate(videoList);
                userCollectionMapper.deleteByPrimaryKey(collection.getId());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            videos.setCollections(Math.max(videos.getCollections() - 1, 0));
            redisUpdate(videos);
            userCollectionMapper.deleteByPrimaryKey(collection.getId());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    private ResponseEntity<?> newGives(String videoId, String userId, Byte action){
        UserGives gives = UserGives.builder().actionType(action).
                targetId(videoId).userId(userId).actionType(action).type(GIVES_MOVIE).build();
        if (userGivesMapper.insertSelective(gives) > 0){

            userGivesService.redisGivesUpdate(gives);

            try {
                VideoList videos = (VideoList) redisTemplate.opsForHash().get("aurora_videos", videoId);
                if (videos == null) {
                    videos = mapper.queryVideoById(videoId);
                }
                if (ACTION_LIKE.equals(action)) {
                    videos.setLikes(videos.getLikes() + 1);
                } else if (ACTION_DISLIKE.equals(action)) {
                    videos.setDislikes(videos.getDislikes() + 1);
                }
                redisUpdate(videos);
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @Override
    public ResponseEntity<?> action(String videoId, String userId, Byte action){
        Example example = new Example(UserGives.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("targetId", videoId).
                andEqualTo("type", GIVES_MOVIE).andEqualTo("userId", userId);
        UserGives userGives = userGivesMapper.selectOneByExample(example);

        if (userGives == null){
            return newGives(videoId, userId, action);
        }

        String hashKey = GIVES_MOVIE_KEY_PREFIX + userGives.getId();
        UserGives redisUserGives =
                (UserGives)redisTemplate.opsForHash().get("aurora_gives", hashKey);

        if (redisUserGives != null){
            userGives = redisUserGives;
        }

        try {
            VideoList videos = (VideoList)redisTemplate.opsForHash().get("aurora_videos", videoId);
            if (videos == null){
                videos = mapper.queryVideoById(videoId);
            }
            if (ACTION_LIKE.equals(action)) {
                if (ACTION_LIKE.equals(userGives.getActionType())) {
                    videos.setLikes(Math.max(videos.getLikes() - 1, 0));
                    userGives.setActionType(ACTION_NONE);
                } else if (ACTION_DISLIKE.equals(userGives.getActionType())) {
                    videos.setLikes(videos.getLikes() + 1);
                    videos.setDislikes(Math.max(videos.getDislikes() - 1, 0));
                    userGives.setActionType(ACTION_LIKE);
                } else {
                    videos.setLikes(videos.getLikes() + 1);
                    userGives.setActionType(ACTION_LIKE);
                }
            } else if (ACTION_DISLIKE.equals(action)) {
                if (ACTION_DISLIKE.equals(userGives.getActionType())) {
                    videos.setDislikes(Math.max(videos.getDislikes() - 1, 0));
                    userGives.setActionType(ACTION_NONE);
                } else if (ACTION_LIKE.equals(userGives.getActionType())) {
                    videos.setDislikes(videos.getDislikes() + 1);
                    videos.setLikes(Math.max(videos.getLikes() - 1, 0));
                    userGives.setActionType(ACTION_DISLIKE);
                } else {
                    videos.setDislikes(videos.getDislikes() + 1);
                    userGives.setActionType(ACTION_DISLIKE);
                }
            }
            redisUpdate(videos);
            userGivesService.update(userGives);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> update(Video video) {
        if ("".equals(video.getAuthorId()) || "".equals(video.getId())){
            return ResponseEntity.badRequest().body("必要内容不能为空!");
        }else {
            if (mapper.updateByPrimaryKeySelective(video) == 1){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
        }
    }
}
