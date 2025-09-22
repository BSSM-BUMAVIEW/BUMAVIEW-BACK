package com.bssm.bumaview.domain.subscription.application.mail;

import com.bssm.bumaview.domain.subscription.application.mail.exception.MailSendFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendSimple(String to, String subject, String text) {

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
        }catch (MailException e){
            throw MailSendFailException.EXCEPTION;
        }
    }
}