package com.epam.travelAgency.config;

import com.epam.travelAgency.repository.impl.HotelRepository;
import com.epam.travelAgency.repository.impl.ReviewRepository;
import com.epam.travelAgency.repository.impl.TourRepository;
import com.epam.travelAgency.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {

    @Bean
    @Autowired
    public HotelRepository hotelRepository(DataSource dataSource) {
        return new HotelRepository(dataSource);
    }

    @Bean
    @Autowired
    public ReviewRepository reviewRepository(DataSource dataSource) {
        return new ReviewRepository(dataSource);
    }

    @Bean
    @Autowired
    public TourRepository tourRepository(DataSource dataSource) {
        return new TourRepository(dataSource);
    }

    @Bean
    @Autowired
    public UserRepository userRepository(DataSource dataSource) {
        return new UserRepository(dataSource);
    }

}
