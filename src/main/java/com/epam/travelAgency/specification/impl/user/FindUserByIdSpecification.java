package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.entity.metamodel.User_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindUserByIdSpecification implements FindSpecification<User, Long> {

    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = %d";
    private long userId;

    public FindUserByIdSpecification() {}

    public FindUserByIdSpecification(long userId) {
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
        return String.format(SELECT_USER_BY_ID, this.userId);
    }

    @Override
    public CriteriaQuery<User> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        return criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.USER_ID), this.userId));
    }

}
