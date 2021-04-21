package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zdanovich.core.entity.Hotel;

import java.util.Set;

public class FeatureMixIn {

    @JsonBackReference
    private Set<Hotel> hotels; //TODO return in Detailed
}
