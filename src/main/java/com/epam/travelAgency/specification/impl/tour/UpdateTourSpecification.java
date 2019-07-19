package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTourSpecification implements UpdateSpecification<Tour> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTourSpecification.class);
    public static final String UPDATE_TOUR = "UPDATE tours SET tour_id = ?, photo = ?, date = ?, duration = ?, description = ?, cost = ?, tour_type = ?, hotel_id = ?, country_id = ?";
    private Tour tour;

    public UpdateTourSpecification(Tour tour) {
        this.tour = tour;
    }

    @Override
    public int specified(PreparedStatement preparedStatement) throws SQLException {
        int count = 0;
        try {
            preparedStatement.addBatch(UPDATE_TOUR);
            preparedStatement.setLong(1, tour.getTourId());
            preparedStatement.setBlob(2, new FileInputStream(tour.getPhotoPath().toFile()));
            preparedStatement.setTimestamp(3, tour.getStartDate());
            preparedStatement.setTimestamp(4, tour.getEndDate());
            preparedStatement.setString(5, tour.getDescription());
            preparedStatement.setDouble(6, tour.getCost().val);
            preparedStatement.setString(7, tour.getType().toString());
            preparedStatement.setLong(8, tour.getCountryId());
            preparedStatement.setLong(9, tour.getHotelId());
            count++;
        } catch (FileNotFoundException e) {
            logger.error("Creating stream for reading file error");
        }
        return count;
    }

    @Override
    public String getSQLQuery() {
        return UPDATE_TOUR;
    }

}
