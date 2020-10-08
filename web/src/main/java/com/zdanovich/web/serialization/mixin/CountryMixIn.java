package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zdanovich.core.entity.Tour;

import java.util.Set;

//@JsonIgnoreProperties(value = {"tours"})
public class CountryMixIn {

    @JsonBackReference
    private Set<Tour> tours;
}