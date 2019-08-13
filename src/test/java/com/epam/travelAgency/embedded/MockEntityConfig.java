package com.epam.travelAgency.embedded;

import com.epam.travelAgency.entity.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class MockEntityConfig {

    @Bean
    @Primary
    public Country country() {
        return Mockito.mock(Country.class);
    }

    @Bean
    @Primary
    public Hotel hotel() {
        return Mockito.mock(Hotel.class);
    }

    @Bean
    @Primary
    public Review review() {
        return Mockito.mock(Review.class);
    }

    @Bean
    @Primary
    public Tour tour() {
        return Mockito.mock(Tour.class);
    }

    @Bean
    @Primary
    public User user() {
        return Mockito.mock(User.class);
    }

}
