package com.zdanovich.web.security.auth;

import com.zdanovich.web.security.WebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    public JwtAuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
        super(WebSecurityConfiguration.DEFAULT_FILTER_PROCESSES_URL);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return getAuthenticationManager().authenticate(authUtils.convert(request));
    }
}