package online.kyralo.user.service;

import online.kyralo.user.domain.UserComment;
import org.springframework.http.ResponseEntity;

/**
 * @author wangchen
 */
public interface UserCommentService {

    /**
     * 查询视频的一级评论
     * @param videoId  视频id
     * @param userId 用户id
     * @return 视频的一级评论
     */
    ResponseEntity<?> getLevel1Comments(String  videoId, String userId);

    /**
     * 查询视频的二级评论
     * @param videoId  视频id
     * @param ancestryId 祖父id(一级评论id)
     * @param userId 用户id
     * @return 视频的二级评论
     */
    ResponseEntity<?> getLevel2Comments(String  videoId, String ancestryId, String userId);

    /**
     * 新增评论
     * @param userComment 用户评论
     * @return 是否新增成功
     */
    ResponseEntity<?> insert(UserComment userComment);

    /**
     * 点赞 踩
     * @param commentId 评论id
     * @param userId 用户id
     * @param action 动作
     * @return 是否成功
     */
    ResponseEntity<?> action(String commentId, String userId, Byte action);

}
