package com.zdanovich.web.security.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.POJONode;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.service.impl.UserService;
import com.zdanovich.core.utils.Utils;
import com.zdanovich.web.security.Authorities;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@Component
@Log4j2
public class AuthService implements AuthenticationConverter {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;
    public static final String TOKEN_HEADER = "JWTOKEN";
    private static final String SECRET = "secret";

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Override
    public Authentication convert(HttpServletRequest request) {
        String jwtToken = request.getHeader(TOKEN_HEADER);
        if (jwtToken == null) {
            throw new AuthenticationServiceException(String.format("Header '%s' is not found", TOKEN_HEADER));
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jws<Claims> claimsJws = Jwts.parser().
                    setSigningKey(SECRET).
                    parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            String username = claims.getSubject();
            if (authentication != null) {
                if (authentication.getName() == null) {
                    throw new AuthenticationServiceException("Authentication lost name");
                }
                if (!authentication.getName().equals(username)) {
                    throw new AuthenticationServiceException(String.format("Authentication name '%s' is differ from token username '%s'",
                            authentication.getName(), username));
                } else {
                    return authentication;
                }
            } else {
                log.error("Authentication is missed");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return generate(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities(), request);
            }
        }
    }

    public Authentication register(HttpServletRequest request) {
        JsonNode jsonNode = obtain(request);
        String username = jsonNode.findValue(Utils.USERNAME).asText();
        String password = jsonNode.findValue(Utils.PASSWORD).asText();
        if (userService.findByLogin(username).isPresent()) {
            throw new AuthenticationServiceException(String.format("User '%s' is already registered", username));
        }
        User user = new User();
        user.setLogin(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        userService.save(user);
        return generate(username, password, Authorities.getFor(UserRole.USER), request);
    }

    public Authentication login(HttpServletRequest request) {
        JsonNode jsonNode = obtain(request);
        String username = jsonNode.findValue(Utils.USERNAME).asText();
        String password = jsonNode.findValue(Utils.PASSWORD).asText();
        UserDetails userDetails = checkInMemory(username, password);
        return generate(username, password, userDetails.getAuthorities(), request);
    }

    private JsonNode obtain(HttpServletRequest request) {
        try {
            return objectMapper.readTree(request.getReader());
        } catch (IOException e) {
            log.error("Error occurred during finding '%s' in the request");
        }
        return new POJONode(null);
    }

    private UserDetails checkInMemory(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(String.format("Wrong password = '%s' for user '%s'", username, password));
        }
        return userDetails;
    }

    private Authentication generate(String username, String password, Collection<? extends GrantedAuthority> authorities, HttpServletRequest request) {
        JsonWebAuthenticationToken authentication = new JsonWebAuthenticationToken(username, password, authorities);
        authentication.setDetails(authenticationDetailsSource.buildDetails(request));
        return authentication;
    }

    public String generateToken(String username) {
        return Jwts.
                builder().
                //setClaims(Jwts.claims()).
                setSubject(username).
                //setHeaderParam(Utils.PASSWORD, password).
                setIssuedAt(new Date()).
                //setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)).
                signWith(SignatureAlgorithm.HS512, SECRET).
                compact();
    }
}