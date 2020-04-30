package online.kyralo.user.dao;

import online.kyralo.user.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper mapper;

    @Test
    void getAll(){
        List<User> users = mapper.selectAll();

        users.forEach(System.out::println);
    }

    @Test
    void getById(){
//        Example example = new Example(User.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("id", "4b645fb0ffa3471aa193f0d27ddc9ecf");
////        criteria.andEqualTo("name", "wangchen1");
//        System.out.println(mapper.selectByExample(example));

        System.out.println(mapper.selectByPrimaryKey("ed6a4164941d4a4d9a71f4e0fdc19fb7"));
    }

    @Test
    void queryByMail() {

        User user = mapper.queryByMail("15270640536@163.com");
        System.out.println(user);
        Assert.assertNotNull(user);
    }

    @Test
    void queryById() {
        User user = mapper.selectByPrimaryKey("4b645fb0ffa3471aa193f0d27ddc9ecf");
        Assert.assertNotNull(user);
    }

    @Test
    void insert() {
        User user = new User();
        user.setId(UUID.randomUUID().toString().replace("-",""));
        user.setName("wangchen1");
        user.setPassword("1234567");
        user.setMail("xxxxxx@qq.com");
        user.setSign("莫问!");

        int i = mapper.insert(user);

        System.out.println(i);
        Assert.assertEquals(1, i);

    }

    @Test
    void update() {
        User user = new User();
        user.setId("accd999b39dc453d89a5078e4f2041b9");
        user.setPassword("666666");

        int i = mapper.updateByPrimaryKeySelective(user);

        Assert.assertEquals(1, i);
    }

    @Test
    void listByName(){
        List<User> users = mapper.listByName("y");
        System.out.println(users);
        System.out.println(users.size());
    }

    @Test
    public void testQueryByMail() {
        System.out.println(mapper.queryByMail("kyralo721@gmail.com"));
    }
}