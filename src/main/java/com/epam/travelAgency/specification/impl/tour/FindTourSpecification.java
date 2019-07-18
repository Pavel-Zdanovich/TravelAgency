package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindTourSpecification implements FindSpecification<Tour, Tour> {

    private static final Logger logger = LoggerFactory.getLogger(FindTourSpecification.class);//TODO FindTourWithoutPhoto because its blob
    public static final String SELECT_TOUR = "SELECT * FROM tours WHERE id = %d, date = %s, duration = %s, description = %s, cost = %s, tour_type = %s, hotel_id = %d, country_id = %d";
    private Tour tour;

    public FindTourSpecification() {}

    public FindTourSpecification(Tour tour) {
        this.tour = tour;
    }

    @Override
    public void setSpecification(Tour tour) {
        this.tour = tour;
    }

    @Override
    public Tour getSpecification() {
        return this.tour;
    }

    /*@Override
    public void specified(PreparedStatement preparedStatement, Tour tour) throws SQLException {
        try {
            preparedStatement.setLong(1, tour.getTourId());
            preparedStatement.setBlob(2, new FileInputStream(tour.getPhotoPath().toFile()));
            preparedStatement.setTimestamp(3, tour.getStartDate());
            preparedStatement.setTimestamp(4, tour.getEndDate());
            preparedStatement.setString(5, tour.getDescription());
            preparedStatement.setDouble(6, tour.getCost().val);
            preparedStatement.setString(7, tour.getType());
            preparedStatement.setLong(8, tour.getCountry().getCountryId());
            preparedStatement.setLong(9, tour.getHotel().getHotelId());
        } catch (FileNotFoundException e) {
            logger.error("Creating stream for reading file error");
        }
    }*/

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR, tour.getTourId(), tour.getStartDate(), tour.getEndDate(), tour.getDescription(),
                tour.getCost().val,  tour.getType(), tour.getCountry().getCountryId(), tour.getHotel().getHotelId());
    }

}
