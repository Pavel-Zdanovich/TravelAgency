package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.util.CostRange;
import org.postgresql.util.PGmoney;
import org.springframework.stereotype.Component;

@Component
public class FindTourByCostRangeSpecification implements FindSpecification<Tour, CostRange> {

    public static final String SELECT_TOUR_BY_COST_RANGE = "SELECT * FROM tours WHERE cost >= %f::MONEY AND cost <= %f::MONEY";
    private CostRange costRange;

    public FindTourByCostRangeSpecification() {
    }

    public FindTourByCostRangeSpecification(PGmoney minCost, PGmoney maxCost) {
        this.costRange = new CostRange(minCost, maxCost);
    }

    public PGmoney getMinCost() {
        return costRange.getMinCost();
    }

    public void setMinCost(PGmoney minCost) {
        this.costRange.setMinCost(minCost);
    }

    public PGmoney getMaxCost() {
        return costRange.getMaxCost();
    }

    public void setMaxCost(PGmoney maxCost) {
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
        return String.format(SELECT_TOUR_BY_COST_RANGE, costRange.getMinCost().val, costRange.getMaxCost().val);
    }

}
