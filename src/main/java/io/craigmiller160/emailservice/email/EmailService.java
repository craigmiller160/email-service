package io.craigmiller160.emailservice.email;

import io.craigmiller160.emailservice.dto.EmailRequest;
import io.craigmiller160.emailservice.exception.SendEmailException;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Try<Void> sendEmail(final EmailRequest emailRequest) {
        log.info("Preparing to send email");
        log.debug("To: {}, CC: {}, BCC: {}, Subject: {}",
                emailRequest.toAddresses(),
                emailRequest.ccAddresses(),
                emailRequest.bccAddresses(),
                emailRequest.subject()
        );
        return Try.run(() -> {
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
            log.info("Email sent successfully");
        })
                .recoverWith(ex -> {
                    final var message = String.format("SMTP Error: %s - %s", ex.getClass().getName(), ex.getMessage());
                    return Try.failure(new SendEmailException(message, ex));
                });
    }

}
