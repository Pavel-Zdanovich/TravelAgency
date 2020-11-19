package com.zdanovich.web.integration.controller;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.web.controller.impl.TourController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Period;
import java.util.List;

public class TourControllerTest extends AbstractControllerTest {

    @Test
    public void save() throws Exception {
        Tour tour = new Tour();
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = request(HttpMethod.POST, TourController.PATH, tour, HttpStatus.CREATED);

        Tour savedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(savedTour);
        tour.setId(savedTour.getId());
        Assert.assertEquals(tour, savedTour);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertNotEquals(0, tours.size());
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Tour tour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(tour);
    }

    @Test
    public void findByStartDate() throws Exception {
        Timestamp startDate = Timestamp.valueOf("2021-01-01 00:00:00");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("startDate", startDate.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getStartDate().equals(startDate)));
    }

    @Test
    public void findByEndDate() throws Exception {
        Timestamp endDate = Timestamp.valueOf("2021-01-01 00:00:00");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("endDate", endDate.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getEndDate().equals(endDate)));
    }

    @Test
    public void findByDuration() throws Exception {
        Timestamp startDate = Timestamp.valueOf("2021-01-01 00:00:00");
        Timestamp endDate = Timestamp.valueOf("2021-01-01 00:00:00");
        Period period = Period.between(startDate.toLocalDateTime().toLocalDate(), endDate.toLocalDateTime().toLocalDate());

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("duration", String.valueOf(period.getDays()))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertTrue(tours.stream().allMatch(tour -> Period.between(tour.getStartDate().toLocalDateTime().toLocalDate(),
                tour.getEndDate().toLocalDateTime().toLocalDate()).getDays() == period.getDays()));
    }

    @Test
    public void findByDescription() throws Exception {
        String description = "";

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("description", description)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getDescription().equals(description)));
    }

    @Test
    public void findByCost() throws Exception {
        BigDecimal cost = BigDecimal.valueOf(123);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("cost", cost.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getCost().equals(cost)));
    }

    @Test
    public void findByTourType() throws Exception {
        TourType type = TourType.BUSINESS;

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(TourController.PATH).queryParam("type", type.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Tour> tours = objectMapper.readerForListOf(Tour.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(tours);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getTourType().equals(type)));
    }

    @Test
    public void update() throws Exception {
        Tour tour = new Tour();
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = request(HttpMethod.POST, TourController.PATH, tour, HttpStatus.CREATED);

        Tour savedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(savedTour);

        mvcResult = request(HttpMethod.PUT, TourController.PATH, savedTour, HttpStatus.OK);

        Tour updatedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(updatedTour);
        Assert.assertEquals(savedTour, updatedTour);
    }

    @Test
    public void delete() throws Exception {
        Tour tour = new Tour();
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = request(HttpMethod.POST, TourController.PATH, tour, HttpStatus.CREATED);

        Tour savedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(savedTour);

        mvcResult = request(HttpMethod.DELETE, TourController.PATH, savedTour, HttpStatus.OK);

        Tour deletedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(deletedTour);
    }

    @Test
    public void deleteById() throws Exception {
        Tour tour = new Tour();
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.BUSINESS);

        MvcResult mvcResult = request(HttpMethod.POST, TourController.PATH, tour, HttpStatus.CREATED);

        Tour savedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(savedTour);

        mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(TourController.PATH).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Tour deletedTour = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tour.class);
        Assert.assertNotNull(deletedTour);
    }
}