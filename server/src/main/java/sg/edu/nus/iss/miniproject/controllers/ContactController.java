package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.miniproject.models.ContactForm;

@RestController
public class ContactController  {
    
    private final JavaMailSender emailSender;

    @Autowired
    public ContactController(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/contact")
    public void sendContactEmail(@RequestBody ContactForm contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(contactForm.getEmail());
        message.setTo("foodalchemytfip@gmail.com");
        message.setSubject("New Contact Form Submission");
        message.setText("Name: " + contactForm.getName() + "\n\n" +
                        "Email: " + contactForm.getEmail() + "\n\n" +
                        "Message: " + contactForm.getMessage());

        emailSender.send(message);
    }
}
