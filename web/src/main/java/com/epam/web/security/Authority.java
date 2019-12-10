package com.epam.web.security;

import com.epam.core.entity.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

class Authority {

    private static final Map<String, Set<GrantedAuthority>> authorities = Authority();
    private static final String GUEST_ROLE = "GUEST";
    private static final GrantedAuthority CREATE = new SimpleGrantedAuthority("CREATE_PRIVILEGE");
    private static final GrantedAuthority SEARCH = new SimpleGrantedAuthority("SEARCH_PRIVILEGE");
    private static final GrantedAuthority UPDATE = new SimpleGrantedAuthority("UPDATE_PRIVILEGE");
    private static final GrantedAuthority DELETE = new SimpleGrantedAuthority("DELETE_PRIVILEGE");

    private static Map<String, Set<GrantedAuthority>> Authority() {
        Map<String, Set<GrantedAuthority>> authorities = new HashMap<>();
        authorities.put(GUEST_ROLE, new HashSet<GrantedAuthority>() {
            {
                add(SEARCH);
            }
        });
        authorities.put(UserRole.USER.toString(), new HashSet<GrantedAuthority>() {
            {
                add(CREATE);
                add(SEARCH);
            }
        });
        authorities.put(UserRole.ADMIN.toString(), new HashSet<GrantedAuthority>() {
            {
                add(CREATE);
                add(SEARCH);
                add(UPDATE);
                add(DELETE);
            }
        });
        return authorities;
    }

    static Set<GrantedAuthority> getAuthority(UserRole userRole) {
        return authorities.getOrDefault(userRole.toString(), authorities.get(GUEST_ROLE));
    }

    static Set<GrantedAuthority> getAuthority(String userRole) {
        return authorities.getOrDefault(userRole, authorities.get(GUEST_ROLE));
    }

}
