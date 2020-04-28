package online.kyralo.common.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-04-19
 * \* Time: 16:46
 * \* Description: 项目常用的方法
 * \
 */

@Component
public class CommonUtil {

    // 魔方秀热度 = (总赞数*0.7+总评论数*0.3)*1000/(发布时间距离当前时间的小时差+2)^1.2
    // => 修改 -> 热度 = (总赞数*0.6+总评论数*0.3 + 收藏数*0.1)*1000/
    // [(发布时间距离当前时间的小时差 + 2)^1.2 + 总赞数 + 踩数 + 收藏数]

    public static double scoreGenerate(Date createTime, Integer likes,
                                       Integer comments, Integer dislikes, Integer collections){
        long hour = DateUtil.between(createTime, new Date(), DateUnit.HOUR);
        return (likes * 0.6 + comments * 0.3 + collections * 0.1) * 1000 /
                (Math.pow((hour + 2), 1.2) + likes + dislikes + collections);
    }

}
