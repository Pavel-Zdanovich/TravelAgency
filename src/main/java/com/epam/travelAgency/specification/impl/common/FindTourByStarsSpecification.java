package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Hotel_;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByStarsSpecification implements FindSpecification<Tour, Integer> {

    public static final String SELECT_TOUR_BY_STARS = "SELECT * FROM tours WHERE hotel_id IN (SELECT hotel_id FROM hotels WHERE stars = %d)";
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

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> tourCriteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> tourRoot = tourCriteriaQuery.from(Tour.class);
        return tourCriteriaQuery.select(tourRoot).where(criteriaBuilder.equal(tourRoot.get(Tour_.HOTEL).get(Hotel_.STARS), this.stars));
    }

}
