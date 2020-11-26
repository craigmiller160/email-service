package io.craigmiller160.emailservice.controller;

import io.craigmiller160.emailservice.dto.EmailRequest;
import io.craigmiller160.emailservice.email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(final EmailService emailService) {
        this.emailService = emailService;
    }

    public ResponseEntity<Void> sendEmail(final EmailRequest emailRequest) {
        // TODO finish this
        return null;
    }

}
