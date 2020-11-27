package io.craigmiller160.emailservice.controller;

import io.craigmiller160.emailservice.dto.EmailRequest;
import io.craigmiller160.emailservice.email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(final EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody final EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest).get();
        return ResponseEntity.noContent().build();
    }

}
