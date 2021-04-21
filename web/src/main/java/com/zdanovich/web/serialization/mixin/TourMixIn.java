package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.User;

import java.util.List;
import java.util.Set;

public class TourMixIn {

    @JsonBackReference
    private Set<User> users; //TODO return in Detailed

    @JsonManagedReference
    private Hotel hotel; //Brief

    @JsonManagedReference
    private Country country; //Brief

    @JsonBackReference
    private List<Review> reviews; //TODO return in Detailed
}