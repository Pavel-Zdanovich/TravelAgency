package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;


public class FindTourByStarsSpecification implements FindSpecification<Tour, Integer> {

    public static final String SELECT_TOUR_BY_STARS = "SELECT * FROM tours WHERE stars = %d";
    private Integer stars;

    public FindTourByStarsSpecification() {}

    public FindTourByStarsSpecification(Integer stars) {
        this.stars = stars;
    }

    @Override
    public void setSpecification(Integer stars) {
        this.stars = stars;
    }

    @Override
    public Integer getSpecification() {
        return this.stars;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_STARS, this.stars);
    }

}
