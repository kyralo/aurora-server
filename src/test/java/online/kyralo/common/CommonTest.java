package online.kyralo.common;

import io.jsonwebtoken.Claims;
import online.kyralo.common.util.CommonUtil;
import online.kyralo.common.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-04-19
 * \* Time: 18:26
 * \* Description:
 * \
 */
public class CommonTest {


    /*
        // 魔方秀热度 = (总赞数*0.7+总评论数*0.3)*1000/(发布时间距离当前时间的小时差+2)^1.2
    // => 修改 -> 热度 = (总赞数*0.6+总评论数*0.3 + 收藏数*0.1)*1000/
    // [(发布时间距离当前时间的小时差 + 2)^1.2 + 总赞数 + 踩数 + 收藏数]

    public static double scoreGenerate(Date createTime, Integer likes,
                                       Integer comments, Integer dislikes, Integer collections){
        long hour = DateUtil.between(createTime, new Date(), DateUnit.HOUR);
        return (likes * 0.6 + comments * 0.3 + collections * 0.1) * 1000 /
                (Math.pow((hour + 2), 1.2) + likes + dislikes + collections);
    }
     */

    @Test
    public void score(){
        Date createTime = new Date();
        Integer likes = 10, dislikes = 2, collections = 10, comments = 10;

        System.out.println(CommonUtil.scoreGenerate(createTime, likes, comments, dislikes, collections));

    }

    @Test
    public void demo(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.matches("jeasn", "$2a$10$sFjw4LLckKpXRG4nyswGwuI/6ShtjT/xrzHjNxZAchhKqTnW1vfKG"));
    }

    @Test
    public void jwtTest(){
        Claims claims = JwtUtil.parseToken("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDc3MTU5ZWY1Zjk0ZGM5ODM1MzkzZjIwYzBhYzQxOCIsInN1YiI6IlJPTEVfVVNFUiIsImV4cCI6MTU4NzcxMDUxN30.cAh8BURNIHOxU5h7ZGw4xuchDGaREjodn24e1rSLDx8");
        assert claims != null;
        System.out.println(claims.getAudience());

    }

    @Test
    public void time(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/");
        System.out.println(format.format(new Date()));
    }

    @Test
    public void uuid(){
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }


}
