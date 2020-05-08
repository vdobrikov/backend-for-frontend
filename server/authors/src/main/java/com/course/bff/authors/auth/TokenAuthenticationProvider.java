package com.course.bff.authors.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final AuthTokenValidationService authTokenValidationService;

    public TokenAuthenticationProvider(AuthTokenValidationService authTokenValidationService) {
        this.authTokenValidationService = authTokenValidationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = authentication.getCredentials().toString();
        var principal = authentication.getPrincipal().toString();

        if (!authTokenValidationService.validateTokenSilently(token)) {
            throw new BadCredentialsException(token);
        }

        return new UsernamePasswordAuthenticationToken(principal, token, Collections.emptySet());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
