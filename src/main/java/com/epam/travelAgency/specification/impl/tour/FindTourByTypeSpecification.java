package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.TourType;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByTypeSpecification implements FindSpecification<Tour, TourType> {

    public static final String SELECT_TOUR_BY_TYPE = "SELECT * FROM tours WHERE tour_type = '%s'";
    private TourType tourType;

    public FindTourByTypeSpecification() {}

    public FindTourByTypeSpecification(TourType tourType) {
        this.tourType = tourType;
    }

    @Override
    public void setSpecification(TourType tourType) {
        this.tourType = tourType;
    }

    @Override
    public TourType getSpecification() {
        return this.tourType;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_TYPE, this.tourType.toString());
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour_type"), this.tourType.toString()));
        return criteriaQuery;
    }
}
