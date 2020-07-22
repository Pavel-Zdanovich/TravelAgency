package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"tours"})
public class HotelMixIn {
}
