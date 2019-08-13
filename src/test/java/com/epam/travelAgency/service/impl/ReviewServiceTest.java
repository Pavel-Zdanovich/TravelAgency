package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.ApplicationConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.service.ReviewService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, TransactionConfig.class, RepositoryConfig.class,
        ServiceConfig.class, ApplicationConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
public class ReviewServiceTest {

    private Review review;
    @Autowired
    private ReviewService reviewService;

    @Before
    public void setUp() throws Exception {
        review = new Review();
        review.setReviewId(1L);
        review.setText("awesome");
        review.setDate(new Timestamp(System.currentTimeMillis()));
        User user = new User();
        user.setUserId(1L);
        review.setUser(user);
        Tour tour = new Tour();
        tour.setTourId(1L);
        review.setTour(tour);
    }

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
        review.setText("incredible");
        review.setDate(new Timestamp(System.currentTimeMillis()));
        reviewService.update(review);
    }

    @Test
    public void save() {
        reviewService.save(review);
    }

    @Test
    public void delete() {
        reviewService.delete(review);
    }

    @Test
    public void deleteById() {
        reviewService.deleteById(1L);
    }

}