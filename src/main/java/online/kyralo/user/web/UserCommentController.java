package online.kyralo.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.common.util.JwtUtil;
import online.kyralo.user.domain.UserComment;
import online.kyralo.user.service.UserCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static online.kyralo.common.constants.SecurityConstants.HEADER_STRING;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:57
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/user_comment")
@Api("用户评论接口")
public class UserCommentController {

    @Resource
    private UserCommentService service;

    @GetMapping("/level1/{videoId}")
    @ApiOperation(value = "查询一级评论 通过视频id", response = UserComment.class)
    public ResponseEntity<?> getLevel1(@NotEmpty(message = "内容不能为空") @PathVariable("videoId") String videoId,
                                       @RequestHeader(HEADER_STRING) String token){
        String userId;
        if (token == null || "".equals(token)){
            userId = null;
        }else {
            try {
                userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            }catch (Exception e){
                userId = null;
            }
        }

        return service.getLevel1Comments(videoId, userId);
    }

    @GetMapping("/level2/{videoId}")
    @ApiOperation(value = "查询二级评论 通过视频id 父级评论id", response = UserComment.class)
    public ResponseEntity<?> getLevel2(@NotEmpty(message = "内容不能为空") @PathVariable("videoId") String videoId,
                                       @NotEmpty(message = "内容不能为空") @RequestParam String ancestryId,
                                       @RequestHeader(HEADER_STRING) String token){
        String userId;
        if (token == null || "".equals(token)){
            userId = null;
        }else {
            try {
                userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            }catch (Exception e){
                userId = null;
            }
        }

        return service.getLevel2Comments(videoId, ancestryId, userId);
    }

    @PostMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "新增用户评论", response = UserComment.class)
    public ResponseEntity<?> insert(@RequestBody UserComment userComment,
                                    @RequestHeader(HEADER_STRING) String token){
        try {
            userComment.setSendId(Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return service.insert(userComment);
    }

    @PutMapping("/action")
    @Secured("ROLE_USER")
    @ApiOperation(value = "更新用户评论点赞, 通过用户id, 0 none, 1 dislike 2 like", response = UserComment.class)
    public ResponseEntity<?> update(@NotEmpty(message = "内容不能为空") @RequestParam("id") String id,
                                    @NotNull(message = "内容不能为空") @RequestParam("action") Byte action,
                                    @RequestHeader(HEADER_STRING) String token){
            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            return service.action(id, userId, action);
    }
}
