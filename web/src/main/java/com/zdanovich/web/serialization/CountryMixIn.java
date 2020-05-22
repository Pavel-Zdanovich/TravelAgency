package com.zdanovich.web.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"tours"})
public class CountryMixIn {
}
