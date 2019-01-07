package com.fabiohb.cursos.cursomc.services.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fabiohb.cursos.cursomc.domain.Pedido;
import com.fabiohb.cursos.cursomc.services.EmailService;

public abstract class AbstractEmailService implements EmailService {

	@Value("${mail.default.sender}")
	private String sender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TemplateEngine engine;

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(message);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage message = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(message);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(pedido.getCliente().getEmail());
		message.setFrom(sender);
		message.setSubject("Pedido confirmado! Código: " + pedido.getId());
		message.setText(pedido.toEmailString());
		return message;
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		MimeMessageHelper mmh = new MimeMessageHelper(message, true);
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mmh.setText(htmlFromTemplatePedido(pedido), true);
		
		return message;
	}
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);

		return engine.process("email/confirmacaoPedido", context);
	}
	
}
