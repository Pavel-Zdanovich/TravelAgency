package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FindHotelSpecification implements FindSpecification<Hotel, Hotel> {

    public static final String SELECT_HOTEL = "SELECT * FROM hotels WHERE hotel_id = %d AND name = '%s' AND stars = %d AND website = '%s' AND coordinate = %s AND features = %s";
    @Autowired
    private Hotel hotel;

    public FindHotelSpecification() {}

    public FindHotelSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void setSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public Hotel getSpecification() {
        return this.hotel;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_HOTEL, hotel.getHotelId(), hotel.getName(), hotel.getStars(), hotel.getWebsite(), hotel.getCoordinate(), hotel.getFeatures());//TODO
    }

}
