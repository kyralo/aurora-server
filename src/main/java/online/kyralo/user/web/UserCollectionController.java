package online.kyralo.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.common.util.JwtUtil;
import online.kyralo.user.domain.UserCollection;
import online.kyralo.user.service.UserCollectionService;
import org.jetbrains.annotations.NotNull;
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
 * \* Time: 20:57
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/user_collection")
@Api("用户收藏接口")
public class UserCollectionController {

    @Resource
    private UserCollectionService service;

    @GetMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "查询所有用户收藏", response = UserCollection.class)
    public ResponseEntity<?> getAllByUserId(@RequestHeader(HEADER_STRING) String token){
            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            return service.listUserCollectionsById(userId);
    }

    @PostMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "新增用户收藏", response = UserCollection.class)
    public ResponseEntity<?> insert(@RequestBody @NotNull UserCollection uerCollection,
                                    @RequestHeader(HEADER_STRING) String token){
            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            uerCollection.setUserId(userId);
            return service.insert(uerCollection);
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "删除用户收藏", response = UserCollection.class)
    public ResponseEntity<?> delete(@RequestParam("id") Integer id, @RequestHeader(HEADER_STRING) String token){
            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            return service.delete(id, userId);
    }
}
