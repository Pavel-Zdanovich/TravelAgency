package com.epam.core.integration.spring;

import com.epam.core.repository.CountryRepository;
import com.epam.core.repository.FeatureRepository;
import com.epam.core.repository.HotelRepository;
import com.epam.core.repository.ReviewRepository;
import com.epam.core.repository.TourRepository;
import com.epam.core.repository.UserRepository;
import com.epam.core.service.impl.CountryService;
import com.epam.core.service.impl.FeatureService;
import com.epam.core.service.impl.HotelService;
import com.epam.core.service.impl.ReviewService;
import com.epam.core.service.impl.TourService;
import com.epam.core.service.impl.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CountryRepository.class, FeatureRepository.class, HotelRepository.class,
        ReviewRepository.class, TourRepository.class, UserRepository.class,
        CountryService.class, FeatureService.class, HotelService.class,
        ReviewService.class, TourService.class, UserService.class})
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
