package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Hotel;

import java.util.Set;

//@JsonIgnoreProperties(value = {"hotels"})
public class FeatureMixIn {

    @JsonBackReference
    private Set<Hotel> hotels;
}
