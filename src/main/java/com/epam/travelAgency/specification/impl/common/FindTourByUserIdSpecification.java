package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

@Component
public class FindTourByUserIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_USER_ID = "SELECT * FROM tours WHERE tour_id IN (SELECT tour_id FROM users_tours WHERE user_id = %d)";
    private Long userId;

    public FindTourByUserIdSpecification() {}

    public FindTourByUserIdSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public void setSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getSpecification() {
        return this.userId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR_BY_USER_ID, this.userId);
    }
}
