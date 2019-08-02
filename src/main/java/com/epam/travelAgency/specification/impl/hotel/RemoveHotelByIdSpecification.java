package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveHotelByIdSpecification implements RemoveSpecification<Hotel> {

    public static final String DELETE_HOTEL_BY_ID = "DELETE FROM hotels WHERE hotel_id = ?";
    private Long hotelId;

    public RemoveHotelByIdSpecification() {
    }

    public RemoveHotelByIdSpecification(Long hotelId) {
        this.hotelId = hotelId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public Hotel getEntity() {
        Hotel hotel = new Hotel();
        hotel.setHotelId(this.hotelId);
        return hotel;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, this.hotelId);
    }

    @Override
    public String getSQLQuery() {
        return DELETE_HOTEL_BY_ID;
    }

}
