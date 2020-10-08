package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Tour;

import java.util.Set;

//@JsonIgnoreProperties(value = {"tours"})
public class HotelMixIn {

    @JsonBackReference
    private Set<Feature> features;

    @JsonBackReference
    private Set<Tour> tours;
}
