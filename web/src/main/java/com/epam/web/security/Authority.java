package com.epam.web.security;

import com.epam.core.entity.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Authority {

    private static final Map<UserRole, Set<GrantedAuthority>> authorities = Authority();
    private static final GrantedAuthority SEARCH = new SimpleGrantedAuthority("ROLE_SEARCH");

    private static Map<UserRole, Set<GrantedAuthority>> Authority() {
        Map<UserRole, Set<GrantedAuthority>> authorities = new HashMap<>();
        for (UserRole userRole : UserRole.values()) {
            authorities.put(userRole, Collections.singleton(new SimpleGrantedAuthority(userRole.name())));
        }
        return authorities;
    }

    static Set<GrantedAuthority> getAuthority(UserRole userRole) {
        return authorities.getOrDefault(userRole, authorities.get(UserRole.GUEST));
    }

}
