package com.epam.web.security;

import com.epam.core.entity.User;
import com.epam.core.repository.UserRepository;
import com.epam.core.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
