package com.mycompany.fooddelivery.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("email")
public class EmailProperties {
	
	private Implementation implemenation = Implementation.MOCK;
	
	private Sandbox sandbox = new Sandbox();
	
	public enum Implementation {
	    SMTP, MOCK, SANDBOX
	}
	
	@NotNull
	private String sender;
	
	@Getter
	@Setter
	public class Sandbox {
	    
	    private String recipient;
	    
	}
	
}
