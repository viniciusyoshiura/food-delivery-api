package com.mycompany.fooddelivery.domain.infrastructure.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mycompany.fooddelivery.core.email.EmailProperties;

public class SandboxMailService extends SmtpEmailService {

    @Autowired
    private EmailProperties emailProperties;
    
    @Override
    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = super.createMimeMessage(message);
        
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getRecipient());
        
        return mimeMessage;
    } 
}
