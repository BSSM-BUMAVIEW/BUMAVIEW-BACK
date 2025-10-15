package com.bssm.bumaview.domain.subscription.application.mail;

import com.bssm.bumaview.domain.subscription.application.mail.exception.MailSendFailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendHtml(String to, String subject, String html) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(mimeMessage);
            log.info("✅ 메일 전송 성공: {}", to);
        } catch (MessagingException | MailException e) {
            log.error("📛 메일 전송 실패: {}", e.getMessage(), e);
            throw MailSendFailException.EXCEPTION;
        }
    }
}