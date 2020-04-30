package online.kyralo.user.service.impl;

import online.kyralo.user.domain.User;
import online.kyralo.user.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService service;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void queryById() {
        ResponseEntity<?> responseEntity = service.queryById("accd999b39dc453d89a5078e4f2041b9");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void update() {
        User user = new User();
        user.setId("accd999b39dc453d89a5078e4f2041b9");
        user.setName("kyralo");

        ResponseEntity<?> responseEntity = service.update(user);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void demo(){
//        redisTemplate.opsForZSet().
    }
}