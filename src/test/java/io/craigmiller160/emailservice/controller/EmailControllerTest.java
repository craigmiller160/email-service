package io.craigmiller160.emailservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.craigmiller160.apitestprocessor.ApiTestProcessor;
import io.craigmiller160.apitestprocessor.body.Json;
import io.craigmiller160.apitestprocessor.config.AuthType;
import io.craigmiller160.emailservice.config.EmailConfig;
import io.craigmiller160.emailservice.dto.EmailRequest;
import io.craigmiller160.emailservice.email.EmailService;
import io.craigmiller160.emailservice.testutils.JwtUtils;
import io.craigmiller160.testcontainers.common.TestcontainersExtension;
import io.craigmiller160.testcontainers.common.core.AuthenticationHelper;
import io.vavr.control.Try;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith({TestcontainersExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
public class EmailControllerTest {

  private static final String SUBJECT = "The Subject";
  private static final String MESSAGE = "This is the message";
  private static final String TO_1 = "one@gmail.com";
  private static final String TO_2 = "two@gmail.com";
  private static final String CC_1 = "three@gmail.com";
  private static final String CC_2 = "four@gmail.com";
  private static final String BCC_1 = "five@gmail.com";
  private static final String BCC_2 = "six@gmail.com";

  @MockBean private EmailConfig emailConfig;
  @MockBean private EmailService emailService;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  private ApiTestProcessor apiProcessor;
  private static String token;

  @BeforeAll
  public static void tokenSetup() {
    final var authHelper = new AuthenticationHelper();
    final var user = authHelper.createUser("user_%s@gmail.com");
    token = authHelper.login(user).getToken();
  }

  @BeforeEach
  public void beforeEach() throws Exception {
    final var jwt = JwtUtils.createJwt();

    apiProcessor =
        new ApiTestProcessor(
            setupConfig -> {
              setupConfig.setMockMvc(mockMvc);
              setupConfig.setObjectMapper(objectMapper);
              setupConfig.setSecure(true);
              setupConfig.auth(
                  authConfig -> {
                    authConfig.setType(AuthType.BEARER);
                    authConfig.setBearerToken(token);
                  });
            });
  }

  @Test
  public void test_sendEmail() throws Exception {
    final var emailRequest =
        new EmailRequest(
            List.of(TO_1, TO_2), List.of(CC_1, CC_2), List.of(BCC_1, BCC_2), SUBJECT, MESSAGE);

    when(emailService.sendEmail(emailRequest)).thenReturn(Try.success(null));

    final var result =
        apiProcessor.call(
            apiConfig -> {
              apiConfig.request(
                  requestConfig -> {
                    requestConfig.setPath("/email");
                    requestConfig.setMethod(HttpMethod.POST);
                    requestConfig.setBody(new Json(emailRequest));
                  });
              apiConfig.response(
                  responseConfig -> {
                    responseConfig.setStatus(204);
                  });
            });

    assertEquals(204, result.getResponse().getStatus());
    verify(emailService, times(1)).sendEmail(emailRequest);
  }

  @Test
  public void test_sendEmail_unauthorized() {
    final var emailRequest =
        new EmailRequest(
            List.of(TO_1, TO_2), List.of(CC_1, CC_2), List.of(BCC_1, BCC_2), SUBJECT, MESSAGE);

    when(emailService.sendEmail(emailRequest)).thenReturn(Try.success(null));

    apiProcessor.call(
        apiConfig -> {
          apiConfig.request(
              requestConfig -> {
                requestConfig.setPath("/email");
                requestConfig.setMethod(HttpMethod.POST);
                requestConfig.setBody(new Json(emailRequest));
                requestConfig.overrideAuth(
                    authConfig -> {
                      authConfig.setType(AuthType.NONE);
                    });
              });
          apiConfig.response(
              responseConfig -> {
                responseConfig.setStatus(401);
              });
        });

    verify(emailService, times(0)).sendEmail(emailRequest);
  }
}
