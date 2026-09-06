package com.example.webapp.service;

import com.example.webapp.config.Environment;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class MailService {
    public void sendOtp(String email, String otp, String purpose) {
        try {
            String username = Environment.required("MAIL_USERNAME");
            String password = Environment.required("MAIL_PASSWORD");
            Properties p = new Properties();
            p.setProperty("mail.smtp.host", Environment.get("MAIL_HOST", "smtp.gmail.com"));
            String port = Environment.get("MAIL_PORT", "587");
            p.setProperty("mail.smtp.port", port);
            p.setProperty("mail.smtp.auth", "true");
            p.setProperty("mail.smtp.ssl.enable", Boolean.toString("465".equals(port)));
            p.setProperty("mail.smtp.starttls.enable", Boolean.toString(!"465".equals(port)));
            p.setProperty("mail.smtp.starttls.required", Boolean.toString(!"465".equals(port)));
            p.setProperty("mail.smtp.ssl.checkserveridentity", "true");
            p.setProperty("mail.smtp.connectiontimeout", "10000");
            p.setProperty("mail.smtp.timeout", "10000");
            p.setProperty("mail.smtp.writetimeout", "10000");
            Session session =
                    Session.getInstance(
                            p,
                            new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Environment.get("MAIL_FROM", username)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(purpose, "UTF-8");
            message.setText(
                    "Mã OTP của bạn: " + otp + "\nCó hiệu lực 5 phút. Không chia sẻ mã này.",
                    "UTF-8");
            Transport.send(message);
        } catch (MessagingException | IllegalStateException e) {
            throw new ValidationException(
                    "email",
                    "Không gửi được email. Hãy kiểm tra cấu hình SMTP và thử gửi lại OTP.");
        }
    }
}
