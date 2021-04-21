package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;

import java.util.List;
import java.util.Set;

public class UserMixIn {

    @JsonBackReference
    private Set<Tour> tours; //TODO return in Detailed

    @JsonBackReference
    private List<Review> reviews; //TODO return in Detailed
}