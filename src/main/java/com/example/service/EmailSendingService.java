package com.example.service;

import com.example.dto.ApiResponse;
import com.example.enums.EmailType;
import com.example.exp.AppBadException;
import com.example.repository.EmailHistoryRepository;
import com.example.util.JwtUtil;
import com.example.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String serverDomain;

    @Value("${server.port}")
    private String serverPort;

    private final EmailHistoryService emailHistoryService;
    private final EmailHistoryRepository emailHistoryRepository;
    private final JavaMailSender mailSender;


    public void sendRegistrationEmail(String email, Long profileId) {
        String subject = "Complete registration";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        .button:hover {\n" +
                "            background-color: #dd4444;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Complete registration verification</h1>\n" +
                "<p>Please click to button for completing registration: <a style=\"padding: 10px 30px;\n" +
                "display: inline-block;\n" +
                "text-decoration: none;\n" +
                "collapse: white;\n" +
                "background-color:  indianred;\" href=\"%s:%s/auth/verification/%s\"\n" +
                "target=\"_blank\"> Click\n" +
                "    there</a></p>\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body, serverDomain,serverPort, JwtUtil.encode(profileId, email));
        sendMimeEmail(email, subject, body);
    }

// We will continue this code in the reset API
    public void sentResetPasswordEmail(String username) {
        String code = RandomUtil.getRandomCode();
        String subject = "Reset password Conformation";
        String body = "How are you. This is confirm code reset password send code: " + code;
        checkAndSendMineEmail(username, subject, body,code);
    }

    private void checkAndSendMineEmail(String email, String subject, String body, String code) {
        Long count = emailHistoryService.getEmailCount(email);
        if (count >= 3) {
           throw  new AppBadException("email reached sms");
        }

        sendMimeEmail(email, subject, body);
        emailHistoryService.create(email, code, EmailType.RESET_PASSWORD);
    }

    private void sendMimeEmail(String email, String subject, String body) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            CompletableFuture.runAsync(() -> {
                mailSender.send(msg);
            });
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

   /* public void send(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendSimpleEmail(String email, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAccount);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
    }*/
}
