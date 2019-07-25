package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindTourByCountryIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_COUNTRY_ID = "SELECT * FROM tours WHERE country_id = %d";
    private Long countryId;

    public FindTourByCountryIdSpecification() {}

    public FindTourByCountryIdSpecification(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public void setSpecification(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public Long getSpecification() {
        return this.countryId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_COUNTRY_ID, this.countryId);
    }

}
