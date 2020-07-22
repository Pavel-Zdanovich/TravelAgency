package com.zdanovich.web.serialization.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"hotels"})
public class FeatureMixIn {
}
