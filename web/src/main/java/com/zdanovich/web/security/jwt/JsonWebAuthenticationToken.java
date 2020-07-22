package com.zdanovich.web.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JsonWebAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;
    private String credentials;
    private String token;

    public JsonWebAuthenticationToken(String principal, String credentials, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public String getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
