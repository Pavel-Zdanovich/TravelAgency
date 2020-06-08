package com.zdanovich.web.security;

import com.zdanovich.core.entity.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Authorities {// AuthoritiesMapper, AuthoritiesUtils TODO

    private static final String GUEST_ROLE = "GUEST";

    private static final GrantedAuthority CREATE_PRIVILEGE = new SimpleGrantedAuthority("CREATE_PRIVILEGE");
    private static final GrantedAuthority READ_PRIVILEGE = new SimpleGrantedAuthority("READ_PRIVILEGE");
    private static final GrantedAuthority UPDATE_PRIVILEGE = new SimpleGrantedAuthority("UPDATE_PRIVILEGE");
    private static final GrantedAuthority DELETE_PRIVILEGE = new SimpleGrantedAuthority("DELETE_PRIVILEGE");

    private static final Map<String, Set<GrantedAuthority>> AUTHORITIES_MAP = new HashMap<String, Set<GrantedAuthority>>() {
        {
            put(GUEST_ROLE, new HashSet<GrantedAuthority>() {
                {
                    add(READ_PRIVILEGE);
                }
            });
            put(UserRole.USER.toString(), new HashSet<GrantedAuthority>() {
                {
                    add(CREATE_PRIVILEGE);
                    add(READ_PRIVILEGE);
                }
            });
            put(UserRole.ADMIN.toString(), new HashSet<GrantedAuthority>() {
                {
                    add(CREATE_PRIVILEGE);
                    add(READ_PRIVILEGE);
                    add(UPDATE_PRIVILEGE);
                    add(DELETE_PRIVILEGE);
                }
            });
        }
    };

    private Authorities() {
    }

    public static Collection<? extends GrantedAuthority> getFor(UserRole userRole) {
        return AUTHORITIES_MAP.getOrDefault(userRole.toString(), AUTHORITIES_MAP.get(GUEST_ROLE));
    }
}