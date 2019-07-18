package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveTourSpecification implements RemoveSpecification<Tour> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTourSpecification.class);
    public static final String DELETE_TOUR = "DELETE FROM tours WHERE id = ?, photo = ?, date = ?, duration = ?, description = ?, cost = ?, tour_type = ?, hotel_id = ?, country_id = ?";
    private Tour tour;

    public RemoveTourSpecification(Tour tour) {
        this.tour = tour;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        int count = 0;
        try {
            preparedStatement.addBatch(DELETE_TOUR);
            preparedStatement.setLong(1, tour.getTourId());
            preparedStatement.setBlob(2, new FileInputStream(tour.getPhotoPath().toFile()));
            preparedStatement.setTimestamp(3, tour.getStartDate());
            preparedStatement.setTimestamp(4, tour.getEndDate());
            preparedStatement.setString(5, tour.getDescription());
            preparedStatement.setDouble(6, tour.getCost().val);
            preparedStatement.setString(7, tour.getType());
            preparedStatement.setLong(8, tour.getCountry().getCountryId());
            preparedStatement.setLong(9, tour.getHotel().getHotelId());
            count++;
        } catch (FileNotFoundException e) {
            logger.error("Creating stream for reading file error");
        }
        return count;
    }

    @Override
    public String getSQLQuery() {
        return DELETE_TOUR;
    }

}
