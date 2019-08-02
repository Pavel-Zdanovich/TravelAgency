package com.epam.travelAgency.embedded;

import com.epam.travelAgency.repository.impl.HotelRepository;
import com.epam.travelAgency.repository.impl.ReviewRepository;
import com.epam.travelAgency.repository.impl.TourRepository;
import com.epam.travelAgency.repository.impl.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MockRepositoryConfig {

    @Bean
    @Primary
    public HotelRepository hotelRepository() {
        return Mockito.mock(HotelRepository.class);
    }

    @Bean
    @Primary
    public ReviewRepository reviewRepository() {
        return Mockito.mock(ReviewRepository.class);
    }

    @Bean
    @Primary
    public TourRepository tourRepository() {
        return Mockito.mock(TourRepository.class);
    }

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

}
