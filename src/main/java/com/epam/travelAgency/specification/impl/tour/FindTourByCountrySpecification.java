package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Country;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;

public class FindTourByCountrySpecification implements FindSpecification<Tour, Country> {

    public static final String SELECT_TOUR_BY_COUNTRY = "SELECT * FROM tours WHERE country_id = %d";
    private Country country;

    public FindTourByCountrySpecification() {}

    public FindTourByCountrySpecification(Country country) {
        this.country = country;
    }

    @Override
    public void setSpecification(Country country) {
        this.country = country;
    }

    @Override
    public Country getSpecification() {
        return this.country;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_COUNTRY, this.country.getCountryId());
    }

}
