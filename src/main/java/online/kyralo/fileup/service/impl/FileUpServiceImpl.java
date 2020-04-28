package online.kyralo.fileup.service.impl;

import online.kyralo.common.util.FileUpUtil;
import online.kyralo.fileup.service.FileUpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * \* Created with IntelliJ IDEA.
 * \* @author: WangChen
 * \* Date: 19-7-13
 * \* Time: 上午9:39
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class FileUpServiceImpl implements FileUpService {

    @Resource
    private Environment environment;

    @Value("${aurora.fileup.root_path}")
    private String absoluteUrl;

    private ResponseEntity<?> imageUp(MultipartFile file,
                                      String person,
                                      String usepath,
                                      String userId,
                                      String imageName){

        String maxFileSize = environment.getProperty("spring.servlet.multipart.max-file-size");

        if (maxFileSize != null){

//            if (file.getSize() > Long.parseLong(maxFileSize)){
//
//
//                file = (MultipartFile)ImgUtil
//                        .scale((Image) file, (Long.parseLong(maxFileSize)) / (float) file.getSize()) ;
//
//            }

            String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");

            String orignName = String.valueOf(System.currentTimeMillis());

            if (!"".equals(imageName)){
                orignName = imageName;
            }

            orignName = orignName + "." + split[split.length - 1];

            String path = absoluteUrl + person + userId + "/images/" + usepath;

            if (FileUpUtil.singleFileUp(file,path, orignName)){
                return ResponseEntity.ok("upload/" + person + userId + "/images/" + usepath + orignName);
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败!");
            }
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件最大限制");
        }
    }

    public ResponseEntity<?> videoStore(MultipartFile file, String path, String userId, String videoName){

            // String maxFileSize = environment.getProperty("spring.servlet.multipart.max-file-size")
            // todo 视频格式转换成.flv格式(要是条件允许[服务器配高]的话)

            String usePath = absoluteUrl + userId + path;
            String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            String orignName = file.getOriginalFilename();
            if (!"".equals(videoName)){
                orignName = videoName + "." + split[split.length - 1];
            }
            orignName = orignName + "." + split[split.length - 1];
            if (FileUpUtil.singleFileUp(file,usePath, orignName)){
                return ResponseEntity.ok("upload/" + userId + path + orignName);
            }else {
                return ResponseEntity.ok("上传失败!");
            }
    }

    @Override
    public ResponseEntity<?> avatarUpForUser(MultipartFile file, String userId) {
        return imageUp(file,"user/","avatar/img/", userId, "avatar");
    }

    @Override
    public ResponseEntity<?> imageUpForVideo(MultipartFile file, String userId) {
        return imageUp(file,"videos/","cover/", userId, "");
    }

    @Override
    public ResponseEntity<?> videoUp(MultipartFile file, String userId, String videoName) {
        return videoStore(file, "videos/", userId, videoName);
    }
}
