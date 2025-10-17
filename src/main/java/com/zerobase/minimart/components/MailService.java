package com.zerobase.minimart.components;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnBean(JavaMailSender.class)
public class MailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public boolean sendHtmlMail(String to, String subject, String htmlContent) {
        if (mailSender == null) {
            log.warn("메일 서비스가 비활성화되어 있습니다. 메일 발송을 건너뜁니다.");
            return false;
        }
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);  // true: HTML 형식 허용
            mailSender.send(message);
            log.info("메일 발송 성공: {}", to);
            return true;
        } catch (MessagingException e) {
            log.error("메일 발송 오류: {}", e.getMessage());
            return false;
        }
    }
}