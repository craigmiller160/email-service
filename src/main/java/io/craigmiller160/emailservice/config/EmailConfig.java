package io.craigmiller160.emailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
public class EmailConfig {

  private final String host;
  private final int port;
  private final String username;
  private final String password;
  private final String mailAuth;
  private final String mailSsl;
  private final String mailStartTls;
  private final String debug;

  public EmailConfig(
      @Value("${spring.mail.host}") final String host,
      @Value("${spring.mail.port}") final int port,
      @Value("${spring.mail.username}") final String username,
      @Value("${spring.mail.password}") final String password,
      @Value("${spring.mail.properties.mail.smtp.auth}") final String mailAuth,
      @Value("${spring.mail.properties.mail.smtp.ssl.enable}") final String mailSsl,
      @Value("${spring.mail.properties.mail.smtp.starttls.enable}") final String mailStartTls,
      @Value("${spring.mail.properties.mail.smtp.debug}") final String debug) {
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.mailAuth = mailAuth;
    this.mailSsl = mailSsl;
    this.mailStartTls = mailStartTls;
    this.debug = debug;
  }

  @Bean
  public JavaMailSender javaMailSender() {
    final var mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    final var props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", mailAuth);
    props.put("mail.smtp.starttls.enable", mailStartTls);
    props.put("mail.smtp.ssl.enable", mailSsl);
    props.put("mail.debug", debug);

    return mailSender;
  }
}
