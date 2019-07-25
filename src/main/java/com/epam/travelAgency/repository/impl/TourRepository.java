package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.impl.TourService;
import com.epam.travelAgency.specification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class TourRepository implements Repository<Tour> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TourRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(AddSpecification<Tour> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Tour> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<Tour> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<Tour> query(FindSpecification<Tour, ? extends Object> specification) {
        return jdbcTemplate.query(specification.getSQLQuery(), new TourService());//TODO DI
    }

}
