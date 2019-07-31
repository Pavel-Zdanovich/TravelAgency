package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.TourType;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindTourByTypeSpecification implements FindSpecification<Tour, TourType> {

    public static final String SELECT_TOUR_BY_TYPE = "SELECT * FROM tours WHERE tour_type = '%s'";
    private TourType tourType;

    public FindTourByTypeSpecification() {}

    public FindTourByTypeSpecification(TourType tourType) {
        this.tourType = tourType;
    }

    @Override
    public void setSpecification(TourType tourType) {
        this.tourType = tourType;
    }

    @Override
    public TourType getSpecification() {
        return this.tourType;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_TYPE, this.tourType.toString());
    }
}
