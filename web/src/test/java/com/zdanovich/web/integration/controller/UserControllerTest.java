package com.zdanovich.web.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.web.controller.impl.UserController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserControllerTest extends AbstractControllerTest {

    private String path = UserController.PATH;

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin1");
        user.setPassword("TestUserPassword1");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = request(HttpMethod.POST, path, user, HttpStatus.CREATED);

        User savedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(savedUser);
        user.setId(savedUser.getId());
        Assert.assertEquals(user, savedUser);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<User> users = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assert.assertNotNull(users);
        Assert.assertNotEquals(0, users.size());
    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).queryParam("id", id.toString())).
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
                perform(MockMvcRequestBuilders.get(path).queryParam("login", login)).
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
                perform(MockMvcRequestBuilders.get(path).queryParam("role", role.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getRole(), role);
    }

    @Test
    public void update() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin1");
        user.setPassword("TestUserPassword1");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        put(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(new ObjectMapper().writeValueAsString(user))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void delete() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin1");
        user.setPassword("TestUserPassword1");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        delete(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(new ObjectMapper().writeValueAsString(user))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void deleteById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    /*@Test
    public void deleteAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }*/
}