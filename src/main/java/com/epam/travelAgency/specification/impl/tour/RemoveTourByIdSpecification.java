package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveTourByIdSpecification implements RemoveSpecification<Tour> {

    public static final String DELETE_TOUR_BY_ID = "DELETE FROM tours WHERE tour_id = ?";
    private Long tourId;

    public RemoveTourByIdSpecification() {
    }

    public RemoveTourByIdSpecification(Long tourId) {
        this.tourId = tourId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public Tour getEntity() {
        Tour tour = new Tour();
        tour.setTourId(this.tourId);
        return tour;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, this.tourId);
    }

    @Override
    public String getSQLQuery() {
        return DELETE_TOUR_BY_ID;
    }

}
