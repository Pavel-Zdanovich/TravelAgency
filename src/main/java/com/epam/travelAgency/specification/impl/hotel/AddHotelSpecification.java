package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.AddSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

@Component
public class AddHotelSpecification implements AddSpecification<Hotel> {

    public static final String INSERT_HOTEL = "INSERT INTO hotels (hotel_id, name, stars, website, coordinate, features) VALUES (?,?,?,?,?,?)";
    @Autowired
    private Hotel hotel;

    public AddHotelSpecification() {
    }

    public AddHotelSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, hotel.getHotelId());
        preparedStatement.setString(2, hotel.getName());
        preparedStatement.setInt(3, hotel.getStars());
        preparedStatement.setObject(4, hotel.getWebsite());
        preparedStatement.setObject(5, hotel.getCoordinate());
        preparedStatement.setArray(6, preparedStatement.getConnection().createArrayOf("types_of_features", hotel.getFeatures()));//Unable to find server array type for provided name types_of_features.
    }

    @Override
    public String getSQLQuery() {
        return INSERT_HOTEL;
    }

}
