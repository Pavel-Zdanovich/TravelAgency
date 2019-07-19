package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

public class FindTourByTypeSpecification implements FindSpecification<Tour, Tour.Type> {

    public static final String SELECT_TOUR_BY_TYPE = "SELECT * FROM tours WHERE tour_type = %s";
    private Tour.Type type;//TODO decide string or enum

    public FindTourByTypeSpecification() {}

    public FindTourByTypeSpecification(Tour.Type type) {
        this.type = type;
    }

    @Override
    public void setSpecification(Tour.Type type) {
        this.type = type;
    }

    @Override
    public Tour.Type getSpecification() {
        return this.type;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_TYPE, this.type.toString());
    }
}
