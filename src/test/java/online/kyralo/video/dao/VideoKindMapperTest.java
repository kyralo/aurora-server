package online.kyralo.video.dao;

import online.kyralo.video.domain.VideoKind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoKindMapperTest {

    @Resource
    private VideoKindMapper mapper;

    @Test
    public void insert(){
        VideoKind videoKind = new VideoKind();
        videoKind.setName("动漫");
        videoKind.setInfo("番剧漫减题材");

        mapper.insertSelective(videoKind);
    }

}