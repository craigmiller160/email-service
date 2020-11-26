package io.craigmiller160.emailservice.email;

import io.craigmiller160.emailservice.dto.EmailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(final EmailRequest emailRequest) {
        final var message = new SimpleMailMessage();
        // TODO finish this
    }

}
