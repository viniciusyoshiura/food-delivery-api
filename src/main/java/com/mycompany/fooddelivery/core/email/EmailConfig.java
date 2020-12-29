package com.mycompany.fooddelivery.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mycompany.fooddelivery.domain.infrastructure.service.MockMailService;
import com.mycompany.fooddelivery.domain.infrastructure.service.SandboxMailService;
import com.mycompany.fooddelivery.domain.infrastructure.service.SmtpEmailService;
import com.mycompany.fooddelivery.domain.service.MailSendingService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public MailSendingService envioEmailService() {
		switch (emailProperties.getImplemenation()) {
			case MOCK:
				return new MockMailService();
			case SMTP:
				return new SmtpEmailService();
			case SANDBOX:
			    return new SandboxMailService();	
			default:
				return null;
		}
	}
	
}
