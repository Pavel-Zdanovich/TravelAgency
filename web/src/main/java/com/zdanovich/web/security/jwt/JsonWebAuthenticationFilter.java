package com.zdanovich.web.security.jwt;

import com.zdanovich.web.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonWebAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String FILTER_APPLIED = JsonWebAuthenticationFilter.class.getName().concat(".APPLIED");

    private final AuthService authService;

    public JsonWebAuthenticationFilter(RequestMatcher requestMatcher, AuthService authService) {
        super(requestMatcher);
        this.authService = authService;
        //this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                logger.info("Authentication success 2");
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return this.authService.restoreAuthentication(request); //authenticate
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        super.successfulAuthentication(req, res, chain, auth);
        logger.info("Authentication success 3");
        req.setAttribute(FILTER_APPLIED, Boolean.TRUE); //TODO
        chain.doFilter(req, res);
    }
}