package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.postgresql.util.PGInterval;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class FindtourByDurationSpecification implements FindSpecification<Tour, Period> {

    public static final String SELECT_TOUR_BY_DURATION = "SELECT * FROM tours WHERE age(start_date, end_date) = '%s'";
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
        return String.format(SELECT_TOUR_BY_DURATION, durationToString(period));
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        Expression<PGInterval> duration = criteriaBuilder.function("age", PGInterval.class, root.get(Tour_.START_DATE), root.get(Tour_.END_DATE));
        criteriaQuery.select(root).where(criteriaBuilder.equal(duration.as(PGInterval.class),
                new Date(LocalDateTime.of(period.getYears(), period.getMonths(), period.getDays() + 1, 0, 0).toEpochSecond(ZoneOffset.UTC))));//TODO Invalid value for DayOfMonth (valid values 1 - 28/31): 0
        return criteriaQuery;
    }

    private String durationToString(Period period) {
        StringBuilder stringBuilder = new StringBuilder();
        if (period.getYears() != 0) {
            stringBuilder.append(period.getYears()).append(" years ");
        }
        if (period.getMonths() != 0) {
            stringBuilder.append(period.getMonths()).append(" months ");
        }
        if (period.getDays() != 0) {
            stringBuilder.append(period.getDays()).append(" days");
        }
        return stringBuilder.toString();
    }

}
