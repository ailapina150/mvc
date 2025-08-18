package com.resume.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);
        mailSender.send(message);
    }

    public void sendMailWithAttachment(String to, String subject, String text, String attachmentPath) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            // Используем помощник для создания multipart сообщения
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true
            );

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom(from);

            // Добавляем вложение
            FileSystemResource file = new FileSystemResource(
                    new File(attachmentPath)
            );
            helper.addAttachment(file.getFilename(), file);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке письма с вложением", e);
        }
    }

}

