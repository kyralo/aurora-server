package online.kyralo.user.service;

import online.kyralo.user.domain.User;
import org.springframework.http.ResponseEntity;

/**
 * @author wangchen
 */
public interface UserService {
    /**
     * 查询用户信息 通过id
     * @param id 用户id
     * @return 用户信息
     */
    ResponseEntity<?> queryById(String id);

//    /**
//     * 新增用户
//     * @param user 用户信息
//     * @return 新增的状态
//     */
//    ResponseEntity<?> insert(User user);

    /**
     * 更新用户信息 通过用户id
     * @param user 用户信息
     * @return 更新状态
     */
    ResponseEntity<?> update(User user);
}
