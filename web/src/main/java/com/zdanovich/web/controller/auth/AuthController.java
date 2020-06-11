package com.zdanovich.web.controller.auth;

import com.zdanovich.web.security.auth.AuthService;
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

@RestController
@RequestMapping(path = AuthController.PATH)
public class AuthController {

    public static final String PATH = "/auth";
    public static final String REGISTER = "/signup";
    public static final String LOGIN = "/signin";
    public static final String LOGOUT = "/signout";

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = AuthController.REGISTER)
    public ResponseEntity<Authentication> register(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            authentication = this.authService.register(request);
            authentication = this.authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = this.authService.generateToken(authentication.getName());
            HttpHeaders headers = new HttpHeaders();
            headers.set(AuthService.TOKEN_HEADER, token);

            return ResponseEntity.ok().headers(headers).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = AuthController.LOGIN)
    public ResponseEntity<Authentication> login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            authentication = this.authService.login(request);
            authentication = this.authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = this.authService.generateToken(authentication.getName());
            HttpHeaders headers = new HttpHeaders();
            headers.set(AuthService.TOKEN_HEADER, token);

            return ResponseEntity.ok().headers(headers).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}