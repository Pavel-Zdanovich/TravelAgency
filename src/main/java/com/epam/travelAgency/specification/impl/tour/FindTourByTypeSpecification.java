package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

public class FindTourByTypeSpecification implements FindSpecification<Tour, String> {//TODO replace string with Tour.Type

    public static final String SELECT_TOUR_BY_TYPE = "SELECT * FROM tours WHERE tour_type = %s";
    private String type;//TODO decide string or enum

    public FindTourByTypeSpecification() {}

    public FindTourByTypeSpecification(String type) {
        this.type = type;
    }

    @Override
    public void setSpecification(String type) {
        this.type = type;
    }

    @Override
    public String getSpecification() {
        return this.type;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_TYPE, this.type);
    }
}
