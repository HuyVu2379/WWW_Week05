package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.services.EmailService;
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("narurouchiha10@gmail.com"); // Địa chỉ email gửi đi

        javaMailSender.send(message);
    }
}
