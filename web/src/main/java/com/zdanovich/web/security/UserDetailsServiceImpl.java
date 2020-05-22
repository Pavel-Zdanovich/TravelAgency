package com.zdanovich.web.security;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = null;
        Optional<User> optionalUser = userService.findByLogin(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(), Authorities.getAuthority(user.getRole()));
        } else {
            throw new UsernameNotFoundException(String.format("User with login '%s' not found", username));
        }
        return userDetails;
    }

}
