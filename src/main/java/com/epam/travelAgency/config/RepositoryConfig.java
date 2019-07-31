package com.epam.travelAgency.config;

import com.epam.travelAgency.repository.impl.HotelRepository;
import com.epam.travelAgency.repository.impl.ReviewRepository;
import com.epam.travelAgency.repository.impl.TourRepository;
import com.epam.travelAgency.repository.impl.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    //@Autowired(datasource)
    public HotelRepository hotelRepository() {
        return new HotelRepository();
    }

    @Bean
    //@Autowired
    public ReviewRepository reviewRepository() {
        return new ReviewRepository();
    }

    @Bean
    //@Autowired
    public TourRepository tourRepository() {
        return new TourRepository();
    }

    @Bean
    //@Autowired
    public UserRepository userRepository() {
        return new UserRepository();
    }

}
