package online.kyralo.user.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserCommentMapperTest {

    @Resource
    private UserCommentMapper mapper;

    @Test
    public void getAll(){
        System.out.println(mapper.selectAll());
    }

    @Test
    public void listLevel1Comments() {
        System.out.println(mapper.listLevel1Comments("7eeacf5061e243079406aec97bb25e95"));
    }

    @Test
    public void listLevel2Comments() {
    }
}