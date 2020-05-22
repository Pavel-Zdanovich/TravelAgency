package com.zdanovich.web.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Country.class, CountryMixIn.class);
        objectMapper.addMixIn(Feature.class, FeatureMixIn.class);
        objectMapper.addMixIn(Hotel.class, HotelMixIn.class);
        objectMapper.addMixIn(Review.class, ReviewMixIn.class);
        objectMapper.addMixIn(Tour.class, TourMixIn.class);
        objectMapper.addMixIn(User.class, UserMixIn.class);
        objectMapper.registerModule(new Hibernate5Module());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
