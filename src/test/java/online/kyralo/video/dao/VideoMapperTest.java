package online.kyralo.video.dao;

import online.kyralo.user.domain.UserGives;
import online.kyralo.video.domain.Video;
import online.kyralo.video.domain.VideoList;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class VideoMapperTest {

    @Autowired
    private VideoMapper mapper;

    @Test
    void queryVideoById() {
        VideoList videoList = mapper.queryVideoById("09fcc374d44b4a8b8d0745d8c7b747fb");
        System.out.println(videoList);
    }

    @Test
    void listByTitleOrIntro() {
        Set<VideoList> videoLists = mapper.listByTitleOrIntro("生");
        System.out.println(videoLists);
    }

    @Test
    void testListByTitleOrIntro() {
        Set<VideoList> k = mapper.listByTitleOrIntro("测");
        System.out.println(k);
    }

    @Test
    public void testQueryVideoById() {

        Example example = new Example(Video.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("authorId", "46af013d2e3f43b188450e805dd1db60");

        System.out.println(mapper.selectOneByExample(example));

    }
}