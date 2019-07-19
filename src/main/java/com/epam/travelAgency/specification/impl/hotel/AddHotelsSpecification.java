package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.AddSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class AddHotelsSpecification implements AddSpecification<Hotel> {

    public static final String INSERT_HOTEL = "INSERT INTO hotels (id, name, stars, website, coordinate, feature) VALUES (?,?,?,?,?,?)";
    private Collection<Hotel> hotels;

    public AddHotelsSpecification(Collection<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        int count = 0;
        for (Hotel hotel : this.hotels){
            preparedStatement.addBatch(INSERT_HOTEL);
            preparedStatement.setLong(1, hotel.getHotelId());
            preparedStatement.setString(2, hotel.getName());
            preparedStatement.setInt(3, hotel.getStars());
            preparedStatement.setObject(4, hotel.getWebsite());
            preparedStatement.setObject(5, hotel.getCoordinate());
            preparedStatement.setString(6, hotel.getFeature().toString());
            count++;
        }
        return count;
    }

    @Override
    public String getSQLQuery() {
        return INSERT_HOTEL;
    }

}
