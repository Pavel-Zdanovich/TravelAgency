package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class ReviewServiceTest {

    @Autowired
    private Review review;
    @Autowired
    private Repository<Review> reviewRepository;
    @Autowired
    private Service<Review> reviewService;

    @Test
    public void findAll() {
        Assert.assertNotNull(reviewService.findAll());
    }

    @Test
    public void findById() {
        Assert.assertNotNull(reviewService.findById(1L));
    }

    @Test
    public void update() {
        review.setText(review.getText() + "KEK");
        reviewService.update(review);
    }

    @Test
    public void save() {
        Review review = new Review();
        review.setReviewId(0);
        review.setText("awesome");
        review.setDate(new Timestamp(System.currentTimeMillis()));
        reviewService.save(review);
    }

    @Test
    public void delete() {
        reviewService.delete(review);
    }

    @Test
    public void deleteById() {
        reviewService.deleteById(0);
    }

}