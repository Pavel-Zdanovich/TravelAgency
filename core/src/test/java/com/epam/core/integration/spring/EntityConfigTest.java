package com.epam.core.integration.spring;

import com.epam.core.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Country.class, Feature.class, Hotel.class, Review.class, Tour.class, User.class})
@ActiveProfiles(profiles = "test")
public class EntityConfigTest {

    @Autowired
    private Country country;
    @Autowired
    private Feature feature;
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
    public void getFeature() {
        Assert.assertNotNull(feature);
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