package com.zdanovich.web.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@Configuration
public class SerializationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().
                addMixIn(Country.class, CountryMixIn.class).
                addMixIn(Feature.class, FeatureMixIn.class).
                addMixIn(Hotel.class, HotelMixIn.class).
                addMixIn(Review.class, ReviewMixIn.class).
                addMixIn(Tour.class, TourMixIn.class).
                addMixIn(User.class, UserMixIn.class).
                registerModule(new Hibernate5Module()).
                //registerModule(new CoreJackson2Module()).
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).
                //enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS).
                //enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN).
                //setNodeFactory(JsonNodeFactory.withExactBigDecimals(true)).
                setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
