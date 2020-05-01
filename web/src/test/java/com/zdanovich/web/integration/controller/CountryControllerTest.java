package com.zdanovich.web.integration.controller;

import com.zdanovich.core.entity.Country;
import com.zdanovich.web.controller.impl.CountryController;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

public class CountryControllerTest extends AbstractControllerTest {

    private String path = CountryController.PATH;

    @Test
    public void save() throws Exception {
        Country country = new Country();
        country.setName("TestCountryName1");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.post(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
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
                perform(MockMvcRequestBuilders.get(path).queryParam("id", "1")).
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
        Country country = new Country();
        country.setName("TestCountryName1");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void delete() throws Exception {
        Country country = new Country();
        country.setName("TestCountryName1");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(path)).
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