package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.impl.ReviewService;
import com.epam.travelAgency.specification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class ReviewRepository implements Repository<Review> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(AddSpecification<Review> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void update(UpdateSpecification<Review> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public void remove(RemoveSpecification<Review> specification) {
        jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Override
    public Collection<Review> query(FindSpecification<Review, ? extends Object> specification) {
        return jdbcTemplate.query(specification.getSQLQuery(), new ReviewService());//TODO DI
    }

}
