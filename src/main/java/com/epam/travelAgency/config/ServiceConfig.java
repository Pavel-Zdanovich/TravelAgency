package com.epam.travelAgency.config;

import com.epam.travelAgency.service.impl.HotelService;
import com.epam.travelAgency.service.impl.ReviewService;
import com.epam.travelAgency.service.impl.TourService;
import com.epam.travelAgency.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    @Autowired
    public HotelService hotelService() {
        return new HotelService();
    }

    @Bean
    @Autowired
    public ReviewService reviewService() {
        return new ReviewService();
    }

    @Bean
    @Autowired
    public TourService tourService() {
        return new TourService();
    }

    @Bean
    @Autowired
    public UserService userService() {
        return new UserService();
    }

}
