package com.zdanovich.web.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.web.controller.impl.TourController;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TourControllerTest extends AbstractControllerTest {

    private String path = TourController.PATH;

    @Test
    public void save() throws Exception {
        Tour tour = new Tour();
        tour.setPhotoPath("");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        post(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(new ObjectMapper().writeValueAsString(tour))).
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
        Tour tour = new Tour();
        tour.setPhotoPath("");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        put(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(new ObjectMapper().writeValueAsString(tour))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();
    }

    @Test
    public void delete() throws Exception {
        Tour tour = new Tour();
        tour.setPhotoPath("");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.
                        delete(path).
                        contentType(MediaType.APPLICATION_JSON).
                        content(new ObjectMapper().writeValueAsString(tour))).
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