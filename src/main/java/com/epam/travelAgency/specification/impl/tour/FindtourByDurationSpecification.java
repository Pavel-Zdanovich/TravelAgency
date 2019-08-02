package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.Period;

@Component
public class FindtourByDurationSpecification implements FindSpecification<Tour, Period> {

    public static final String SELECT_TOUR_BY_DURATION = "SELECT * FROM tours WHERE age(start_date, end_date) = '%s years %s months %s days'";
    private Period period;

    public FindtourByDurationSpecification() {
    }

    public FindtourByDurationSpecification(Period period) {
        this.period = period;
    }

    public FindtourByDurationSpecification(Timestamp startDate, Timestamp endDate) {
        this.period = Period.between(startDate.toLocalDateTime().toLocalDate(), endDate.toLocalDateTime().toLocalDate());
    }

    public void setDuration(Timestamp from, Timestamp to) {
        this.period = Period.between(from.toLocalDateTime().toLocalDate(), to.toLocalDateTime().toLocalDate());
    }

    @Override
    public void setSpecification(Period period) {
        this.period = period;
    }

    @Override
    public Period getSpecification() {
        return this.period;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_DURATION, period.getYears(), period.getMonths(), period.getDays());
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        Expression<String> duration = criteriaBuilder.function("age", String.class, root.get("start_date"), root.get("end_date"));
        StringBuilder stringBuilder = new StringBuilder(period.getYears()).append(" years").append(period.getMonths()).append(" months").append(period.getDays()).append(" days");
        criteriaQuery.select(root).where(criteriaBuilder.equal(duration, stringBuilder.toString()));
        return criteriaQuery;
    }
}
