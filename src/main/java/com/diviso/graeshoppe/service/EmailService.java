package com.diviso.graeshoppe.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.github.jhipster.config.JHipsterProperties;

@Service
public class EmailService {
	
	private final Logger log = LoggerFactory.getLogger(EmailService.class);
	
	private final JavaMailSender javaMailSender;
	private final JHipsterProperties jHipsterProperties;
	
	public EmailService(JavaMailSender javaMailSender, JHipsterProperties jHipsterProperties) {
		this.javaMailSender=javaMailSender;
		this.jHipsterProperties=jHipsterProperties;
	}
	
	 
	    public void sendEmail(String to, String subject, String content) {
	        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
	             to, subject, content);

	        // Prepare message using a Spring helper
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        try {
	            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	            message.setTo(to);
	            message.setSubject(subject);
	            message.setText(content);
	            javaMailSender.send(mimeMessage);
	            log.debug("Sent email to User '{}'", to);
	        }  catch (MailException | MessagingException e) {
	            log.warn("Email could not be sent to user '{}'", to, e);
	        }
	    }
}
