package online.kyralo.user.service;

import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;

/**
 * @author wangchen
 */
public interface MailService {

    /**
     * 简单邮件发送
     * @param to 接受者mail
     * @param title 标题
     * @param content 文本内容
     */
    ResponseEntity<?> sendSimpleMail(String to, String title, String content);

    /**
     * 附件发送
     * @param to 接受者mail
     * @param title 标题
     * @param cotent 文本内容
     * @param fileList 文件
     */
    ResponseEntity<?> sendAttachmentsMail(String to, String title, String cotent, List<File> fileList);
}
