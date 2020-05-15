package online.kyralo.fileup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 */
public interface OssService {
    /**
     * 用户头像上传 用户
     * @param file 图片流
     * @return 图片路径
     */
    ResponseEntity<?> avatarUpForUser(MultipartFile file);

    /**
     * 视频封面上传
     * @param file 图片流
     * @return 图片路径
     */
    ResponseEntity<?> imageUpForVideo(MultipartFile file);

    /**
     * 视频上传
     * @param file 视频文件
     * @param videoName 视频名
     * @return 视频路径
     */
    ResponseEntity<?> videoUp(MultipartFile file, String videoName);

}
