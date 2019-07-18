package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

import java.sql.Timestamp;

public class FindTourByStartDateSpecification implements FindSpecification<Tour, Timestamp> {

    public static final String SELECT_TOUR_BY_DATE = "SELECT * FROM tours WHERE startDate = %s";
    private Timestamp startDate;

    public FindTourByStartDateSpecification() {}

    public FindTourByStartDateSpecification(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setSpecification(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public Timestamp getSpecification() {
        return this.startDate;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_DATE, this.startDate);
    }

}
