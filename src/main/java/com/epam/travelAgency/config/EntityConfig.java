package com.epam.travelAgency.config;

import com.epam.travelAgency.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityConfig {

    @Bean
    public Country country() {
        return new Country();
    }

    @Bean
    public Hotel hotel() {
        return new Hotel();
    }

    @Bean
    public Review review() {
        return new Review();
    }

    @Bean
    public Tour tour() {
        return new Tour();
    }

    @Bean
    public User user() {
        return new User();
    }

}
