package online.kyralo.user.service.impl;

import online.kyralo.user.domain.UserComment;
import online.kyralo.user.service.UserCommentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserCommentServiceImplTest {

    @Autowired
    private UserCommentService service;

    @Test
    void insert() {
        UserComment userComment = new UserComment();
        userComment.setId(UUID.randomUUID().toString().replace("-",""));
        userComment.setCommentContent("eeeeee");
        userComment.setVideoId("ad557f34b91c43258383dc9175f2a8d8");
        userComment.setSendId("0c9b46de4a6042beb5422bc86d9833ed");
        System.out.println(service.insert(userComment));
    }
}