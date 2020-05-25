package com.rhinodevs.crudbackend.services;

import org.springframework.mail.SimpleMailMessage;

import com.rhinodevs.crudbackend.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
