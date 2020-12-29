package com.mycompany.fooddelivery.domain.infrastructure.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockMailService extends SmtpEmailService {

    @Override
    public void send(Message message) {
        String body = processTemplate(message);

        log.info("[MOCK E-MAIL] For: {}\n{}", message.getRecipients(), body);
    }
}
