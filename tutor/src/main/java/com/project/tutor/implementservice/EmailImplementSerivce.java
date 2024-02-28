package com.project.tutor.implementservice;

import com.project.tutor.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class EmailImplementSerivce implements EmailService {
    @Autowired
    JavaMailSender javaSendMail;

    @Override
    public void sendEmailActive(String email, String activeCode) {
        try {
            String subject = "Kích hoạt tài khoản tại web Tutor";
            String text = "Vui lòng sử dụng mã sau để kich hoạt cho tài khoản <" + email + ">:" +
                    "<html><body><br/><h1>" + activeCode + "</h1></body></html>";
            text += "<br/> Click vào đường link để kích hoạt tài khoản :";
            String url = "http://localhost:8080/auth/active" + email + "/" + activeCode;
            text += "<br/> <a href=" + url + ">" + url + "</a>";
            sendEmail("duynvde160530@fpt.edu.vn", email, subject, text);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending mail : " + e.getMessage());
        }
    }

    @Override
    public void sendEmailResetPassword(String email) {
        MimeMessage message = javaSendMail.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Reset password");
            helper.setText("""
                    <div>
                      <a href="http://localhost:8080/forgot-password?email=%s" target="_blank">click link to verify</a>
                    </div>
                    """.formatted(email), true);
            javaSendMail.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Send mail resetpassword fail :" + e.getMessage());
        }
    }

    @Override
    public void sendEmail(String from, String to, String subject, String text) {
        // MimeMailMessage => send email with media
        // SimpleMailMessage send email with text
        MimeMessage message = javaSendMail.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (Exception e) {
            throw new RuntimeException("Send mail fail :" + e.getMessage());
        }
        // send email
        javaSendMail.send(message);

    }

    @Override
    public String createActiveCode() {
        return UUID.randomUUID().toString();
    }
}
