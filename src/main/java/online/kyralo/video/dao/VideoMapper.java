package online.kyralo.video.dao;

import online.kyralo.video.domain.Video;
import online.kyralo.video.domain.VideoList;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author 王宸
 */
@Repository
public interface VideoMapper extends Mapper<Video> {

    /**
     * 查询视频信息 通过视频id
     * @param id 视频id
     * @return 视频信息
     */
    VideoList queryVideoById(String id);

    /**
     * 获取视频 通过视频类型id
     * @param videoKindId 视频类型id
     * @return 视频
     */
    List<VideoList> listVideosByKind(Integer videoKindId);

    /**
     * 获取视频 通过视频类型id
     * @return 视频
     */
    List<VideoList> listVideos();

    /**
     * 通过关键字查询
     * @param str 关键字
     * @return 查询视频结果
     */
    Set<VideoList> listByTitleOrIntro(String str);
}