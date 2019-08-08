package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Country_;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        return criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Tour_.COUNTRY).get(Country_.COUNTRY_ID), this.countryId));
    }
}
