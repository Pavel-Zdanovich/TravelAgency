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
public class FindTourByStartDateSpecification implements FindSpecification<Tour, Timestamp> {

    public static final String SELECT_TOUR_BY_DATE = "SELECT * FROM tours WHERE start_date = '%s'";
    private Timestamp startDate;

    public FindTourByStartDateSpecification() {}

    public FindTourByStartDateSpecification(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setSpecification(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public Timestamp getSpecification() {
        return this.startDate;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_DATE, this.startDate);
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("start_date"), this.startDate));
        return criteriaQuery;
    }
}
