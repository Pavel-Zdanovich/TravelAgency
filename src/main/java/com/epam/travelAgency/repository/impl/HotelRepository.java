package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.impl.HotelService;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class HotelRepository implements Repository<Hotel> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    public HotelRepository() {}

    /*@Autowired
    public HotelRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }*/

    @Override
    public void add(AddSpecification<Hotel> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Hotel> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<Hotel> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<Hotel> query(FindSpecification<Hotel, ? extends Object> specification) {
        return jdbcTemplate.query(specification.getSQLQuery(), new HotelService());//TODO DI
    }

}
