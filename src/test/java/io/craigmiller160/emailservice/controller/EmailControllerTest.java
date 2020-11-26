package io.craigmiller160.emailservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import io.craigmiller160.apitestprocessor.ApiTestProcessor;
import io.craigmiller160.apitestprocessor.config.AuthType;
import io.craigmiller160.apitestprocessor.config.SetupConfig;
import io.craigmiller160.emailservice.config.EmailConfig;
import io.craigmiller160.emailservice.email.EmailService;
import io.craigmiller160.emailservice.testutils.JwtUtils;
import io.craigmiller160.oauth2.config.OAuthConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.security.KeyPair;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EmailControllerTest {

    private static KeyPair keyPair;
    private static JWKSet jwkSet;

    @MockBean
    private OAuthConfig oAuthConfig;
    @MockBean
    private EmailConfig emailConfig;
    @MockBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private ApiTestProcessor apiProcessor;
    private String token;

    @BeforeAll
    public static void keySetup() throws Exception {
        keyPair = JwtUtils.createKeyPair();
        jwkSet = JwtUtils.createJwkSet(keyPair);
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        when(oAuthConfig.getJwkSet())
                .thenReturn(jwkSet);
        when(oAuthConfig.getClientKey())
                .thenReturn(JwtUtils.CLIENT_KEY);
        when(oAuthConfig.getClientName())
                .thenReturn(JwtUtils.CLIENT_NAME);

        final var jwt = JwtUtils.createJwt();
        token = JwtUtils.signAndSerializeJwt(jwt, keyPair.getPrivate());

        apiProcessor = new ApiTestProcessor(setupConfig -> {
            setupConfig.setMockMvc(mockMvc);
            setupConfig.setObjectMapper(objectMapper);
            setupConfig.auth(authConfig -> {
                authConfig.setType(AuthType.BEARER);
                authConfig.setBearerToken(token);
            });
        });
    }

    @Test
    public void test_sendEmail() {
        throw new RuntimeException();
    }

    @Test
    public void test_sendEmail_unauthorized() {
        throw new RuntimeException();
    }

}
