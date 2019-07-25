package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.service.Service;
import org.postgis.PGgeometry;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class HotelService implements Service<Hotel> {

    @Override
    public Hotel mapRow(ResultSet resultSet, int i) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(resultSet.getLong("hotel_id"));
        hotel.setName(resultSet.getString("name"));
        hotel.setStars(resultSet.getInt("stars"));
        hotel.setWebsite(resultSet.getString("website"));
        hotel.setCoordinate((PGgeometry) resultSet.getObject("coordinate"));
        hotel.setFeatures((Hotel.Feature[])(resultSet.getArray("features").getArray()));
        return hotel;
    }
}
