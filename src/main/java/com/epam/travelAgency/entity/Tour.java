package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class Tour {

    enum Type {
        TREATMENT,
        TOURISM,
        LEISURE,
        BUSINESS
    }

    private long tourId;
    private Path photoPath;
    private Timestamp date;
    private Timestamp duration;
    private String description;
    private Type type;
    private Hotel hotel;//TODO DI
    private Country country;

    public Tour() {
        this.tourId = UUID.randomUUID().timestamp();
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public Path getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(Path photoPath) {
        this.photoPath = photoPath;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDuration() {
        return duration;
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
