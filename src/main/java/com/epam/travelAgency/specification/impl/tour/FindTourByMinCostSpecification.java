package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.postgresql.util.PGmoney;
import org.springframework.stereotype.Component;

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
}
