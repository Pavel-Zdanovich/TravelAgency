package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;

import java.util.List;
import java.util.Set;

//@JsonIgnoreProperties(value = {"tours", "reviews"})
public class UserMixIn {

    @JsonBackReference
    private Set<Tour> tours;

    @JsonBackReference
    private List<Review> reviews;
}