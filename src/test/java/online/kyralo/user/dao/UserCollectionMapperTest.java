package online.kyralo.user.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserCollectionMapperTest {

    @Resource
    private UserCollectionMapper mapper;

    @Test
    public void listUserCollectionsById() {

        System.out.println(mapper.listUserCollectionsById("546dc0e6490b4bdd9faf6a97b8319c61"));

    }
}