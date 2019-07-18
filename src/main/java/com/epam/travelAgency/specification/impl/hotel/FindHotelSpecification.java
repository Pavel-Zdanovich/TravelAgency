package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.FindSpecification;

public class FindHotelSpecification implements FindSpecification<Hotel, Hotel> {

    public static final String SELECT_HOTEL = "SELECT * FROM hotels WHERE id = %d, name = %s, stars = %d, website = %s, coordinate = %s, feature = %s";
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

    /*@Override
    public void specified(PreparedStatement preparedStatement, Hotel hotel) throws SQLException {
        preparedStatement.setLong(1, hotel.getHotelId());
        preparedStatement.setString(2, hotel.getName());
        preparedStatement.setInt(3, hotel.getStars());
        preparedStatement.setObject(4, hotel.getWebsite());
        preparedStatement.setObject(5, hotel.getCoordinate());
        preparedStatement.setString(6, hotel.getFeature());
    }*/

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_HOTEL, hotel.getHotelId(), hotel.getName(), hotel.getStars(), hotel.getWebsite(), hotel.getCoordinate(), hotel.getFeature());
    }

}
