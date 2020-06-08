package com.zdanovich.web.security.auth;

import com.zdanovich.web.security.WebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    @Lazy
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(WebSecurityConfiguration.DEFAULT_FILTER_PROCESSES_URL);
        setAuthenticationManager(authenticationManager);
        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = authService.convert(request);
        return this.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}