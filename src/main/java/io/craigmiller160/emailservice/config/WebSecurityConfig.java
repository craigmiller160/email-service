package io.craigmiller160.emailservice.config;

import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()
        .requiresChannel()
        .anyRequest()
        .requiresSecure()
        .and()
        .authorizeRequests()
        .antMatchers("/actuator/health")
        .permitAll()
        .antMatchers("/**")
        .hasAnyRole("access");
  }
}
