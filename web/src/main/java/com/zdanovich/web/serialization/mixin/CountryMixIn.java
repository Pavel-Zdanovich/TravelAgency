package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Tour;

import java.util.Set;

public class CountryMixIn {

    @JsonBackReference
    private Set<Tour> tours; //TODO return in Detailed
}