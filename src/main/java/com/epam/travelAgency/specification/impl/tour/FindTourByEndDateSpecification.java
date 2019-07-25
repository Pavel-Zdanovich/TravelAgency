package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class FindTourByEndDateSpecification implements FindSpecification<Tour, Timestamp> {

    public static final String SELECT_TOUR_BY_DURATION = "SELECT * FROM tours WHERE end_date = '%s'";
    private Timestamp endDate;

    public FindTourByEndDateSpecification() {
    }

    public FindTourByEndDateSpecification(Timestamp endDate) {
        this.endDate = endDate;
    }

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
