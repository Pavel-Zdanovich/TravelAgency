package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindTourByIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_ID = "SELECT * FROM tours WHERE tour_id = %d";
    private Long tourId;

    public FindTourByIdSpecification() {
    }

    public FindTourByIdSpecification(Long tourId) {
        this.tourId = tourId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public void setSpecification(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public Long getSpecification() {
        return this.tourId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_ID, this.tourId);
    }
}
