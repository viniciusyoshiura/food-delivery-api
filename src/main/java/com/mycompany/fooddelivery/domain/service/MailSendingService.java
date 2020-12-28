package com.mycompany.fooddelivery.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface MailSendingService {

	void send(Message message);

	@Getter
	@Builder
	class Message {

		// ---------- Destiny
		// ---------- @Singular - singularize the recipients
		@Singular
		private Set<String> recipients;
		
		// ---------- Throws execption case this attribute is null
		@NonNull
		private String subject;
		
		@NonNull
		private String body;
		
		@Singular("variable")
		private Map<String, Object> variables;
		
	}

}
