package com.zdanovich.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.service.impl.UserService;
import com.zdanovich.core.utils.CoreUtils;
import com.zdanovich.web.controller.system.AuthController;
import com.zdanovich.web.security.Authorities;
import com.zdanovich.web.security.jwt.JsonWebAuthenticationFilter;
import com.zdanovich.web.security.jwt.JsonWebAuthenticationToken;
import com.zdanovich.web.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@Component
public class AuthService extends DaoAuthenticationProvider {

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Autowired
    private Properties webProperties;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    private volatile String username;
    private volatile String password;

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

    public Authentication filter(HttpServletRequest request) {
        String servletPath = request.getServletPath();

        if ((AuthController.PATH + AuthController.SIGN_UP).equals(servletPath)) {
            return register(request);
        }
        if ((AuthController.PATH + AuthController.SIGN_IN).equals(servletPath)) {
            return login(request);
        }

        String jwtToken = request.getHeader(webProperties.getProperty(WebUtils.JWT_HEADER_NAME));
        if (jwtToken == null) {
            throw new AuthenticationServiceException(String.format("Header '%s' is not found", webProperties.getProperty(WebUtils.JWT_HEADER_NAME)));
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(webProperties.getProperty(WebUtils.JWT_SERVICE_SECRET)).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            String username = claims.getSubject();
            if (authentication == null) {
                throw new AuthenticationServiceException("Authentication lost");
            } else {
                if (authentication.getName() == null) {
                    throw new AuthenticationServiceException("Authentication lost name");
                }
                if (!authentication.getName().equals(username)) {
                    throw new AuthenticationServiceException(String.format("Authentication name '%s' is differ from token username '%s'",
                            authentication.getName(), username));
                } else {
                    return authentication;
                }
            }
        }
    }

    public Authentication register(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null && request.getAttribute(JsonWebAuthenticationFilter.ALREADY_FILTERED_ATTR_NAME) == null) {
            this.obtain(request);
            if (this.userService.findByLogin(username).isPresent()) {
                throw new AuthenticationServiceException(String.format("User '%s' is already registered", username));
            }
            User user = save(username, password);
            authentication = generate(username, password, Authorities.getFor(user.getRole()), request);
            return this.authenticate(authentication);
        } else {
            return handleAuthentication(request, authentication, username, password);
        }
    }

    public Authentication login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null && request.getAttribute(JsonWebAuthenticationFilter.ALREADY_FILTERED_ATTR_NAME) == null) {
            this.obtain(request);
            authentication = generate(username, password, Authorities.getFor(UserRole.USER), request);
            return this.authenticate(authentication);
        } else {
            return handleAuthentication(request, authentication, username, password);
        }
    }

    private Authentication handleAuthentication(HttpServletRequest request, Authentication authentication, String username, String password) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            logger.info(String.format("Anonymous '%s' is going authorize", authentication.getName()));
            authentication = generate(username, password, Authorities.getFor(UserRole.USER), request);
            return this.authenticate(authentication);
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            logger.info("Authentication has already been done");
            if (authentication.getName().equals(username)) {
                if (authentication.getCredentials().equals(password)) {
                    logger.info(String.format("Authentication has already been done, login as '%s' with a password '%s'", username, password));
                    return authentication;
                } else {
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }
            } else {
                throw new UsernameNotFoundException(String.format("User '%s' is authorized, an attempt to login as '%s' with a password '%s'",
                        authentication.getName(), username, password));
            }

        }

        throw new AuthenticationServiceException(String.format("Unknown authentication: %s", authentication));
    }

    private void obtain(HttpServletRequest request) {
        try {
            Map<String, String> map = this.objectMapper.readValue(request.getReader(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));
            username = map.get(CoreUtils.USERNAME);
            password = map.get(CoreUtils.PASSWORD);
        } catch (IOException e) {
            String error = "Error occurred during parse request body";
            logger.error(error);
            throw new AuthenticationServiceException(error, e);
        }
    }

    private User save(String username, String password) {
        try {
            User user = new User();
            user.setLogin(username);
            user.setPassword(this.getPasswordEncoder().encode(password));
            user.setRole(UserRole.USER);
            return this.userService.save(user);
        } catch (Exception e) {
            String error = String.format("Registration of user '%s' failed due to: %s", username, e.getMessage());
            logger.error(error);
            throw new AuthenticationServiceException(error, e);
        }
    }

    private Authentication generate(String username, String password, Collection<? extends GrantedAuthority> authorities, HttpServletRequest request) {
        JsonWebAuthenticationToken authenticationToken = new JsonWebAuthenticationToken(username, password, authorities, generateToken(username));
        authenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return authenticationToken;
    }

    private String generateToken(String username) {
        return Jwts.builder()
                //header
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                //payload|claim
                .setSubject(username)
                //signature
                .signWith(SignatureAlgorithm.HS512, webProperties.getProperty(WebUtils.JWT_SERVICE_SECRET))
                .compact();
    }
}