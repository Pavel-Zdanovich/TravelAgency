package com.zdanovich.web.controller.system;

import com.zdanovich.web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PostMapping(path = AuthController.REGISTER)
    public ResponseEntity<Authentication> register(HttpServletRequest request) {
        return ResponseEntity.ok(this.authService.register(request));
    }

    @PostMapping(path = AuthController.LOGIN)
    public ResponseEntity<Authentication> login(HttpServletRequest request) {
        return ResponseEntity.ok(this.authService.login(request));
    }
}