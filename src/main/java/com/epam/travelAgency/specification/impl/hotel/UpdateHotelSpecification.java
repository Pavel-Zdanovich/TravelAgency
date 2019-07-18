package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.UpdateSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateHotelSpecification implements UpdateSpecification<Hotel> {

    public static final String UPDATE_HOTEL = "UPDATE hotels SET id = ?, name = ?, stars = ?, website = ?, coordinate = ?, feature = ?";
    private Hotel hotel;

    public UpdateHotelSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, hotel.getHotelId());
        preparedStatement.setString(2, hotel.getName());
        preparedStatement.setInt(3, hotel.getStars());
        preparedStatement.setObject(4, hotel.getWebsite());
        preparedStatement.setObject(5, hotel.getCoordinate());
        preparedStatement.setString(6, hotel.getFeature());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_HOTEL;
    }

}
