package net.namlongadv.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	private JavaMailSender sender;
	
	public void sendEmail() throws Exception {
		MimeMessage message = sender.createMimeMessage();

		// Enable the multiple part flag!
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo("baotoan.95@gmail.com");
		helper.setText("<html><body>Here is a cat picture! <img src='cid:id101'/><body></html>", true);
		helper.setSubject("Hi");

//		ClassPathResource file = new ClassPathResource("cat.jpg");
//		helper.addInline("id101", file);

		sender.send(message);
	}
}
