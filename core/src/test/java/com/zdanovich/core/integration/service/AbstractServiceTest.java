package com.zdanovich.core.integration.service;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.core.integration.repository.AbstractRepositoryTest;
import com.zdanovich.core.service.impl.CountryService;
import com.zdanovich.core.service.impl.FeatureService;
import com.zdanovich.core.service.impl.HotelService;
import com.zdanovich.core.service.impl.ReviewService;
import com.zdanovich.core.service.impl.TourService;
import com.zdanovich.core.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(classes = CoreModuleConfiguration.class)
public abstract class AbstractServiceTest extends AbstractRepositoryTest {

    @Autowired
    protected CountryService countryService;
    @Autowired
    protected FeatureService featureService;
    @Autowired
    protected HotelService hotelService;
    @Autowired
    protected ReviewService reviewService;
    @Autowired
    protected TourService tourService;
    @Autowired
    protected UserService userService;

}
