package online.kyralo.video.service;

import online.kyralo.video.domain.Video;
import org.springframework.http.ResponseEntity;


/**
 * @author wangchen
 */
public interface VideoService {

    /**
     * 获取推荐视频列表
     * @return 推荐视频列表
     */
    ResponseEntity<?> recommendVideos();

    /**
     * 查询视频信息 通过视频id
     * @param id 视频id
     * @param userId 用户id
     * @return 视频信息
     */
    ResponseEntity<?> queryVideoById(String id, String userId);

    /**
     * 获取热门视频列表 范围列表
     * @param start start
     * @param end end
     * @return 热门视频列表
     */
    ResponseEntity<?> hotVideos(Integer start, Integer end);

    /**
     * 获取视频 通过视频类型id
     * @return 视频
     */
    ResponseEntity<?> listVideos();

    /**
     * 获取视频 通过视频类型id
     * @param videoKindId 视频类型id
     * @return 视频
     */
    ResponseEntity<?>  listVideosByKind(Integer videoKindId);


    /**
     * 新增视频
     * @param video 视频信息
     * @return 新增的状态
     */
    ResponseEntity<?> insert(Video video);

    /**
     * 点赞 踩
     * @param videoId 视频id
     * @param userId 用户id
     * @param action 动作
     * @return 是否成功
     */
    ResponseEntity<?> action(String videoId, String userId, Byte action);

    /**
     * 收藏
     * @param videoId 视频id
     * @param userId 用户id
     * @return 是否成功
     */
    ResponseEntity<?> actionCollection(String videoId, String userId);

    /**
     * 更新视频信息
     * @param video 视频信息
     * @return 更新的状态
     */
    ResponseEntity<?> update(Video video);
}
