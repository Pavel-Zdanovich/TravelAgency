package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpdateHotelSpecification implements UpdateSpecification<Hotel> {

    public static final String UPDATE_HOTEL = "UPDATE hotels SET hotel_id = ?, name = ?, stars = ?, website = ?, coordinate = ?, feature = ?";
    @Autowired
    private Hotel hotel;

    public UpdateHotelSpecification() {
    }

    public UpdateHotelSpecification(Hotel hotel) {
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
        preparedStatement.setArray(6, preparedStatement.getConnection().createArrayOf("types_of_features", hotel.getFeatures()));
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_HOTEL;
    }

}
