package com.fabiohb.cursos.cursomc.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage message) {
		log.info("Enviando envio de email...\n" + message);
		mailSender.send(message);
		log.info("Email enviado...");
	}

	@Override
	public void sendHtmlEmail(MimeMessage message) {
		log.info("Enviando envio de email HTML...\n" + message);
		javaMailSender.send(message);
		log.info("Email enviado...");
	}

}
