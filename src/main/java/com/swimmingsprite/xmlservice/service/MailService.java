package com.swimmingsprite.xmlservice.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
public class MailService {
    JavaMailSender mailSender;

    MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // TODO: 29. 3. 2021 REFACTOR parameters to single object
    public void sendWithAttachment(String to, byte[] attachment, String attName, String msg, String subject) {
     /*   SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("swisp@pepisandbox.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(html);
        mailSender.send(message);

*/

        /*try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = html;
            mimeMessage.setContent(htmlMsg, "text/html");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("swisp@pepisandbox.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }*/


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("swisp@pepisandbox.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);

            helper.addAttachment(attName, new ByteArrayResource(attachment));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
