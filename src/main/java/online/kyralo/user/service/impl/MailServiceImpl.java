package online.kyralo.user.service.impl;

import online.kyralo.user.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-27
 * \* Time: 10:31
 * \* Year: 2019
 * \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MailServiceImpl implements MailService {

    @Resource
    private Environment environment;

    @Resource
    private JavaMailSender mailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ResponseEntity<?> sendSimpleMail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (MailException e) {
            logger.info("邮件发送失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("邮件未发送!");
        }

        logger.info("邮件发送成功");
        return ResponseEntity.status(HttpStatus.OK).body("code success");
    }

    @Override
    public ResponseEntity<?> sendAttachmentsMail(String to, String title, String cotent, List<File> fileList) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(cotent);
            String fileName;
            for (File file : fileList) {
                fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
                helper.addAttachment(fileName, file);
            }
            mailSender.send(message);

        } catch (Exception e) {
            logger.info("邮件发送失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("邮件未发送!");
        }

        logger.info("邮件发送成功");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
