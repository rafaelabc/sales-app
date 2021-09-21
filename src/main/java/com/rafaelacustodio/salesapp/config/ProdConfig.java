package com.rafaelacustodio.salesapp.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rafaelacustodio.salesapp.services.DBService;
import com.rafaelacustodio.salesapp.services.EmailService;
import com.rafaelacustodio.salesapp.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
		

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
