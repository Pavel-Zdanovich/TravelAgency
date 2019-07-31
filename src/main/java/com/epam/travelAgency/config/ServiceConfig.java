package com.epam.travelAgency.config;

import com.epam.travelAgency.service.impl.HotelService;
import com.epam.travelAgency.service.impl.ReviewService;
import com.epam.travelAgency.service.impl.TourService;
import com.epam.travelAgency.service.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public HotelService hotelService() {
        return new HotelService();
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewService();
    }

    @Bean
    public TourService tourService() {
        return new TourService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

}
