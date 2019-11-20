package com.epam.web.security;

import com.epam.core.entity.User;
import com.epam.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl() {
        System.out.println("\nUSER DETAILS SERVICE CREATED\n");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("\nSEARCH USER IN DATABASE\n");
        UserDetails userDetails = null;
        Optional<User> optionalUser = userRepository.findByLogin(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(), Authority.getAuthority(user.getRole()));
        } else {
            throw new UsernameNotFoundException("User with login : " + username + " not found");
        }
        return userDetails;
    }

}
