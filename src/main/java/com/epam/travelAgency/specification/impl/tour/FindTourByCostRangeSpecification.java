package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.util.CostRange;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

@Component
public class FindTourByCostRangeSpecification implements FindSpecification<Tour, CostRange> {

    public static final String SELECT_TOUR_BY_COST_RANGE = "SELECT * FROM tours WHERE cost >= %f AND cost <= %f";
    private CostRange costRange;

    public FindTourByCostRangeSpecification() {
    }

    public FindTourByCostRangeSpecification(BigDecimal minCost, BigDecimal maxCost) {
        this.costRange = new CostRange(minCost, maxCost);
    }

    public BigDecimal getMinCost() {
        return costRange.getMinCost();
    }

    public void setMinCost(BigDecimal minCost) {
        this.costRange.setMinCost(minCost);
    }

    public BigDecimal getMaxCost() {
        return costRange.getMaxCost();
    }

    public void setMaxCost(BigDecimal maxCost) {
        this.costRange.setMaxCost(maxCost);
    }

    @Override
    public void setSpecification(CostRange costRange) {
        this.costRange = costRange;
    }

    @Override
    public CostRange getSpecification() {
        return costRange;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_COST_RANGE, costRange.getMinCost(), costRange.getMaxCost());
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        return criteriaQuery.select(root).where(criteriaBuilder.between(root.get(Tour_.COST), costRange.getMinCost(), costRange.getMaxCost()));
    }
}
