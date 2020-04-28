package online.kyralo.user.service;

import org.springframework.http.ResponseEntity;
import online.kyralo.user.domain.User;

import javax.servlet.http.HttpSession;

/**
 * @author wangchen
 */
public interface LoginService {

    /**
     * 密码登入
     * @param mail 邮箱
     * @param password 密码
     * @return 是否登录成功
     */
    ResponseEntity<?> login(String mail, String password);

    /**
     * 注册
     * @param user 用户信息
     * @return 注册信息
     */
    ResponseEntity<?> register(User user);
}
