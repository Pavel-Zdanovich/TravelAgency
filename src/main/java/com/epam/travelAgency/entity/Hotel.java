package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Hotel {

    enum Feature {

        WI_FI("wi-fi"),
        AIR_CONDITIONER("air conditioner"),
        MINI_BAR("mini-bar"),
        CAR_RENTAL("car rental"),
        ROOM_SERVICE("room service"),
        CABLE_TV("cable tv");

        private final String name;

        Feature(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

    }

    private long hotelId;
    private String name;
    private int stars;
    private Object website;//TODO Postgres inet type create
    private Object coordinate;//TODO PostGIS geometry type create;
    private String feature;//TODO equals, hash, toString for each entity

    public Hotel() {
        setHotelId(UUID.randomUUID().timestamp());
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Object coordinate) {
        this.coordinate = coordinate;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
