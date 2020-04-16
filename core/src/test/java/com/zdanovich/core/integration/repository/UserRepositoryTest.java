package com.zdanovich.core.integration.repository;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Test
    public void save_Without_Tours() {
        User expected = new User();
        expected.setLogin("TestUserLogin1");
        expected.setPassword("TestUserPassword");
        expected.setRole(UserRole.USER);

        User actual = userRepository.findByLogin(expected.getLogin()).orElse(null);
        Assert.assertNull(actual);

        User savedUser = userRepository.save(expected);
        Assert.assertNotNull(savedUser);
        expected.setId(savedUser.getId());

        actual = userRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_Detached_Tours() {

    }

    @Test
    public void save_With_Attached_Tours() {

    }

    @Test
    public void save_With_New_Tours() {

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
        User actual = userRepository.findById(elonMusk.getId()).orElse(null);
        Assert.assertEquals(elonMusk, actual);
    }

    @Test
    public void findByLogin() {
        User actual = userRepository.findByLogin(elonMusk.getLogin()).orElse(null);
        Assert.assertEquals(elonMusk, actual);
    }

    @Test
    public void findByLoginLike() {
        String loginRegex = "lonMus";
        List<User> users = userRepository.findByLoginLike(loginRegex);
        Assert.assertTrue(users.stream().allMatch(user -> user.getLogin().matches(loginRegex)));
    }

    @Test
    public void findByLoginStartingWith() {
        String prefix = "Elon";
        List<User> users = userRepository.findByLoginStartingWith(prefix);
        Assert.assertTrue(users.stream().allMatch(user -> user.getLogin().startsWith(prefix)));
    }

    @Test
    public void findByLoginEndingWith() {
        String suffix = "Musk";
        List<User> users = userRepository.findByLoginEndingWith(suffix);
        Assert.assertTrue(users.stream().allMatch(user -> user.getLogin().endsWith(suffix)));
    }

    @Test
    public void findByLoginContaining() {
        String loginContaining = "Musk";
        List<User> users = userRepository.findByLoginContaining(loginContaining);
        Assert.assertTrue(users.stream().allMatch(user -> user.getLogin().contains(loginContaining)));
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<User> users = userRepository.findAll();
        Assert.assertNotNull(users);
        Assert.assertFalse(users.isEmpty());
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
        Assert.assertNotEquals(0, userRepository.count());
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update_Without_Tours() {
    }

    @Test
    public void update_With_Detached_Tours() {}

    @Test
    public void update_With_Attached_Tours() {}

    @Test
    public void update_With_New_Tours() {}

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
        //I hope I'll never use this method
    }

    @Test
    public void deleteAll_By_Iterable() {

    }

    @Test
    public void deleteAllInBatch() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }
}
