package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;

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

}
