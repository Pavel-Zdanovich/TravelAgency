package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.service.Service;
import org.postgresql.util.PGmoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TourService implements Service<Tour> {

    private static final Logger logger = LoggerFactory.getLogger(TourService.class);

    @Override
    public Tour mapRow(ResultSet resultSet, int i) throws SQLException {
        Tour tour = new Tour();
        tour.setTourId(resultSet.getLong("tour_id"));
        tour.setPhotoPath(Paths.get(resultSet.getString("photo")));
        tour.setStartDate(resultSet.getTimestamp("start_date"));
        tour.setEndDate(resultSet.getTimestamp("end_date"));
        tour.setDescription(resultSet.getString("description"));
        tour.setCost(new PGmoney((double) resultSet.getObject("cost")));
        tour.setType(Tour.Type.getType(resultSet.getString("tour_type")));
        tour.setHotelId(resultSet.getLong("hotel_id"));
        tour.setCountryId(resultSet.getLong("country_id"));
        return tour;
    }

}
