package com.fabiohb.cursos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.fabiohb.cursos.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage message);
	
}
