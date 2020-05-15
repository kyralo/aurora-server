package online.kyralo.fileup.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.fileup.service.OssService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;


/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-05-14
 * \* Time: 19:56
 * \* Description: oss 文件上传
 * \
 */

@RestController
@RequestMapping("/api/v2/oss")
@Api("文件上传[alioss]接口")
public class OssController {

    @Resource
    private OssService service;

    @PostMapping("/user/images")
    @ApiOperation(value = "用户头像上传")
    @Secured("ROLE_USER")
    public ResponseEntity<?> avatarUpForUser(@NotNull(message = "内容不能为空") @RequestParam("file") MultipartFile file){
        return service.avatarUpForUser(file);
    }

    @PostMapping("/video/image")
    @ApiOperation(value = "视频图片上传")
    @Secured("ROLE_USER")
    public ResponseEntity<?> imageUpForVideo(@NotNull(message = "内容不能为空") @RequestParam("file") MultipartFile file){
            return service.imageUpForVideo(file);
    }

    @PostMapping("/user/videos")
    @ApiOperation(value = "视频上传")
    @Secured("ROLE_USER")
    public ResponseEntity<?> videoUp(@NotNull(message = "内容不能为空") @RequestParam("file") MultipartFile file,
                                     @RequestParam(defaultValue = "") String videoName){
            return service.videoUp(file, videoName);
    }

}
