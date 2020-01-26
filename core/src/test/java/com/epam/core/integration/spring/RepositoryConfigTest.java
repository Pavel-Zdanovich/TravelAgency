package com.epam.core.integration.spring;

import com.epam.core.repository.CountryRepository;
import com.epam.core.repository.FeatureRepository;
import com.epam.core.repository.HotelRepository;
import com.epam.core.repository.ReviewRepository;
import com.epam.core.repository.TourRepository;
import com.epam.core.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CountryRepository.class, FeatureRepository.class, HotelRepository.class,
        ReviewRepository.class, TourRepository.class, UserRepository.class})
public class RepositoryConfigTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void get_CountryRepository() {
        Assert.assertNotNull(countryRepository);
    }

    @Test
    public void get_FeatureRepository() {
        Assert.assertNotNull(featureRepository);
    }

    @Test
    public void get_HotelRepository() {
        Assert.assertNotNull(hotelRepository);
    }

    @Test
    public void get_ReviewRepository() {
        Assert.assertNotNull(reviewRepository);
    }

    @Test
    public void get_TourRepository() {
        Assert.assertNotNull(tourRepository);
    }

    @Test
    public void get_UserRepository() {
        Assert.assertNotNull(userRepository);
    }
}
