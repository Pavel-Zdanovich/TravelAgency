package com.zdanovich.web.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.web.controller.impl.UserController;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

public class UserControllerTest extends AbstractControllerTest {

    private String path = UserController.PATH;

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setLogin("TestUserLogin1");
        user.setPassword("TestUserPassword1");
        user.setRole(UserRole.USER);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        post(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(new ObjectMapper().writeValueAsString(user))).
                andExpect(MockMvcResultMatchers.status().isCreated()).
                andReturn();
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).requestAttr("id", 1L)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void findByName() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).requestAttr("name", "France")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
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