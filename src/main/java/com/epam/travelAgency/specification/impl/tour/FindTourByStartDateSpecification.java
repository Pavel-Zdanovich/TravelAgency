package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class FindTourByStartDateSpecification implements FindSpecification<Tour, Timestamp> {

    public static final String SELECT_TOUR_BY_DATE = "SELECT * FROM tours WHERE start_date = '%s'";
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
