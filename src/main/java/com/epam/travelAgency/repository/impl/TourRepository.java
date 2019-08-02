package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Tour;
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
public class TourRepository implements Repository<Tour> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public TourRepository() {
    }

    @Override
    public void add(AddSpecification<Tour> specification) {
        Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Tour> specification) {
        Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<Tour> specification) {
        Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<Tour> query(FindSpecification<Tour, ? extends Object> specification) {
        Session session = sessionFactory.openSession();
        org.hibernate.query.Query<Tour> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<Tour> tours = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return tours;
        //return jdbcTemplate.query(specification.getSQLQuery(), new TourService());//TODO DI
    }

}
