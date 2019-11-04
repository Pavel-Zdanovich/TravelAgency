package com.epam.core.integration.spring;

import com.epam.core.repository.*;
import com.epam.core.service.impl.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CountryRepository.class, FeatureRepository.class, HotelRepository.class,
        ReviewRepository.class, TourRepository.class, UserRepository.class,
        CountryService.class, FeatureService.class, HotelService.class,
        ReviewService.class, TourService.class, UserService.class})
@ActiveProfiles(profiles = "dev")
public class ServiceConfigTest {

    @Autowired
    private CountryService countryService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private TourService tourService;
    @Autowired
    private UserService userService;

    @Test
    public void get_CountryService() {
        Assert.assertNotNull(countryService);
    }

    @Test
    public void get_FeatureService() {
        Assert.assertNotNull(featureService);
    }

    @Test
    public void get_HotelService() {
        Assert.assertNotNull(hotelService);
    }

    @Test
    public void get_ReviewService() {
        Assert.assertNotNull(reviewService);
    }

    @Test
    public void get_TourService() {
        Assert.assertNotNull(tourService);
    }

    @Test
    public void get_UserService() {
        Assert.assertNotNull(userService);
    }
}
