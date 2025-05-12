package com.company.component.smpt;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;

@RequiredArgsConstructor
@Service
public class SmptService {
    @Value("${mail.from}")
    private String from;

    private final JavaMailSender mailSender;

    public Boolean sendSms(@RequestBody Sms sms) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        if (sms.getTo().contains(",")) {
            message.setTo(sms.getTo().split(","));
        } else {
            message.setTo(sms.getTo());
        }
        message.setSubject(sms.getSubject());
        message.setText(sms.getText());
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String sendHtml(@RequestBody Sms sms)  {
        try {
            sendHtmlEmail(sms.getTo(), sms.getSubject(), HtmlContent.htmlContent);
            return "SENT HTML";
        } catch (Exception e) {
            return "ERROR";
        }
    }
    public String sendEmailViaFile(@RequestBody Sms sms)  {
        try {
            String filePath = new ClassPathResource("image.jpg").getFile().getAbsolutePath();
            sendEmailWithAttachment(sms.getTo(), sms.getSubject(), sms.getText(), filePath);
            return "SENT FILE";
        }
        catch (Exception e) {
            return "ERROR";
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to.split(","));
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public String sendEmailWithAttachment(String to, String subject, String text, String filePath) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to.split(","));
        helper.setSubject(subject);
        helper.setText(text);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        helper.addAttachment("image.jpg", file);

        mailSender.send(message);
        return "SENT";
    }
}
