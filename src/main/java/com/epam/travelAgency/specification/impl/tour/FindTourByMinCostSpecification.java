package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.postgresql.util.PGmoney;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByMinCostSpecification implements FindSpecification<Tour, PGmoney> {

    public static final String SELECT_TOUR_BY_MIN_COST = "SELECT * FROM tours WHERE cost >= '%s'";
    private PGmoney minCost;

    public FindTourByMinCostSpecification() {}

    public FindTourByMinCostSpecification(PGmoney minCost) {
        this.minCost = minCost;
    }

    @Override
    public void setSpecification(PGmoney minCost) {
        this.minCost = minCost;
    }

    @Override
    public PGmoney getSpecification() {
        return this.minCost;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_MIN_COST, this.minCost.val);
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), this.minCost.val));
        return criteriaQuery;
    }

}
