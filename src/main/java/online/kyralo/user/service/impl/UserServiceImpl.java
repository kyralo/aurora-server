package online.kyralo.user.service.impl;

import online.kyralo.user.dao.UserMapper;
import online.kyralo.user.domain.User;
import online.kyralo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:39
 * \* Year: 2019
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<?> queryById(String id) {
        if ("".equals(id)){
           return ResponseEntity.badRequest().build();
        }else {
            User user = mapper.selectByPrimaryKey(id);
            if (user != null){
                user.setPassword(null);
                return ResponseEntity.ok(user);
            }else {
                return ResponseEntity.notFound().build();
            }
        }

    }


    @Override
    public ResponseEntity<?> update(User user) {

        if ("".equals(user.getId())){
            return ResponseEntity.badRequest().build();
        }else {
            if (user.getPassword() != null){
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            if (mapper.updateByPrimaryKeySelective(user) == 1){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }
        }
    }
}
