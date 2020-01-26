package com.epam.core.integration.repository;

import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.User;
import com.epam.core.config.MigrationConfig;
import com.epam.core.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, MigrationConfig.class, UserRepository.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() {
        User expected = new User();
        expected.setLogin("TestUserLogin");
        expected.setPassword("TestUserPassword");
        userRepository.save(expected);
        User actual = userRepository.findByLogin(expected.getLogin()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_By_Iterable() {
    }

    @Test
    public void saveAndFlush() {
    }

    @Test
    public void flush() {
    }

    @Test
    public void findById() {
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        User actual = userRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByLogin() {
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        User actual = userRepository.findByLogin(expected.getLogin()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByLoginLike() {
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        User actual = userRepository.findByLoginLike("%ElonMusk%").stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByLoginStartingWith() {
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        User actual = userRepository.findByLoginStartingWith(expected.getLogin()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByLoginEndingWith() {
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        User actual = userRepository.findByLoginEndingWith(expected.getLogin()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByLoginContaining() {
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        User actual = userRepository.findByLoginContaining(expected.getLogin()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<User> actual = userRepository.findAll();
        Assert.assertTrue(actual.size() > 0);
    }

    @Test
    public void findAllById() {
    }

    @Test
    public void findAll_With_Sort () {
    }

    @Test
    public void findAll_By_Example() {
    }

    @Test
    public void findAll_By_Example_With_Sort() {
    }

    @Test
    public void findAll_By_Pageable() {
    }

    @Test
    public void findAll_By_Example_And_Pageable() {
    }

    @Test
    public void existsById() {
        Assert.assertTrue(userRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
    }

    @Test
    public void count() {
        Assert.assertNotEquals(userRepository.count(), 0);
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update() {
        User expected = userRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setLogin("UpdatedUserLogin");
        userRepository.save(expected);
        User actual = userRepository.findById(2L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        User foundUser = userRepository.findById(3L).orElse(null);
        Assert.assertNotNull(foundUser);
        userRepository.delete(foundUser);
        User deletedUser = userRepository.findById(3L).orElse(null);
        Assert.assertNull(deletedUser);
    }

    @Test
    public void deleteById() {
        User foundUser = userRepository.findById(4L).orElse(null);
        Assert.assertNotNull(foundUser);
        userRepository.deleteById(4L);
        User deletedUser = userRepository.findById(4L).orElse(null);
        Assert.assertNull(deletedUser);
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void deleteAll_By_Iterable() {
        long numberOfUsersBeforeDeletion = userRepository.count();
        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("ElonMusk");
        user1.setPassword("SpaceXXX");
        User user2 = new User();
        user2.setId(2L);
        user2.setLogin("ElonMusk");
        user2.setPassword("SpaceXXX");
        userRepository.deleteAll(new ArrayList<User>(){{
            add(user1);
            add(user2);
        }});
        long numberOfUsersAfterDeletion = userRepository.count();
        Assert.assertEquals(numberOfUsersBeforeDeletion - numberOfUsersAfterDeletion, 2);
    }

    @Test
    public void deleteAllInBatch() {
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }

}
