package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

@Component
public class FindTourByEndDateSpecification implements FindSpecification<Tour, Timestamp> {

    public static final String SELECT_TOUR_BY_END_DATE = "SELECT * FROM tours WHERE end_date = '%s'";
    private Timestamp endDate;

    public FindTourByEndDateSpecification() {
    }

    public FindTourByEndDateSpecification(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public void setSpecification(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public Timestamp getSpecification() {
        return this.endDate;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_END_DATE, this.endDate);
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("end_date"), this.endDate));
        return criteriaQuery;
    }
}
