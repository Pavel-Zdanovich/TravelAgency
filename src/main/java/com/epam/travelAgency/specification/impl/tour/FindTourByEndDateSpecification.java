package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

import java.sql.Timestamp;

public class FindTourByEndDateSpecification implements FindSpecification<Tour, Timestamp> {

    public static final String SELECT_TOUR_BY_DURATION = "SELECT * FROM tours WHERE endDate = %s";
    private Timestamp endDate;

    @Override
    public void setSpecification(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public Timestamp getSpecification() {
        return this.endDate;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_DURATION, this.endDate);
    }
}
