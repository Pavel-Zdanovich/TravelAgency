package com.epam.travelAgency.config;

import com.epam.travelAgency.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EntityConfig.class)
@ActiveProfiles(profiles = "dev")
public class EntityConfigTest {

    @Autowired
    private Country country;
    @Autowired
    private Hotel hotel;
    @Autowired
    private Review review;
    @Autowired
    private Tour tour;
    @Autowired
    private User user;

    @Test
    public void getCountry() {
        Assert.assertNotNull(country);
    }

    @Test
    public void getHotel() {
        Assert.assertNotNull(hotel);
    }

    @Test
    public void getReview() {
        Assert.assertNotNull(review);
    }

    @Test
    public void getTour() {
        Assert.assertNotNull(tour);
    }

    @Test
    public void getUser() {Assert.assertNotNull(user);}

}