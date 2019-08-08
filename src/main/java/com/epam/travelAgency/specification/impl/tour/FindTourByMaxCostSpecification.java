package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.postgresql.util.PGmoney;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByMaxCostSpecification implements FindSpecification<Tour, PGmoney> {

    public static final String SELECT_TOUR_BY_MIN_COST = "SELECT * FROM tours WHERE cost <= '%s'";
    private PGmoney maxCost;

    public FindTourByMaxCostSpecification() {}

    public FindTourByMaxCostSpecification(PGmoney maxCost) {
        this.maxCost = maxCost;
    }

    @Override
    public void setSpecification(PGmoney maxCost) {
        this.maxCost = maxCost;
    }

    @Override
    public PGmoney getSpecification() {
        return this.maxCost;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_MIN_COST, this.maxCost.val);
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        return criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get(Tour_.COST), this.maxCost.val));
    }

}
