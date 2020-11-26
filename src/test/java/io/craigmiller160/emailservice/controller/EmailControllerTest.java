package io.craigmiller160.emailservice.controller;

import io.craigmiller160.emailservice.config.EmailConfig;
import io.craigmiller160.emailservice.email.EmailService;
import io.craigmiller160.oauth2.config.OAuthConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EmailControllerTest {

    @MockBean
    private OAuthConfig oAuthConfig;
    @MockBean
    private EmailConfig emailConfig;
    @MockBean
    private EmailService emailService;

    @Test
    public void test_sendEmail() {
        throw new RuntimeException();
    }

    @Test
    public void test_sendEmail_unauthorized() {
        throw new RuntimeException();
    }

}
