package online.kyralo.fileup.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.common.util.JwtUtil;
import online.kyralo.fileup.service.FileUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import java.util.Objects;

import static online.kyralo.common.constants.SecurityConstants.HEADER_STRING;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: WangChen
 * \* Date: 19-7-13
 * \* Time: 上午9:39
 * \
 */
@RestController
@RequestMapping("/api/v2/file_up")
@Api("文件上传接口")
public class FileUpController {

    @Resource
    private FileUpService service;

    @PostMapping("/user/images")
    @ApiOperation(value = "用户头像上传")
    @Secured("ROLE_USER")
    public ResponseEntity<?> avatarUpForUser(@NotNull(message = "内容不能为空") @RequestParam("file") MultipartFile file,
                                             @RequestHeader(HEADER_STRING) String token){
        String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
        if (userId == null || "".equals(userId)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return service.avatarUpForUser(file, userId);
    }

    @PostMapping("/video/image")
    @ApiOperation(value = "视频图片上传")
    @Secured("ROLE_USER")
    public ResponseEntity<?> imageUpForVideo(@NotNull(message = "内容不能为空") @RequestParam("file") MultipartFile file,
                                             @RequestHeader(HEADER_STRING) String token){

            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            if (userId == null || "".equals(userId)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }else {
                return service.imageUpForVideo(file, userId);
            }
    }

    @PostMapping("/user/videos")
    @ApiOperation(value = "视频上传")
    @Secured("ROLE_USER")
    public ResponseEntity<?> videoUp(@NotNull(message = "内容不能为空") @RequestParam("file") MultipartFile file,
                                     @RequestHeader(HEADER_STRING) String token,
                                     @RequestParam(defaultValue = "") String videoName){

            String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            if (userId == null || "".equals(userId)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }else {
                return service.videoUp(file, userId, videoName);
            }
    }

}
