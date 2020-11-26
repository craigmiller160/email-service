package io.craigmiller160.emailservice.email;

import io.craigmiller160.emailservice.dto.EmailRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    private static final String SUBJECT = "The Subject";
    private static final String MESSAGE = "This is the message";
    private static final String TO_1 = "one@gmail.com";
    private static final String TO_2 = "two@gmail.com";
    private static final String CC_1 = "three@gmail.com";
    private static final String CC_2 = "four@gmail.com";
    private static final String BCC_1 = "five@gmail.com";
    private static final String BCC_2 = "six@gmail.com";

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailService emailService;

    @Test
    public void test_sendEmail() {
        final var emailRequest = new EmailRequest(
                List.of(TO_1, TO_2),
                List.of(CC_1, CC_2),
                List.of(BCC_1, BCC_2),
                SUBJECT,
                MESSAGE
        );
        emailService.sendEmail(emailRequest);

        final var captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1))
                .send(captor.capture());

        final var message = captor.getValue();
        assertNotNull(message);
        assertThat(message, allOf(
                hasProperty("subject", equalTo(SUBJECT)),
                hasProperty("text", equalTo(MESSAGE)),
                hasProperty("to", equalTo(new String[] {TO_1, TO_2})),
                hasProperty("cc", equalTo(new String[] {CC_1, CC_2})),
                hasProperty("bcc", equalTo(new String[] {BCC_1, BCC_2}))
        ));
    }

}
