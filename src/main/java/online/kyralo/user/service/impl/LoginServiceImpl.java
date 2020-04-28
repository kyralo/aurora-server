package online.kyralo.user.service.impl;

import online.kyralo.common.util.JwtUtil;
import online.kyralo.user.dao.UserMapper;
import online.kyralo.user.domain.User;
import online.kyralo.user.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static online.kyralo.common.constants.CommonConstants.TOKEN;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-27
 * \* Time: 11:57
 * \* Year: 2019
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseEntity<?> login(String mail, String password) {
        if ("".equals(mail) || "".equals(password)){
            return ResponseEntity.badRequest().body("邮箱或密码为空");
        }else {
            User user = userMapper.queryByMail(mail);
            if (user != null){
                if (bCryptPasswordEncoder.matches(password, user.getPassword())){
                    user.setPassword("");
                    HttpHeaders httpHeaders = new HttpHeaders();

                    String cacheToken = (String)redisTemplate.opsForValue().get(TOKEN + mail);

                    if (cacheToken != null && JwtUtil.parseToken("Bearer " + cacheToken) != null){
                        httpHeaders.setBearerAuth(cacheToken);
                    }else {
                        String token = JwtUtil.generateTokenByUserId(user.getId());
                        redisTemplate.opsForValue().set(TOKEN + mail, token);
                        redisTemplate.expire(TOKEN + mail, 3, TimeUnit.HOURS);
                        httpHeaders.setBearerAuth(token);
                    }

                    return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
                }else {
                    //todo 登入失败处理
                    return ResponseEntity.notFound().build();
                }
            }else {
                return ResponseEntity.status(406).body("账号已注销或被封禁");
            }
        }
    }

    @Override
    public ResponseEntity<?> register(User user) {

        if ("".equals(user.getMail()) || "".equals(user.getPassword())){
            return ResponseEntity.badRequest().build();
        }else {
            user.setId(UUID.randomUUID().toString().replace("-",""));

            // 密码加密
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (userMapper.insertSelective(user) == 1){
                return ResponseEntity.status(HttpStatus.CREATED).body("register success");
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
