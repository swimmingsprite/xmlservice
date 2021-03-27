package com.swimmingsprite.xmlservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class MailService {
    JavaMailSender mailSender;

    MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // TODO: 26. 3. 2021 generalize sendHtml() text, based on the document type

    public void sendHtml(String to, String html, String subject) {
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
            helper.setText("V prílohe Vám posielam faktúru. ");

            helper.addAttachment("Faktura.html", new ByteArrayResource(html.getBytes(StandardCharsets.UTF_8)));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void sendPdf(String to, byte[] pdfByteArr, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("swisp@pepisandbox.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("V prílohe Vám posielam faktúru. ");

            helper.addAttachment("Faktura.pdf", new ByteArrayResource(pdfByteArr));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
