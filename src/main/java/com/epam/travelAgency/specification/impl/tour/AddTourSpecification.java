package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.AddSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class AddTourSpecification implements AddSpecification<Tour> {

    public static final String INSERT_TOUR = "INSERT INTO tours (tour_id, photo, start_date, end_date, description, cost, tour_type, hotel_id, country_id) VALUES (?,?,?,?,?,?,?::\"types_of_tours\",?,?)";
    @Autowired
    private Tour tour;

    public AddTourSpecification() {
    }

    public AddTourSpecification(Tour tour) {
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
        preparedStatement.setString(2, tour.getPhotoPath().toAbsolutePath().toString());
        preparedStatement.setTimestamp(3, tour.getStartDate());
        preparedStatement.setTimestamp(4, tour.getEndDate());
        preparedStatement.setString(5, tour.getDescription());
        preparedStatement.setObject(6, tour.getCost());
        preparedStatement.setString(7, tour.getType().toString());
        preparedStatement.setLong(8, tour.getCountryId());
        preparedStatement.setLong(9, tour.getHotelId());
    }

    @Override
    public String getSQLQuery() {
        return INSERT_TOUR;
    }

}
