package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendEmail(String to, String subject, String body);
}
