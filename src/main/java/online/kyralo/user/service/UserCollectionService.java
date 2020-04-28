package online.kyralo.user.service;

import online.kyralo.user.domain.UserCollection;
import org.springframework.http.ResponseEntity;

/**
 * @author wangchen
 */
public interface UserCollectionService {

    /**
     * 查询所有用户收藏 通过用户id
     * @param userId 用户id
     * @return 所有用户收藏
     */
    ResponseEntity<?> listUserCollectionsById(String userId);

    /**
     * 新增用户收藏
     * @param userCollection 用户收藏
     * @return 新增的状态
     */
    ResponseEntity<?> insert(UserCollection userCollection);

    /**
     * 删除收藏 通过收藏id和收藏id
     * @param id 收藏id
     * @param userId 收藏id
     * @return 删除的状态
     */
    ResponseEntity<?> delete(Integer id, String userId);

}
