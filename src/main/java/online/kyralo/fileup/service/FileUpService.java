package online.kyralo.fileup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: WangChen
 * \* Date: 19-7-13
 * \* Time: 上午9:36
 * \
 */
public interface FileUpService {
//    /** 缩放图片 scale */
//
//    /** 剪裁图片 cut */
//
//    /** 按行列剪裁切片 例:(将图片分为20行和20列) slice */
//
//    /** 图片类型转换，支持GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG等 convert */
//
//    /** 彩色转为黑白 gray */
//
//    /** 添加文字水印 pressText */
//
//    /** 添加图片水印 pressImage */
//
//    /** 旋转图片 rotate */
//
//    /** 水平翻转图片 flip */

    /**
     * 用户头像上传 用户
     * @param file 图片流
     * @param userId 用户id
     * @return 图片路径
     */
    ResponseEntity<?> avatarUpForUser(MultipartFile file, String userId);

    /**
     * 视频封面上传
     * @param file 图片流
     * @param userId 用户id
     * @return 图片路径
     */
    ResponseEntity<?> imageUpForVideo(MultipartFile file, String userId);

    /**
     * 视频上传
     * @param file 视频文件
     * @param userId 用户id
     * @param videoName 视频名
     * @return 视频路径
     */
    ResponseEntity<?> videoUp(MultipartFile file, String userId, String videoName);
}
