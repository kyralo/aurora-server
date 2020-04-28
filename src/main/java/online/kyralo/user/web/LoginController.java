package online.kyralo.user.web;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.user.domain.RegisterVo;
import online.kyralo.user.service.LoginService;
import online.kyralo.user.service.MailService;
import online.kyralo.user.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Random;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-27
 * \* Time: 9:56
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/user")
@Api("用户登录接口")
public class LoginController {

    @Resource
    private MailService mailService;

    @Resource
    private LoginService loginService;

    public ResponseEntity<?> getCheckCode(String mail, HttpSession httpSession) {
        // 生成验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为：" + checkCode;

        String salt = "aurora_" + mail;
        String md5 = Hashing.md5().newHasher().
                putString(salt + checkCode, Charsets.UTF_8).hash().toString();

        httpSession.setAttribute(mail, md5);
        // 生效时间为60秒
//        httpSession.setMaxInactiveInterval(60);
        return mailService.sendSimpleMail(mail, "注册验证码", message);
    }

    @PostMapping("/login")
    @ApiOperation(value = "邮箱验证码登入")
    public ResponseEntity<?> verifyCodeToLogin(
            @NotEmpty(message = "内容不能为空!") @Email(message = "邮箱格式不对") @RequestParam String email,
                                               @NotEmpty(message = "内容不能为空!") @RequestParam String password) {
        return loginService.login(email, password);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterVo registerVo,
                                      HttpSession session) {

        if (registerVo.getCode() == null){
            // 生成验证码
            String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
            String message = "您的注册验证码为：" + checkCode;

            String salt = "aurora_" + registerVo.getMail();
            String md5 = Hashing.md5().newHasher().
                    putString(salt + checkCode, Charsets.UTF_8).hash().toString();

            session.setAttribute(registerVo.getMail(), md5);
            return mailService.sendSimpleMail(registerVo.getMail(), "注册验证码", message);
        }else {
            String checkCode = (String) session.getAttribute(registerVo.getMail());
            // 加盐
            String salt = "aurora_" + registerVo.getMail();
            String md5Code = Hashing.md5().newHasher().
                    putString(salt + registerVo.getCode(), Charsets.UTF_8).hash().toString();
            if (md5Code.equals(checkCode)) {
                session.removeAttribute(registerVo.getMail());
                User user = new User();
                BeanUtils.copyProperties(registerVo, user);
                return loginService.register(user);
            } else {
                return ResponseEntity.status(406).body("验证码验证失败");
            }
        }


    }
}
