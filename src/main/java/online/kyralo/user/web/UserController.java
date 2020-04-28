package online.kyralo.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import online.kyralo.common.util.JwtUtil;
import online.kyralo.user.domain.User;
import online.kyralo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Objects;

import static online.kyralo.common.constants.SecurityConstants.HEADER_STRING;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:56
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/user")
@Api("用户信息接口")
public class UserController {

    @Resource
    private UserService service;

    @GetMapping
    @Secured("ROLE_USER")
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 400, message = "请求参数出错"),
            @ApiResponse(code = 404, message = "没有资源"),
            @ApiResponse(code = 500, message = "服务器错误 重新请求")
    })
    @ApiOperation(value = "查询用户信息,通过用户id", response = User.class)
    public ResponseEntity<?> getById(@RequestHeader(HEADER_STRING) String token){
            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            return service.queryById(userId);
    }

    @GetMapping("/author_id")
    @ApiOperation(value = "查询用户信息,通过用户id", response = User.class)
    public ResponseEntity<?> getByAuthorId(@RequestParam String authorId){
        return service.queryById(authorId);
    }


    @PutMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "更新用户信息,通过用户id", response = User.class)
    public ResponseEntity<?> update(@RequestBody User user, @RequestHeader(HEADER_STRING) String token){
        String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
        user.setId(userId);
        return service.update(user);
    }
}
