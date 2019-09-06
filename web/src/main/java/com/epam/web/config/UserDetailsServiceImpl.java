/*
package com.epam.web.config;

import com.epam.core.entity.User;
import com.epam.core.entity.UserRole;
import com.epam.core.repository.UserRepository;
import com.epam.core.specification.FindSpecification;
import com.epam.core.specification.impl.user.FindUserByLoginSpecification;
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
        FindSpecification<User, String> findUserByLoginSpecification = new FindUserByLoginSpecification(username);
        Optional<User> optionalUser = userRepository.query(findUserByLoginSpecification).stream().findFirst();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Authority.getAuthority(UserRole.MEMBER));
        } else {
            throw new UsernameNotFoundException("Username : " + username + " not found");
        }
        return userDetails;
    }

}
*/
