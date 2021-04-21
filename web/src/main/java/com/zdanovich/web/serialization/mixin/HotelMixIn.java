package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Tour;

import java.util.Set;

public class HotelMixIn {

    @JsonBackReference
    private Set<Feature> features; //TODO return in Brief

    @JsonBackReference
    private Set<Tour> tours; //TODO return in Detailed
}
