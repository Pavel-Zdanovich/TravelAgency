package com.zdanovich.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.service.impl.UserService;
import com.zdanovich.core.utils.CoreUtils;
import com.zdanovich.web.security.Authorities;
import com.zdanovich.web.security.jwt.JsonWebAuthenticationFilter;
import com.zdanovich.web.security.jwt.JsonWebAuthenticationToken;
import com.zdanovich.web.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
public class AuthService extends DaoAuthenticationProvider {

    private static final AuthenticationDetailsSource<HttpServletRequest, ?> AUTHENTICATION_DETAILS_SOURCE = new WebAuthenticationDetailsSource();

    private static final String PASSWORD_CLAIM = "pass";
    private static final String ROLE_CLAIM = "role";

    @Autowired
    private Properties webProperties;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @Override
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        logger.info("Authentication success 1");
        return authentication;
    }

    public Authentication restoreAuthentication(HttpServletRequest request) {
        String token = request.getHeader(webProperties.getProperty(WebUtils.JWT_HEADER_NAME));
        if (token == null) {
            logger.info(String.format("Header '%s' is not found. Authorize as anonymous", webProperties.getProperty(WebUtils.JWT_HEADER_NAME)));
            return new AnonymousAuthenticationToken("key", "anonymous", Authorities.getFor(Authorities.GUEST_ROLE));
        } else {
            Jws<Claims> claimsJws = null;
            try {
                claimsJws = Jwts.parser().setSigningKey(webProperties.getProperty(WebUtils.JWT_SERVICE_SECRET)).parseClaimsJws(token);
            } catch (ExpiredJwtException e) {
                throw new AuthenticationServiceException("Expired jwt", e);
            } catch (UnsupportedJwtException e) {
                throw new AuthenticationServiceException("Unsupported jwt", e);
            } catch (MalformedJwtException e) {
                throw new AuthenticationServiceException("Malformed jwt", e);
            } catch (SignatureException e) {
                throw new AuthenticationServiceException("Invalid signature", e);
            }
            Claims claims = claimsJws.getBody();
            String username = claims.getSubject();
            String password = claims.get(PASSWORD_CLAIM, String.class);
            UserRole role = UserRole.valueOf(claims.get(ROLE_CLAIM, String.class));
            return this.generateAuthentication(username, password, role, token, request);
        }
    }

    public Authentication register(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String authenticationJson = null;
            try {
                authenticationJson = this.objectMapper.writeValueAsString(authentication);
            } catch (JsonProcessingException e) {
                authenticationJson = authentication.toString();
            }
            throw new AuthenticationServiceException(String.format("Authentication has already been done: %s", authenticationJson));
        }

        Map<String, String> map = this.obtain(request);
        String username = map.get(CoreUtils.USERNAME);
        String password = map.get(CoreUtils.PASSWORD);

        if (this.userService.findByLogin(username).isPresent()) { //TODO check exists in DB
            throw new AuthenticationServiceException(String.format("User '%s' is already registered", username));
        }

        User user = this.save(username, password, UserRole.USER);
        authentication = this.generateAuthentication(username, password, user.getRole(), request);
        ((AbstractAuthenticationToken) authentication).eraseCredentials();
        return authentication; //no need to call authenticate(), because no need to check authorities
    }

    public Authentication login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String authenticationJson = null;
            try {
                authenticationJson = this.objectMapper.writeValueAsString(authentication);
            } catch (JsonProcessingException e) {
                authenticationJson = authentication.toString();
            }
            throw new AuthenticationServiceException(String.format("Authentication has already been done: %s", authenticationJson));
        }

        Map<String, String> map = this.obtain(request);
        String username = map.get(CoreUtils.USERNAME);
        String password = map.get(CoreUtils.PASSWORD);

        if (!this.userService.findByLogin(username).isPresent()) { //TODO check exists in DB
            throw new AuthenticationServiceException(String.format("User '%s' is not registered", username));
        }

        authentication = this.generateAuthentication(username, password, UserRole.USER, request);
        ((AbstractAuthenticationToken) authentication).eraseCredentials();
        return authentication; //no need to call authenticate(), because no need to check authorities
    }

    private Map<String, String> obtain(HttpServletRequest request) {
        try {
            return this.objectMapper.readValue(request.getReader(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));
        } catch (IOException e) {
            String error = "Error occurred during parse request body";
            logger.error(error);
            throw new AuthenticationServiceException(error, e);
        }
    }

    private User save(String username, String password, UserRole role) {
        try {
            User user = new User();
            user.setLogin(username);
            user.setPassword(this.getPasswordEncoder().encode(password));
            user.setRole(role);
            return this.userService.save(user);
        } catch (Exception e) {
            String error = String.format("Registration of user '%s' failed due to: %s", username, e.getMessage());
            logger.error(error);
            throw new AuthenticationServiceException(error, e);
        }
    }

    private Authentication generateAuthentication(String username, String password, UserRole role, HttpServletRequest request) {
        String token = this.generateToken(username, password, role);
        JsonWebAuthenticationToken authentication = new JsonWebAuthenticationToken(username, password, Authorities.getFor(role), token);
        authentication.setDetails(AUTHENTICATION_DETAILS_SOURCE.buildDetails(request));
        return authentication;
    }

    private Authentication generateAuthentication(String username, String password, UserRole role, String token, HttpServletRequest request) {
        JsonWebAuthenticationToken authentication = new JsonWebAuthenticationToken(username, password, Authorities.getFor(role), token);
        authentication.setDetails(AUTHENTICATION_DETAILS_SOURCE.buildDetails(request));
        return authentication;
    }

    private String generateToken(String username, String password, UserRole role) {
        return Jwts.builder()
                //header
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                //payload
                .setSubject(username)
                .claim(PASSWORD_CLAIM, password)
                .claim(ROLE_CLAIM, role)
                //signature
                .signWith(SignatureAlgorithm.HS512, webProperties.getProperty(WebUtils.JWT_SERVICE_SECRET))
                .compact();
    }
}