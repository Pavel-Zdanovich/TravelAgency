package com.zdanovich.web.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.utils.Utils;
import com.zdanovich.web.controller.impl.HotelController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        MvcResult mvcResult = request(HttpMethod.POST, path, hotel, HttpStatus.CREATED);

        Hotel savedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());
        Assert.assertEquals(hotel, savedHotel);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Hotel> hotels = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assert.assertNotNull(hotels);
        Assert.assertNotEquals(0, hotels.size());
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Hotel hotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(hotel);
    }

    @Test
    public void findByName() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).queryParam("name", "Park Hyatt Saigon")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Hotel hotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(hotel);
    }

    @Test
    public void findByStars() throws Exception {
        Short stars = Short.valueOf("5");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).queryParam("stars", stars.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Hotel> hotels = objectMapper.readerForListOf(Hotel.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(hotels);
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getStars().compareTo(stars) == 0));
    }

    @Test
    public void findByArea() throws Exception {
        BigDecimal minLatitude = BigDecimal.valueOf(56.2317882);
        BigDecimal maxLatitude = BigDecimal.valueOf(56.2317882);
        BigDecimal minLongitude = BigDecimal.valueOf(-3.9663499);
        BigDecimal maxLongitude = BigDecimal.valueOf(-3.9663499);

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).
                        queryParam("minLatitude", minLatitude.toString()).
                        queryParam("maxLatitude", maxLatitude.toString()).
                        queryParam("minLongitude", minLongitude.toString()).
                        queryParam("maxLongitude", maxLongitude.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Hotel> hotels = objectMapper.readerForListOf(Hotel.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(hotels);
        Assert.assertTrue(hotels.stream().allMatch(hotel ->
                        hotel.getLatitude().compareTo(minLatitude) >= 0 &&
                        hotel.getLatitude().compareTo(maxLatitude) <= 0 &&
                        hotel.getLongitude().compareTo(minLongitude) >= 0 &&
                        hotel.getLongitude().compareTo(maxLongitude) <= 0));
    }

    @Test
    public void findByFeatures() throws Exception {
        Set<String> features = new HashSet<>();
        features.add(Utils.AIR_CONDITIONER);
        features.add(Utils.CABLE_TV);
        features.add(Utils.CAR_RENTAL);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.addAll("features", new ArrayList<>(features));

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(path).queryParams(multiValueMap)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Hotel> hotels = objectMapper.readerForListOf(Hotel.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(hotels);
        Assert.assertTrue(hotels.stream().allMatch(hotel -> features.stream().allMatch(hotel::containsFeature)));
    }

    @Test
    public void findByCountry() throws Exception {

    }

    @Test
    public void update() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName2");
        hotel.setWebsite("https://www.test-hotel-2.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(23));
        hotel.setLongitude(BigDecimal.valueOf(45));

        MvcResult mvcResult = request(HttpMethod.POST, path, hotel, HttpStatus.CREATED);

        Hotel savedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(savedHotel);

        savedHotel.setName("UpdatedHotelName");

        mvcResult = request(HttpMethod.PUT, path, savedHotel, HttpStatus.OK);

        Hotel updatedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(updatedHotel);
        Assert.assertEquals(savedHotel, updatedHotel);
    }

    @Test
    public void delete() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName3");
        hotel.setWebsite("https://www.test-hotel-3.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34));
        hotel.setLongitude(BigDecimal.valueOf(56));

        MvcResult mvcResult = request(HttpMethod.POST, path, hotel, HttpStatus.CREATED);

        Hotel savedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(savedHotel);

        mvcResult = request(HttpMethod.DELETE, path, savedHotel, HttpStatus.OK);

        Hotel deletedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(deletedHotel);
    }

    @Test
    public void deleteById() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName4");
        hotel.setWebsite("https://www.test-hotel-4.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(45));
        hotel.setLongitude(BigDecimal.valueOf(67));

        MvcResult mvcResult = request(HttpMethod.POST, path, hotel, HttpStatus.CREATED);

        Hotel savedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(savedHotel);

        mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(path).queryParam("id", String.valueOf(savedHotel.getId()))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Hotel deletedHotel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hotel.class);
        Assert.assertNotNull(deletedHotel);
    }
}