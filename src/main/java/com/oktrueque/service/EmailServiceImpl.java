package com.oktrueque.service;

import com.oktrueque.model.Email;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String MESSAGE= "Error sending email";
    private static final String SUCCESS= "Email send";

    private final JavaMailSender javaMailSender;

    @Autowired
    private Configuration fmConfiguration;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendMail(Email mail, String template) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setTo(mail.getMailTo());
            mail.setMailContent(geContentFromTemplate(mail.getModel(), template));
            mimeMessageHelper.setText(mail.getMailContent(), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
           LOGGER.warn(MESSAGE, e);
        }
        LOGGER.info(SUCCESS);
    }

    private String geContentFromTemplate(Map < String, Object > model, String template) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(fmConfiguration.getTemplate(template), model));
        } catch (Exception e) {
            LOGGER.warn(MESSAGE, e);
        }
        return content.toString();
    }

    @Override
    public ResponseEntity contact(Email email){
        Map<String,Object> model = new LinkedHashMap<>();
        model.put("emailContent",email.getMailContent());
        email.setModel(model);
        sendMail(email,"contactUs.ftl");
        return new ResponseEntity(HttpStatus.OK);
    }
}