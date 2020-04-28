package online.kyralo.user.dao;

import online.kyralo.user.domain.GivesForComments;
import online.kyralo.user.domain.GivesForVideo;
import online.kyralo.user.domain.UserGives;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 王宸
 */

@Repository
public interface UserGivesMapper extends Mapper<UserGives> {

    /**
     * 统计点赞数
     * @param userGives targetId, type
     * @return 点赞数
     */
    int countGives(UserGives userGives);

    /**
     * 获取用户点赞过的评论
     * @param userId 用户id
     * @return 用户点赞过的评论
     */
    List<GivesForComments> listsUserGivesForComments(String userId);

    /**
     * 获取用户点赞过的视频
     * @param userId 用户id
     * @return 用户点赞过的视频
     */
    List<GivesForVideo> listsUserGivesForVideo(String userId);

}