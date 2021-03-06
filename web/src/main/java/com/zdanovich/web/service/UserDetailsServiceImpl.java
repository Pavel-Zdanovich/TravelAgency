package com.zdanovich.web.service;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.repository.UserRepository;
import com.zdanovich.web.security.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = null;
        Optional<User> optionalUser = this.userRepository.findByLogin(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(), Authorities.getFor(user.getRole()));
        } else {
            throw new UsernameNotFoundException(String.format("User with login '%s' not found", username));
        }
        return userDetails;
    }
}