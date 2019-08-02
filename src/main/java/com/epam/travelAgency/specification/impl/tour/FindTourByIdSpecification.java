package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_ID = "SELECT * FROM tours WHERE tour_id = %d";
    private Long tourId;

    public FindTourByIdSpecification() {
    }

    public FindTourByIdSpecification(Long tourId) {
        this.tourId = tourId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public void setSpecification(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public Long getSpecification() {
        return this.tourId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_ID, this.tourId);
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour_id"), this.tourId));
        return criteriaQuery;
    }

}
