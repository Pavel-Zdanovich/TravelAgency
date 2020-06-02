package com.zdanovich.web.controller.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.utils.Utils;
import com.zdanovich.web.security.auth.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(path = AuthController.PATH)
public class AuthController {

    public static final String PATH = "/auth";
    public static final String REGISTER = "/signup";
    public static final String LOGIN = "/signin";
    public static final String LOGOUT = "/signout";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = AuthController.REGISTER)
    public ResponseEntity<Authentication> register(HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            JsonNode jsonNode = objectMapper.readTree(request.getReader());
            String username = jsonNode.findValue(Utils.USERNAME).asText();
            String password = jsonNode.findValue(Utils.PASSWORD).asText();

            authenticationManager.authenticate(authUtils.register(username, password, request));

            String token = authUtils.generateToken(username);
            HttpHeaders headers = new HttpHeaders();
            headers.set(AuthUtils.TOKEN_HEADER, token);
            headers.setBearerAuth(token);

            return ResponseEntity.ok().headers(headers).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = AuthController.LOGIN)
    public ResponseEntity<Authentication> login(HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            JsonNode jsonNode = objectMapper.readTree(request.getReader());
            String username = jsonNode.findValue(Utils.USERNAME).asText();
            String password = jsonNode.findValue(Utils.PASSWORD).asText();

            authenticationManager.authenticate(authUtils.login(username, password, request));

            String token = authUtils.generateToken(username);
            HttpHeaders headers = new HttpHeaders();
            headers.set(AuthUtils.TOKEN_HEADER, token);
            headers.setBearerAuth(token);

            return ResponseEntity.ok().headers(headers).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}