package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.RemoveSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveHotelSpecification implements RemoveSpecification<Hotel> {

    public static final String DELETE_HOTEL = "DELETE FROM hotels WHERE id = ?, name = ?, stars = ?, website = ?, coordinate = ?, feature = ?";
    private Hotel hotel;

    public RemoveHotelSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.addBatch(DELETE_HOTEL);
        preparedStatement.setLong(1, hotel.getHotelId());
        preparedStatement.setString(2, hotel.getName());
        preparedStatement.setInt(3, hotel.getStars());
        preparedStatement.setObject(4, hotel.getWebsite());
        preparedStatement.setObject(5, hotel.getCoordinate());
        preparedStatement.setString(6, hotel.getFeature().toString());
        return 1;
    }

    @Override
    public String getSQLQuery() {
        return DELETE_HOTEL;
    }

}
