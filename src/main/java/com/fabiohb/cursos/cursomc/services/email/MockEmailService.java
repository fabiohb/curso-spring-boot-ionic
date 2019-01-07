package com.fabiohb.cursos.cursomc.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockEmailService extends AbstractEmailService {
	
	@Override
	public void sendEmail(SimpleMailMessage message) {
		log.info("Simulando envio de email...\n" + message);
		log.info("Email enviado...");
	}

	@Override
	public void sendHtmlEmail(MimeMessage message) {
		log.info("Simulando envio de email HTML...\n" + message);
		log.info("Email enviado...");
	}

}
