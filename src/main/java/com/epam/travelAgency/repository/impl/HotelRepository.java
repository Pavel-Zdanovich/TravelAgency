package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Hotel;
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
public class HotelRepository implements Repository<Hotel> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public HotelRepository() {}

    @Override
    public void add(AddSpecification<Hotel> specification) {
        Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Hotel> specification) {
        Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<Hotel> specification) {
        Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());//Hibernate execute DELETE FROM hotels WHERE hotel_id = ?
        session.close();
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<Hotel> query(FindSpecification<Hotel, ? extends Object> specification) {
        Session session = sessionFactory.openSession();
        org.hibernate.query.Query<Hotel> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<Hotel> hotels = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return hotels;
        //return jdbcTemplate.query(specification.getSQLQuery(), new HotelService());//TODO DI
    }

}
