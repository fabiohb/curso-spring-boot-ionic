package com.fabiohb.cursos.cursomc.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage message) {
		log.info("Enviando envio de email...\n" + message);
		mailSender.send(message);
		log.info("Email enviado...");
	}

}
