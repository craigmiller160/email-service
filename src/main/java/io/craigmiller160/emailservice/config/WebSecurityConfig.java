package io.craigmiller160.emailservice.config;

import io.craigmiller160.oauth2.security.JwtValidationFilterConfigurer;
import io.craigmiller160.webutils.security.AuthEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthEntryPoint authEntryPoint;
    private final JwtValidationFilterConfigurer jwtValidationFilterConfigurer;

    public WebSecurityConfig(final AuthEntryPoint authEntryPoint,
                             final JwtValidationFilterConfigurer jwtValidationFilterConfigurer) {
        this.authEntryPoint = authEntryPoint;
        this.jwtValidationFilterConfigurer = jwtValidationFilterConfigurer;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requiresChannel().anyRequest().requiresSecure()
                .and()
                .authorizeRequests()
                .antMatchers(jwtValidationFilterConfigurer.getInsecurePathPatterns()).permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .apply(jwtValidationFilterConfigurer)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
    }

}
