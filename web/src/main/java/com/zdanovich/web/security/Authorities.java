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

    public static final String GUEST_ROLE = "GUEST";

    public static final String CREATE_PRIVILEGE = "CREATE_PRIVILEGE";
    public static final String READ_PRIVILEGE = "READ_PRIVILEGE";
    public static final String UPDATE_PRIVILEGE = "UPDATE_PRIVILEGE";
    public static final String DELETE_PRIVILEGE = "DELETE_PRIVILEGE";

    public static final GrantedAuthority CREATE_PRIVILEGE_AUTHORITY = new SimpleGrantedAuthority(CREATE_PRIVILEGE);
    public static final GrantedAuthority READ_PRIVILEGE_AUTHORITY = new SimpleGrantedAuthority(READ_PRIVILEGE);
    public static final GrantedAuthority UPDATE_PRIVILEGE_AUTHORITY = new SimpleGrantedAuthority(UPDATE_PRIVILEGE);
    public static final GrantedAuthority DELETE_PRIVILEGE_AUTHORITY = new SimpleGrantedAuthority(DELETE_PRIVILEGE);

    public static final Map<String, Set<GrantedAuthority>> AUTHORITIES_MAP = new HashMap<String, Set<GrantedAuthority>>() {
        {
            put(GUEST_ROLE, new HashSet<GrantedAuthority>() {
                {
                    add(READ_PRIVILEGE_AUTHORITY);
                }
            });
            put(UserRole.USER.toString(), new HashSet<GrantedAuthority>() {
                {
                    add(CREATE_PRIVILEGE_AUTHORITY);
                    add(READ_PRIVILEGE_AUTHORITY);
                }
            });
            put(UserRole.ADMIN.toString(), new HashSet<GrantedAuthority>() {
                {
                    add(CREATE_PRIVILEGE_AUTHORITY);
                    add(READ_PRIVILEGE_AUTHORITY);
                    add(UPDATE_PRIVILEGE_AUTHORITY);
                    add(DELETE_PRIVILEGE_AUTHORITY);
                }
            });
        }
    };

    private Authorities() {
    }

    public static Collection<? extends GrantedAuthority> getFor(UserRole userRole) {
        return AUTHORITIES_MAP.getOrDefault(userRole.toString(), AUTHORITIES_MAP.get(GUEST_ROLE));
    }

    public static Collection<? extends GrantedAuthority> getFor(String userRole) {
        return AUTHORITIES_MAP.getOrDefault(userRole, AUTHORITIES_MAP.get(GUEST_ROLE));
    }
}