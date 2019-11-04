package com.epam.core.repository;

import com.epam.core.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile(value = "dev")
@Transactional
public interface UserRepository extends CommonRepository<User, Long> {

    Optional<User> findByLogin(String login);
    List<User> findByLoginLike(String loginRegex);
    List<User> findByLoginStartingWith(String login);
    List<User> findByLoginEndingWith(String login);
    List<User> findByLoginContaining(String login);

}
