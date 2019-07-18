package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

import java.time.Duration;

public class FindtourByDurationSpecification implements FindSpecification<Tour, Duration> {

    public static final String SELECT_TOUR_BY_DURATION = "SELECT * FROM tours WHERE age(startDate, endDate) = %s";
    private Duration duration;

    @Override
    public void setSpecification(Duration specification) {

    }

    @Override
    public Duration getSpecification() {
        return null;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_DURATION, duration);//TODO duration format ot "43 years 9 mons 27 days"
    }
    //Duration.between(LocalTime.parse(startDate.toString()), LocalTime.parse(endDate.toString()))

}
