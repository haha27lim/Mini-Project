package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import sg.edu.nus.iss.miniproject.models.ContactForm;

@RestController
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private JavaMailSender emailSender;

    public ContactController(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/api/contact")
    public ResponseEntity<Void> sendContactEmail(@RequestBody ContactForm contactForm) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(contactForm.getEmail());
        helper.setTo("foodalchemytfip@gmail.com");
        helper.setSubject("Contact Form Submission");

        String content = "<b>Name:</b> " + contactForm.getName() + "<br>"
                + "<b>Email:</b> " + contactForm.getEmail() + "<br>"
                + "<b>Subject:</b> " + contactForm.getSubject() + "<br><br>"
                + "<b>Message:</b><br>" + contactForm.getMessage();

        helper.setText(content, true);

        emailSender.send(mimeMessage);

        return ResponseEntity.ok().build();
    }

}

