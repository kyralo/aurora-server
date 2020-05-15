package online.kyralo.fileup.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.kyralo.common.util.AliOssUtil;
import online.kyralo.fileup.service.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;


/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-05-14
 * \* Time: 19:58
 * \
 */

@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.dir.user.avatar}")
    private String ossUserAvatar;

    @Value("${aliyun.oss.dir.video.cover}")
    private String ossVideoCover;

    @Value("${aliyun.oss.dir.video.media}")
    private String ossVideoMedia;

    @Override
    public ResponseEntity<?> avatarUpForUser(MultipartFile file) {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        String avatarUrl = AliOssUtil.userRelatedUrl(ossUserAvatar, "avatar");

        String upload = null;
        try {
            upload = AliOssUtil.upload(file, avatarUrl);
        } catch (Exception ex) {
            log.error("文件上传失败! -- " + new Date().toString());
        }

        if (upload != null){
            return ResponseEntity.ok(upload);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> imageUpForVideo(MultipartFile file) {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        String videoCoverUrl = AliOssUtil.videoRelatedUrl(ossVideoCover, "");

        String upload = null;
        try {
            upload = AliOssUtil.upload(file, videoCoverUrl);
        } catch (Exception ex) {
            log.error("文件上传失败! -- " + new Date().toString());
        }

        if (upload != null){
            return ResponseEntity.ok(upload);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<?> videoUp(MultipartFile file, String videoName) {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        String videoMediaUrl = AliOssUtil.videoRelatedUrl(ossVideoMedia, videoName);

        String upload = null;
        try {
            upload = AliOssUtil.upload(file, videoMediaUrl);
        } catch (Exception ex) {
            log.error("文件上传失败! -- " + new Date().toString());
        }

        if (upload != null){
            return ResponseEntity.ok(upload);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
