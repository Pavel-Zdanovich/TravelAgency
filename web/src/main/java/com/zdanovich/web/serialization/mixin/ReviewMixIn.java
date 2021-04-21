package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;

public class ReviewMixIn {

    @JsonManagedReference
    private User user; //Brief

    @JsonManagedReference
    private Tour tour; //TODO serialize tour as brief
}