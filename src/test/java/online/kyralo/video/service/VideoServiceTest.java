package online.kyralo.video.service;

import online.kyralo.video.domain.Video;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class VideoServiceTest {

    @Autowired
    private VideoService service;

    @Test
    void queryVideoById() {



    }

    @Test
    void listVideos() {
    }

    @Test
    void insert() {

        Video video = new Video();
        video.setId(UUID.randomUUID().toString().replace("-",""));
        video.setAuthorId("0c9b46de4a6042beb5422bc86d9833ed");
        video.setCoverUrl("https://www.bilibili.com/d6b46f86-ec67-4db4-b3f6-5bd8f84a8b74");
        video.setKindId(1);
        video.setTitle("测试样例8");
        video.setVideoUrl("https://media.w3.org/2010/05/sintel/trailer.mp4");
        video.setIntroduction("一个测试样例8");
        service.insert(video);

    }

    @Test
    void update() {
    }



    @Test
    void listVideosByKind() {
        System.out.println(service.listVideosByKind(1));
    }
}