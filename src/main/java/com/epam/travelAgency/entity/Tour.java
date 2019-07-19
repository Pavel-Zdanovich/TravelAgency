package com.epam.travelAgency.entity;

import org.postgresql.util.PGmoney;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Component
public class Tour extends Entity {

    public enum Type {

        TREATMENT("treatment"),
        TOURISM("tourism"),
        LEISURE("leisure"),
        BUSINESS("business"),
        PILGRIMAGE("pilgrimage"),
        TRAINING("training"),
        SPORT_COMPETITION("sport competition"),
        RURAL_TOURISM("rural tourism"),
        SCIENTIFIC_EXPEDITION("scientific expedition"),
        ECOTOURISM("ecotourism");

        private final String type;

        Type(String type) {
            this.type = type;
        }


        @Override
        public String toString() {
            return this.type;
        }
    }

    private long tourId;
    private Path photoPath;//TODO PGbytea exist
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private PGmoney cost;
    private Type type;
    private long hotelId;
    private long countryId;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
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
                    getType().equals(tour.getType()) &&
                    getHotelId() == tour.getHotelId() &&
                    getCountryId() == tour.getCountryId();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTourId(), getPhotoPath(), getStartDate(), getEndDate(), getDescription(), getType(), getHotelId(), getCountryId());
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("tourId=").append(tourId)
                .append(", photoPath=").append(photoPath).append(", startDate=").append(startDate)
                .append(", endDate=").append(endDate).append(", description='")
                .append(description).append('\'').append(", type=").append(type)
                .append(", hotelId=").append(hotelId).append(", countryId=").append(countryId).append('}').toString();
    }
}
