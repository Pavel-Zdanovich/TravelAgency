package com.epam.travelAgency.config;

import com.epam.travelAgency.service.HotelService;
import com.epam.travelAgency.service.ReviewService;
import com.epam.travelAgency.service.TourService;
import com.epam.travelAgency.service.UserService;
import com.epam.travelAgency.service.impl.HotelServiceImpl;
import com.epam.travelAgency.service.impl.ReviewServiceImpl;
import com.epam.travelAgency.service.impl.TourServiceImpl;
import com.epam.travelAgency.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"dev", "test"})
public class ServiceConfig {

    @Bean
    public HotelService hotelService() {
        return new HotelServiceImpl();
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewServiceImpl();
    }

    @Bean
    public TourService tourService() {
        return new TourServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

}
