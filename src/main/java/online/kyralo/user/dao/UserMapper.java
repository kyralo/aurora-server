package online.kyralo.user.dao;

import online.kyralo.user.domain.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 王宸
 */
public interface UserMapper extends Mapper<User>{

    /**
     * 查询用户信息 通过mail
     * @param mail 邮箱
     * @return 用户信息
     */
    User queryByMail(String mail);

    /**
     * 通过名字模糊匹配
     * @param name 名字
     * @return 用户列表
     */
    List<User> listByName(String name);

}