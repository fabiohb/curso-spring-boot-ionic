package com.fabiohb.cursos.cursomc.services.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.fabiohb.cursos.cursomc.domain.Pedido;
import com.fabiohb.cursos.cursomc.services.EmailService;

public abstract class AbstractEmailService implements EmailService {

	@Value("${mail.default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(message);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(pedido.getCliente().getEmail());
		message.setFrom(sender);
		message.setSubject("Pedido confirmado! Código: " + pedido.getId());
		message.setText(pedido.toEmailString());
		return message;
	}
	
}
