package com.rafaelacustodio.salesapp.services;

import org.springframework.mail.SimpleMailMessage;

import com.rafaelacustodio.salesapp.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
