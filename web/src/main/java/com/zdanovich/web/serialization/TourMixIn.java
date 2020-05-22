package com.zdanovich.web.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"users", "reviews"})
public class TourMixIn {
}
