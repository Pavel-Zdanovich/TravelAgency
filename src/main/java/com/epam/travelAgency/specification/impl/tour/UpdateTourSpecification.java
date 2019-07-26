package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UpdateTourSpecification implements UpdateSpecification<Tour> {

    public static final String UPDATE_TOUR = "UPDATE tours SET photo = ?, start_date = ?, end_date = ?, description = ?, cost = ?, tour_type = ?::\"types_of_tours\", hotel_id = ?, country_id = ? WHERE tour_id = ?";
    @Autowired
    private Tour tour;

    public UpdateTourSpecification() {
    }

    public UpdateTourSpecification(Tour tour) {
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
        preparedStatement.setString(1, tour.getPhotoPath().toAbsolutePath().toString());
        preparedStatement.setTimestamp(2, tour.getStartDate());
        preparedStatement.setTimestamp(3, tour.getEndDate());
        preparedStatement.setString(4, tour.getDescription());
        preparedStatement.setObject(5, tour.getCost());
        preparedStatement.setString(6, tour.getType().toString());
        preparedStatement.setLong(7, tour.getCountryId());
        preparedStatement.setLong(8, tour.getHotelId());
        preparedStatement.setLong(9, tour.getTourId());
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_TOUR;
    }

}
