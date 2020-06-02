package com.zdanovich.web.integration.controller;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.web.controller.impl.UserController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin1");
        user.setPassword("TestUserPassword1");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = request(HttpMethod.POST, UserController.PATH, user, HttpStatus.CREATED);

        User savedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(savedUser);
        user.setId(savedUser.getId());
        Assert.assertEquals(user, savedUser);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(UserController.PATH)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<User> users = objectMapper.readerForListOf(User.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(users);
        Assert.assertNotEquals(0, users.size());
    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(UserController.PATH).queryParam("id", id.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), id);
    }

    @Test
    public void findByLogin() throws Exception {
        String login = "ElonMusk";

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(UserController.PATH).queryParam("login", login)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getLogin(), login);
    }

    @Test
    public void findByUserRole() throws Exception {
        UserRole role = UserRole.USER;

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(UserController.PATH).queryParam("role", role.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<User> users = objectMapper.readerForListOf(User.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(users);
        Assert.assertTrue(users.stream().allMatch(user -> user.getRole().equals(role)));
    }

    @Test
    public void update() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin2");
        user.setPassword("TestUserPassword2");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = request(HttpMethod.POST, UserController.PATH, user, HttpStatus.CREATED);

        User savedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(savedUser);

        mvcResult = request(HttpMethod.PUT, UserController.PATH, savedUser, HttpStatus.OK);

        User updatedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(savedUser, updatedUser);
    }

    @Test
    public void delete() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin3");
        user.setPassword("TestUserPassword3");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = request(HttpMethod.POST, UserController.PATH, user, HttpStatus.CREATED);

        User savedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(savedUser);

        mvcResult = request(HttpMethod.DELETE, UserController.PATH, savedUser, HttpStatus.OK);

        User deletedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(deletedUser);
    }

    @Test
    public void deleteById() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin4");
        user.setPassword("TestUserPassword4");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = request(HttpMethod.POST, UserController.PATH, user, HttpStatus.CREATED);

        User savedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(savedUser);

        mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(UserController.PATH).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        User deletedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(deletedUser);
    }
}