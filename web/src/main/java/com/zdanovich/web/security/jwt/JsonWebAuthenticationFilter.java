package com.zdanovich.web.security.jwt;

import com.zdanovich.web.security.WebSecurityConfiguration;
import com.zdanovich.web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonWebAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    @Lazy
    public JsonWebAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(WebSecurityConfiguration.ANY_URL);
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return this.getAuthenticationManager().authenticate(authService.convert(request));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        super.successfulAuthentication(req, res, chain, auth);
        chain.doFilter(req, res);
    }
}