package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Country_;
import com.epam.travelAgency.entity.metamodel.Hotel_;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.util.Criterion;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByCriterionSpecification implements FindSpecification<Tour, Criterion> {

    public static final String SELECT_TOUR_BY_CRITERION = "SELECT * FROM tours WHERE %s";
    private Criterion criterion;

    public FindTourByCriterionSpecification() {
    }

    public FindTourByCriterionSpecification(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public void setSpecification(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public Criterion getSpecification() {
        return this.criterion;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_CRITERION, criterion.toString());
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Tour_.TOUR_ID), this.criterion.getTourId()),
                criteriaBuilder.equal(root.get(Tour_.PHOTO_PATH), this.criterion.getPhotoPath()),
                criteriaBuilder.equal(root.get(Tour_.START_DATE), this.criterion.getStartDate()),
                criteriaBuilder.equal(root.get(Tour_.END_DATE), this.criterion.getEndDate()),
                criteriaBuilder.equal(root.get(Tour_.DESCRIPTION), this.criterion.getDescription()),
                criteriaBuilder.equal(root.get(Tour_.COST), this.criterion.getCost()),
                criteriaBuilder.equal(root.get(Tour_.TOUR_TYPE), this.criterion.getTourType()),
                criteriaBuilder.equal(root.get(Tour_.HOTEL).get(Hotel_.HOTEL_ID), this.criterion.getHotelId()),
                criteriaBuilder.equal(root.get(Tour_.COUNTRY).get(Country_.COUNTRY_ID), this.criterion.getCountryId()));
        return criteriaQuery;
    }
}
