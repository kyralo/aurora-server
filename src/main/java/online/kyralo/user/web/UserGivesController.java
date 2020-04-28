package online.kyralo.user.web;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.common.util.JwtUtil;
import online.kyralo.user.domain.UserGives;
import online.kyralo.user.service.UserGivesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-30
 * \* Time: 16:41
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/user_gives")
@Api("用户点赞接口")
public class UserGivesController {

    @Resource
    private UserGivesService service;

//    @GetMapping("/count")
//    @ApiOperation(value = "获取点赞数, targetId, type", response = UserGives.class)
//    public ResponseEntity<?> countGives(@RequestBody UserGives userGives, HttpServletRequest httpServletRequest){
//        Claims claims = JwtUtil.claimGet(httpServletRequest);
//        userGives.setUserId(claims.getAudience());
//        return service.countGives(userGives);
//    }
//
//    @GetMapping
//    @Secured("ROLE_USER")
//    @ApiOperation(value = "获取用户点赞历史", response = UserGives.class)
//    public ResponseEntity<?> listsUserGives(HttpServletRequest httpServletRequest){
//        String userId = JwtUtil.claimGet(httpServletRequest).getAudience();
//        return service.listsUserGives(userId);
//    }

    @PostMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "新增点赞", response = UserGives.class)
    public ResponseEntity<?> insert(@RequestBody UserGives userGives, HttpServletRequest httpServletRequest){
        Claims claims = JwtUtil.claimGet(httpServletRequest);
        userGives.setUserId(claims.getAudience());
        return service.insert(userGives);
    }

}
