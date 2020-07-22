package com.zdanovich.web.serialization;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.web.serialization.mixin.CountryMixIn;
import com.zdanovich.web.serialization.mixin.FeatureMixIn;
import com.zdanovich.web.serialization.mixin.HotelMixIn;
import com.zdanovich.web.serialization.mixin.ReviewMixIn;
import com.zdanovich.web.serialization.mixin.TourMixIn;
import com.zdanovich.web.serialization.mixin.UserMixIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class SerializationConfiguration {

    @Autowired
    public void customize(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        jackson2ObjectMapperBuilder
                .mixIn(Country.class, CountryMixIn.class)
                .mixIn(Feature.class, FeatureMixIn.class)
                .mixIn(Hotel.class, HotelMixIn.class)
                .mixIn(Review.class, ReviewMixIn.class)
                .mixIn(Tour.class, TourMixIn.class)
                .mixIn(User.class, UserMixIn.class)
                .modules(new Hibernate5Module());
    }
}
