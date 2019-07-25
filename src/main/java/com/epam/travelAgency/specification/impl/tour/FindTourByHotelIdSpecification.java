package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindTourByHotelIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_HOTEL_ID = "SELECT * FROM tours WHERE hotel_id = %d";
    private Long hotelId;

    public FindTourByHotelIdSpecification() {
    }

    public FindTourByHotelIdSpecification(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public void setSpecification(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public Long getSpecification() {
        return this.hotelId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_HOTEL_ID, this.hotelId);
    }

}
