package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.postgresql.util.PGmoney;

public class FindTourByCostRangeSpecification implements FindSpecification<Tour, PGmoney> {

    public static final String SELECT_TOUR_BY_COST_RANGE = "SELECT * FROM tours WHERE cost between %f and %f";
    private PGmoney minCost = new PGmoney(0);
    private PGmoney maxCost;

    public FindTourByCostRangeSpecification() {
    }

    public FindTourByCostRangeSpecification(PGmoney minCost, PGmoney maxCost) {
        this.minCost = minCost;
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

    public PGmoney getMinCost() {
        return minCost;
    }

    public void setMinCost(PGmoney minCost) {
        this.minCost = minCost;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_COST_RANGE, minCost.val, maxCost.val);
    }

}
