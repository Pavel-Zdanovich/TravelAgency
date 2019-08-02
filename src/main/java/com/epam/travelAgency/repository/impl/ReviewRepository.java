package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
@Transactional
public class ReviewRepository implements Repository<Review> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public ReviewRepository() {
    }

    @Override
    public void add(AddSpecification<Review> specification) {
        Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Review> specification) {
        Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<Review> specification) {
        Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());
        session.close();
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<Review> query(FindSpecification<Review, ? extends Object> specification) {
        Session session = sessionFactory.openSession();
        org.hibernate.query.Query<Review> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<Review> reviews = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return reviews;
        //return jdbcTemplate.query(specification.getSQLQuery(), new ReviewService());//TODO DI
    }

}
