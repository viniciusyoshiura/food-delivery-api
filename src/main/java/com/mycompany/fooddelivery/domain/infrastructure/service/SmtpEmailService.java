package com.mycompany.fooddelivery.domain.infrastructure.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mycompany.fooddelivery.core.email.EmailProperties;
import com.mycompany.fooddelivery.domain.infrastructure.service.exception.EmailException;
import com.mycompany.fooddelivery.domain.service.MailSendingService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEmailService implements MailSendingService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void send(Message message) {
		
		try {
			
			String body = processTemplate(message);
			
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getSender());
			helper.setTo(message.getRecipients().toArray(new String[0]));
			helper.setSubject(message.getSubject());
			helper.setText(body, true);
			
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("It was not possible to send e-mail", e);
		}
		
	}
	
	private String processTemplate(Message message) {
		try {
			Template template = freemarkerConfig.getTemplate(message.getBody());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(
					template, message.getVariables());
		} catch (Exception e) {
			throw new EmailException("It was not possible to build the e-mail template", e);
		}
	}

}
