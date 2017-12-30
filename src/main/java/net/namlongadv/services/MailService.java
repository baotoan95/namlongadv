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
	
	public void sendEmail(String[] tos, String content, String subject) throws Exception {
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(tos);
		helper.setText(content, true);
		helper.setSubject(subject);

//		ClassPathResource file = new ClassPathResource("cat.jpg");
//		helper.addInline("id101", file);

		sender.send(message);
	}
}
