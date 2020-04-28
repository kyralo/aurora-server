package online.kyralo.user.dao;

import online.kyralo.user.domain.Comment;
import online.kyralo.user.domain.UserComment;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 王宸
 */
@Repository
public interface UserCommentMapper extends Mapper<UserComment> {

    /**
     * 获取所有评论 通过评论id 评论类型
     * @param videoId 评论id
     * @return 所有评论
     */
    List<Comment> listLevel1Comments(String videoId);

    /**
     * comment
     * @param videoId videoId
     * @param ancestryId ancestryId
     * @return comments
     */
    List<Comment> listLevel2Comments(String videoId, String ancestryId);
}