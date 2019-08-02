package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveHotelSpecification implements RemoveSpecification<Hotel> {

    public static final String DELETE_HOTEL = "DELETE FROM hotels WHERE hotel_id = ? AND name = ? AND stars = ? AND website = ? AND coordinate = ? AND features = ?";
    @Autowired
    private Hotel hotel;

    public RemoveHotelSpecification() {
    }

    public RemoveHotelSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public Hotel getEntity() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, hotel.getHotelId());
        preparedStatement.setString(2, hotel.getName());
        preparedStatement.setInt(3, hotel.getStars());
        preparedStatement.setString(4, hotel.getWebsite().toString());
        preparedStatement.setObject(5, hotel.getCoordinate());
        preparedStatement.setArray(6, preparedStatement.getConnection().createArrayOf("types_of_features", hotel.getFeatures()));
    }

    @Override
    public String getSQLQuery() {
        return DELETE_HOTEL;
    }

}
