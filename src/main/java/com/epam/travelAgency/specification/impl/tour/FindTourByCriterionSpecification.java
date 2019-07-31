package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.util.Criterion;
import org.springframework.stereotype.Component;

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

}
