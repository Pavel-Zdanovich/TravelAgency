package com.epam.travelAgency.entity;

public enum Feature {

    WI_FI("wi-fi"),
    AIR_CONDITIONER("air conditioner"),
    MINI_BAR("mini-bar"),
    CAR_RENTAL("car rental"),
    ROOM_SERVICE("room service"),
    CABLE_TV("cable tv"),
    SPA("spa"),
    SWIMMING_POOL("swimming pool"),
    RESTAURANT("restaurant"),
    PARKING("parking");

    private final String name;

    Feature(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Feature getFeature(String stringFeature) {
        for (Feature feature : values()) {
            if (feature.name.equals(stringFeature)) {
                return feature;
            }
        }
        throw new IllegalArgumentException("Illegal feature name : " + stringFeature);
    }

}
