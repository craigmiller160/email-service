package io.craigmiller160.emailservice.email;

import io.craigmiller160.emailservice.dto.EmailRequest;
import io.vavr.control.Option;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(final EmailRequest emailRequest) {
        final var message = new SimpleMailMessage();
        Option.of(emailRequest.toAddresses())
                .forEach(addresses -> {
                    final var toAddresses = new String[addresses.size()];
                    addresses.toArray(toAddresses);
                    message.setTo(toAddresses);
                });
        Option.of(emailRequest.ccAddresses())
                .forEach(addresses -> {
                    final var ccAddresses = new String[addresses.size()];
                    addresses.toArray(ccAddresses);
                    message.setCc(ccAddresses);
                });
        Option.of(emailRequest.bccAddresses())
                .forEach(addresses -> {
                    final var bccAddresses = new String[addresses.size()];
                    addresses.toArray(bccAddresses);
                    message.setBcc(bccAddresses);
                });
        message.setSubject(emailRequest.subject());
        message.setText(emailRequest.text());
        javaMailSender.send(message);
    }

}
