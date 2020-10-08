package com.zdanovich.web.security.jwt;

import com.zdanovich.web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonWebAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String ALREADY_FILTERED_ATTR_NAME = JsonWebAuthenticationFilter.class.getName().concat(".FILTERED");

    @Autowired
    private AuthService authService;

    @Autowired
    @Lazy
    public JsonWebAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(AnyRequestMatcher.INSTANCE);
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                logger.info("Authentication success 2");
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authService.filter(request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        super.successfulAuthentication(req, res, chain, auth);
        logger.info("Authentication success 3");
        req.setAttribute(ALREADY_FILTERED_ATTR_NAME, Boolean.TRUE);
        chain.doFilter(req, res);
    }
}