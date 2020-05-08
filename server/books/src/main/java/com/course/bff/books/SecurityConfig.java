package com.course.bff.books;

import com.course.bff.books.auth.TokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@EnableWebSecurity
@ComponentScan("com.course.bff.books.auth")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationProvider authenticationProvider;

    public SecurityConfig(TokenAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider);
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
