package com.epam.travelAgency.embedded;

import com.epam.travelAgency.repository.HotelRepository;
import com.epam.travelAgency.repository.ReviewRepository;
import com.epam.travelAgency.repository.TourRepository;
import com.epam.travelAgency.repository.UserRepository;
import com.epam.travelAgency.repository.impl.HotelRepositoryImpl;
import com.epam.travelAgency.repository.impl.ReviewRepositoryImpl;
import com.epam.travelAgency.repository.impl.TourRepositoryImpl;
import com.epam.travelAgency.repository.impl.UserRepositoryImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class MockRepositoryConfig {

    @Bean
    @Primary
    public HotelRepository hotelRepository() {
        return Mockito.mock(HotelRepositoryImpl.class);
    }

    @Bean
    @Primary
    public ReviewRepository reviewRepository() {
        return Mockito.mock(ReviewRepositoryImpl.class);
    }

    @Bean
    @Primary
    public TourRepository tourRepository() {
        return Mockito.mock(TourRepositoryImpl.class);
    }

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepositoryImpl.class);
    }

}
