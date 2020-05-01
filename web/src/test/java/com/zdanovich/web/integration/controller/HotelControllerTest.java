package com.zdanovich.web.integration.controller;

import com.zdanovich.core.entity.Hotel;
import com.zdanovich.web.controller.impl.HotelController;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class HotelControllerTest extends AbstractControllerTest {

    private String path = HotelController.PATH;

    @Test
    public void save() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName1");
        hotel.setWebsite("https://www.test-hotel-1.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(12));
        hotel.setLongitude(BigDecimal.valueOf(34));

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
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName1");
        hotel.setWebsite("https://www.test-hotel-1.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(12));
        hotel.setLongitude(BigDecimal.valueOf(34));

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.put(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void delete() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName1");
        hotel.setWebsite("https://www.test-hotel-1.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(12));
        hotel.setLongitude(BigDecimal.valueOf(34));

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