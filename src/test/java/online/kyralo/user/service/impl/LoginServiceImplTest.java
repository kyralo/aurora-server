package online.kyralo.user.service.impl;

import online.kyralo.user.domain.User;
import online.kyralo.user.service.LoginService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginServiceImplTest {

    @Resource
    private LoginService service;

    @Test
    void verifyCodeToLogin() {

    }

//    @Test
//    void login() {
//        ResponseEntity<?> responseEntity = service.login("15270640536@163.com", "123456");
//
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

    @Test
    void register() {
        User user = new User();
        user.setMail("15270640536@163.com");
        user.setName("kyralo");
        user.setPassword("123456");
        ResponseEntity<?> register = service.register(user);
        Assert.assertEquals(HttpStatus.CREATED, register.getStatusCode());
    }

}


