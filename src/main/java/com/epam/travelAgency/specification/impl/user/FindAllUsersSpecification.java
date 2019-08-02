package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindAllUsersSpecification implements FindSpecification<User, Object> {

    public static final String SELECT_ALL_USERS = "SELECT * FROM users";

    @Override
    public void setSpecification(Object specification) {
        //TODO
    }

    @Override
    public Object getSpecification() {
        return null;//TODO
    }

    @Override
    public String getSQLQuery() {
        return SELECT_ALL_USERS;
    }

    @Override
    public CriteriaQuery<User> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        return criteriaQuery;
    }
}
