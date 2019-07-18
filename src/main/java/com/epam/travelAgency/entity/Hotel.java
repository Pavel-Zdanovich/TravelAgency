package com.epam.travelAgency.entity;

import org.postgis.PGgeometry;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class Hotel extends Entity {

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
    private PGobject website;//TODO Postgres inet type create
    private PGgeometry coordinate;
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

    public PGobject getWebsite() {
        return website;
    }

    public void setWebsite(PGobject website) {
        this.website = website;
    }

    public PGgeometry getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(PGgeometry coordinate) {
        this.coordinate = coordinate;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        } else {
            Hotel hotel = (Hotel) o;
            return getHotelId() == hotel.getHotelId() &&
                    getStars() == hotel.getStars() &&
                    getName().equals(hotel.getName()) &&
                    getWebsite().equals(hotel.getWebsite()) &&
                    getCoordinate().equals(hotel.getCoordinate()) &&
                    getFeature().equals(hotel.getFeature());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHotelId(), getName(), getStars(), getWebsite(), getCoordinate(), getFeature());
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("hotelId=").append(hotelId)
                .append(", name='").append(name).append('\'').append(", stars=").append(stars)
                .append(", website=").append(website).append(", coordinate=").append(coordinate)
                .append(", feature='").append(feature).append('\'').append('}').toString();
    }
}
