package com.zdanovich.web.controller.system;

import com.zdanovich.web.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequestMapping(path = AuthController.PATH)
public class AuthController {

    public static final String PATH = "/auth";
    public static final String SIGN_UP = "/signup"; //register
    public static final String SIGN_IN = "/signin"; //enter
    public static final String SIGN_OUT = "/signout"; //exit

    @Autowired
    private AuthService authService;

    @PostMapping(path = AuthController.SIGN_UP)
    public ResponseEntity<Authentication> register(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.register(request));
    }

    @PostMapping(path = AuthController.SIGN_IN)
    public ResponseEntity<Authentication> login(HttpServletRequest request) {
        return ResponseEntity.ok(this.authService.login(request));
    }

    @RequestMapping(path = AuthController.SIGN_OUT)
    public ResponseEntity<?> logout() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.error("Authentication wasn't logged out in the filter");
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return ResponseEntity.ok().build();
    }
}