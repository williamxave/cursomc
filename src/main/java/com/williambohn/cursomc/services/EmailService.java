package com.williambohn.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.williambohn.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
