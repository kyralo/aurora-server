package online.kyralo.user.service;

import online.kyralo.user.domain.UserGives;
import org.springframework.http.ResponseEntity;


/**
 * @author wangchen
 */
public interface UserGivesService {

//    /**
//     * 获取用户点赞过的评论和视频
//     * @param userId 用户id
//     * @return 用户点赞过的评论和视频
//     */
//    ResponseEntity<?> listsUserGives(String userId);

    /**
     * 新增点赞记录
     * @param userGives 点赞记录
     * @return 新增的状态
     */
    ResponseEntity<?> insert(UserGives userGives);

    /**
     * 更新
     * @param userGives 点赞信息
     */
    void redisGivesUpdate(UserGives userGives);

    /**
     * 更新记录
     * @param userGives 点赞记录
     */
    void update(UserGives userGives);

}
