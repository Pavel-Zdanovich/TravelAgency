package com.zdanovich.web.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.web.controller.impl.CountryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CountryControllerTest extends AbstractControllerTest {

    private String path = CountryController.PATH;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void save() throws Exception {
        Country country = new Country();
        country.setName("TestCountryName1");

        MvcResult mvcResult = request(HttpMethod.POST, path, country, HttpStatus.CREATED);

        Country savedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(savedCountry);
        country.setId(savedCountry.getId());
        Assert.assertEquals(country, savedCountry);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Country> countries = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assert.assertNotNull(countries);
        Assert.assertNotEquals(0, countries.size());
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Country country = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(country);
    }

    @Test
    public void findByName() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).requestAttr("name", "France")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Country country = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(country);
    }

    @Test
    public void update() throws Exception {
        Country country = new Country();
        country.setName("TestCountryName2");

        MvcResult mvcResult = request(HttpMethod.POST, path, country, HttpStatus.CREATED);

        Country savedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(savedCountry);

        savedCountry.setName("UpdatedCountryName");

        mvcResult = request(HttpMethod.PUT, path, savedCountry, HttpStatus.OK);

        Country updatedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(updatedCountry);
        Assert.assertEquals(savedCountry, updatedCountry);
    }

    @Test
    public void delete() throws Exception {
        Country country = new Country();
        country.setName("TestCountryName3");

        MvcResult mvcResult = request(HttpMethod.POST, path, country, HttpStatus.CREATED);

        Country savedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(savedCountry);

        mvcResult = request(HttpMethod.DELETE, path, savedCountry, HttpStatus.OK);

        Country deletedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(deletedCountry);
    }

    @Test
    public void deleteById() throws Exception {
        Country country = new Country();
        country.setName("TestCountryName4");

        MvcResult mvcResult = request(HttpMethod.POST, path, country, HttpStatus.CREATED);

        Country savedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(savedCountry);

        mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(path).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Country deletedCountry = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class);
        Assert.assertNotNull(deletedCountry);
    }
}