package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.impl.HotelService;
import com.epam.travelAgency.specification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class HotelRepository implements Repository<Hotel> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HotelRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(AddSpecification<Hotel> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Hotel> specification) {
        jdbcTemplate.query(specification.getSQLQuery(), specification, rowCallbackHandler -> {});
    }

    @Override
    public void remove(RemoveSpecification<Hotel> specification) {
        jdbcTemplate.query(specification.getSQLQuery(), specification, rowCallbackHandler -> {});
    }

    @Override
    public Collection<Hotel> query(FindSpecification<Hotel, ? extends Object> specification) {
        return jdbcTemplate.query(specification.getSQLQuery(), new HotelService());//TODO DI
    }

}
