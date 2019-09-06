/*
package com.epam.web.config;

import com.epam.core.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

class Authority {

    private static final Map<UserRole, GrantedAuthority> authorities = Authority();

    private static Map<UserRole, GrantedAuthority> Authority() {
        Map<UserRole, GrantedAuthority> authorities = new HashMap<>();
        for (UserRole userRole : UserRole.values()) {
            authorities.put(userRole, new SimpleGrantedAuthority(userRole.name()));
        }
        return authorities;
    }

    static Collection<GrantedAuthority> getAuthority(UserRole userRole) {
        return List.of(authorities.getOrDefault(userRole, authorities.get(UserRole.MEMBER)));
    }

}
*/
