package com.zdanovich.core.repository;

import com.zdanovich.core.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<User, Long> {

    Optional<User> findByLogin(String login);
    List<User> findByLoginLike(String loginRegex);
    List<User> findByLoginStartingWith(String login);
    List<User> findByLoginEndingWith(String login);
    List<User> findByLoginContaining(String login);

}
