package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.repository.UserRepository;
import com.zdanovich.core.service.impl.UserService;
import com.zdanovich.web.controller.AbstractController;
import com.zdanovich.web.security.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(path = UserController.PATH)
public class UserController extends AbstractController<User, Long, UserRepository, UserService> {

    public static final String PATH = "/users";

    @Autowired
    public UserController(UserService userService) {
        super(userService);
    }

    @GetMapping(params = "login")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<User> findByLogin(
            @RequestParam
            @NotNull(message = "{user.login.notNull}")
            @Size(min = 5, max = 30, message = "{user.login.size}")
            @Pattern(regexp = User.ONE_WORD_REGEX, message = "{user.login.pattern}") String login) {
        return ResponseEntity.of(service.findByLogin(login));
    }

    @GetMapping(params = "role")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<List<User>> findByUserRole(@RequestParam UserRole role) {
        return ResponseEntity.ok(service.findByUserRole(role));
    }
}