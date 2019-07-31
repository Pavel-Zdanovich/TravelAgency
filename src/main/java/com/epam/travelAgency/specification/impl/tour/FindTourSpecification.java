package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindTourSpecification implements FindSpecification<Tour, Tour> {

    public static final String SELECT_TOUR = "SELECT * FROM tours WHERE tour_id = %d AND photo = '%s' AND start_date = '%s' AND end_date = '%s' AND description = '%s' AND cost = '%s' AND tour_type = '%s' AND hotel_id = %d AND country_id = %d";
    @Autowired
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

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR, tour.getTourId(), tour.getPhotoPath(), tour.getStartDate(),
                tour.getEndDate(), tour.getDescription(), tour.getCost().val,  tour.getTourType(), tour.getCountry().getCountryId(), tour.getHotel().getHotelId());
    }

}
