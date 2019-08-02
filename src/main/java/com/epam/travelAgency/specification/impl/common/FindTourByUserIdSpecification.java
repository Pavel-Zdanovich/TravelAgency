package com.epam.travelAgency.specification.impl.common;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;

@Component
public class FindTourByUserIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_USER_ID = "SELECT * FROM tours WHERE tour_id IN (SELECT tour_id FROM users_tours WHERE user_id = %d)";
    private Long userId;

    public FindTourByUserIdSpecification() {
    }

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

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> tourCriteriaQuery = criteriaBuilder.createQuery(Tour.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Tour> tourRoot = tourCriteriaQuery.from(Tour.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        Join<User, Tour> tours = userRoot.join("tours", JoinType.INNER);
        tourCriteriaQuery.select(tourRoot).where(criteriaBuilder.equal(tours.get("user_id"), this.userId));
        /*Path<List<Tour>> tours = userRoot.get("tours");
        tourCriteriaQuery.select(tourRoot).where(criteriaBuilder.equal(userRoot.join("tours"), tours));*/
        return tourCriteriaQuery;
    }

}
