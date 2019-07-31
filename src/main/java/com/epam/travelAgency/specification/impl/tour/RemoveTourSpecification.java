package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveTourSpecification implements RemoveSpecification<Tour> {

    public static final String DELETE_TOUR = "DELETE FROM tours WHERE tour_id = ? AND photo = ? AND start_date = ? AND end_date = ? AND description = ? AND cost = ? AND tour_type = ?::\"types_of_tours\" AND hotel_id = ? AND country_id = ?";
    @Autowired
    private Tour tour;

    public RemoveTourSpecification() {
    }

    public RemoveTourSpecification(Tour tour) {
        this.tour = tour;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, tour.getTourId());
        preparedStatement.setString(2, tour.getPhotoPath());
        preparedStatement.setTimestamp(3, tour.getStartDate());
        preparedStatement.setTimestamp(4, tour.getEndDate());
        preparedStatement.setString(5, tour.getDescription());
        preparedStatement.setObject(6, tour.getCost());
        preparedStatement.setString(7, tour.getTourType().toString());
        preparedStatement.setLong(8, tour.getCountry().getCountryId());
        preparedStatement.setLong(9, tour.getHotel().getHotelId());
    }

    @Override
    public String getSQLQuery() {
        return DELETE_TOUR;
    }

}
