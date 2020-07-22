package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"tours", "reviews"})
public class UserMixIn {
}
