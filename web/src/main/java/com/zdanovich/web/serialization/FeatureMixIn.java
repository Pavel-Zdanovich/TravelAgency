package com.zdanovich.web.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"hotels"})
public class FeatureMixIn {
}
