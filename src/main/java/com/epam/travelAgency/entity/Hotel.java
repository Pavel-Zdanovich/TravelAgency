package com.epam.travelAgency.entity;

import com.fasterxml.uuid.Generators;
import org.postgis.PGgeometry;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Hotel extends Entity {

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

    private long hotelId;
    private String name;
    private int stars;
    private String website;
    private PGgeometry coordinate;
    private Feature[] features;

    public Hotel() {
        this.hotelId = Generators.timeBasedGenerator().generate().timestamp();
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public PGgeometry getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(PGgeometry coordinate) {
        this.coordinate = coordinate;
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
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
                    getFeatures().equals(hotel.getFeatures());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHotelId(), getName(), getStars(), getWebsite(), getCoordinate(), getFeatures());
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("hotelId=").append(hotelId)
                .append(", name='").append(name).append('\'').append(", stars=").append(stars)
                .append(", website=").append(website).append(", coordinate=").append(coordinate)
                .append(", features={").append(features.toString()).append('}').append('}').toString();
    }
}
