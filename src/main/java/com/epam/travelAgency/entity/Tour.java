package com.epam.travelAgency.entity;

import org.postgresql.util.PGmoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Component
public class Tour extends Entity {

    enum Type {

        TREATMENT,
        TOURISM,
        LEISURE,
        BUSINESS

    }

    private long tourId;
    private Path photoPath;//TODO PGbytea exist
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private PGmoney cost;
    private Type type;//TODO make the same as Hotel.Feature
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PGmoney getCost() {
        return cost;
    }

    public void setCost(PGmoney cost) {
        this.cost = cost;
    }

    public String getType() {
        return type.name();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Hotel getHotel() {
        return hotel;
    }

    @Autowired
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Country getCountry() {
        return country;
    }

    @Autowired
    public void setCountry(Country country) {
        this.country = country;
    }

    public static Type defineType(String type) {
        return Type.valueOf(type);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tour)) {
            return false;
        } else {
            Tour tour = (Tour) o;
            return getTourId() == tour.getTourId() &&
                    getPhotoPath().equals(tour.getPhotoPath()) &&
                    getStartDate().equals(tour.getStartDate()) &&
                    getEndDate().equals(tour.getEndDate()) &&
                    getDescription().equals(tour.getDescription()) &&
                    getType() == tour.getType() &&
                    getHotel().equals(tour.getHotel()) &&
                    getCountry().equals(tour.getCountry());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTourId(), getPhotoPath(), getStartDate(), getEndDate(), getDescription(), getType(), getHotel(), getCountry());
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("tourId=").append(tourId)
                .append(", photoPath=").append(photoPath).append(", startDate=").append(startDate)
                .append(", endDate=").append(endDate).append(", description='")
                .append(description).append('\'').append(", type=").append(type)
                .append(", hotel=").append(hotel).append(", country=").append(country).append('}').toString();
    }
}
