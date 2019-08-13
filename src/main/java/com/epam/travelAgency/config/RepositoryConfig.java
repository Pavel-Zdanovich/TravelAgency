package com.epam.travelAgency.config;

import com.epam.travelAgency.repository.HotelRepository;
import com.epam.travelAgency.repository.ReviewRepository;
import com.epam.travelAgency.repository.TourRepository;
import com.epam.travelAgency.repository.UserRepository;
import com.epam.travelAgency.repository.impl.HotelRepositoryImpl;
import com.epam.travelAgency.repository.impl.ReviewRepositoryImpl;
import com.epam.travelAgency.repository.impl.TourRepositoryImpl;
import com.epam.travelAgency.repository.impl.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"dev", "test"})
public class RepositoryConfig {

    @Bean(name = "hotelRepository")
    public HotelRepository hotelRepository() {
        return new HotelRepositoryImpl();
    }

    @Bean(name = "reviewRepository")
    public ReviewRepository reviewRepository() {
        return new ReviewRepositoryImpl();
    }

    @Bean(name = "tourRepository")
    public TourRepository tourRepository() {
        return new TourRepositoryImpl();
    }

    @Bean(name = "userRepository")
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

}
