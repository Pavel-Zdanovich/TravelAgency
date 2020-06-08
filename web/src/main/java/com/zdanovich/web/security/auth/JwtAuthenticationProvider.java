package com.zdanovich.web.security.auth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider, MessageSourceAware, InitializingBean {

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private MessageSource messageSource;
    private UserCache userCache = new NullUserCache();

    @Autowired
    private AuthService authService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userCache, "A user cache must be set");
        Assert.notNull(this.messages, "A message source must be set");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.onlySupports",
                    "Only JsonWebAuthenticationToken is supported");
        }
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                : authentication.getName();
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JsonWebAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messages = new MessageSourceAccessor(messageSource);
    }
}