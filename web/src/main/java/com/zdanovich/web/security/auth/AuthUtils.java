package com.zdanovich.web.security.auth;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.service.impl.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
@Log4j2
public class AuthUtils implements AuthenticationConverter {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;
    public static final String TOKEN_HEADER = "JWTOKEN";
    private static final String SECRET = "secret";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Override
    public Authentication convert(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = request.getHeader(TOKEN_HEADER);

        if (jwtToken == null) {
            if (authentication == null) {
                throw new AuthenticationServiceException(String.format("Header '%s' is not found", TOKEN_HEADER));
            } else {
                return authentication;
            }
        } else {
            Claims claims = Jwts.parser().
                    setSigningKey(SECRET).
                    parseClaimsJws(jwtToken).
                    getBody();
            String username = claims.getSubject();
            String credentials = "";
            if (authentication != null) {
                if (authentication.getPrincipal().equals(username)) {
                    return authentication;
                } else {
                    throw new AuthenticationServiceException(String.format("User '%s' authenticated instead of you", authentication.getPrincipal()));
                }
            } else {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return generate(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities(), request);
            }
        }
    }

    public Authentication register(String username, String password, HttpServletRequest request) {
        if (userService.findByLogin(username).isPresent()) {
            throw new AuthenticationServiceException(String.format("User '%s' is already registered", username));
        }
        User user = new User();
        user.setLogin(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);
        userService.save(user);
        return generate(username, password, Collections.emptySet(), request);
    }

    public Authentication login(String username, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException(String.format("Wrong password = '%s' for user '%s'", username, password));
        }
        return generate(username, password, userDetails.getAuthorities(), request);
    }

    private Authentication generate(String username, String password, Collection<? extends GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        authentication.setAuthenticated(true);
        return authentication;
    }

    public String generateToken(String username) {
        return Jwts.
                builder().
                setClaims(Jwts.claims()).
                setSubject(username).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)).
                signWith(SignatureAlgorithm.HS512, SECRET).
                compact();
    }
}