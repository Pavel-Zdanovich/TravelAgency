package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.specification.impl.common.FindReviewByUserLoginSpecification;
import com.epam.travelAgency.specification.impl.common.FindReviewByUserSpecification;
import com.epam.travelAgency.specification.impl.review.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class})
public class ReviewRepositoryTest {

    @Autowired
    private Review review;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AddReviewSpecification addReviewSpecification;
    @Autowired
    private UpdateReviewSpecification updateReviewSpecification;
    @Autowired
    private RemoveReviewSpecification removeReviewSpecification;
    @Autowired
    private FindReviewSpecification findReviewSpecification;
    @Autowired
    private FindReviewByUserIdSpecification findReviewByUserIdSpecification;
    @Autowired
    private FindReviewByTourIdSpecification findReviewByTourIdSpecification;
    @Autowired
    private FindReviewByUserSpecification findReviewByUserSpecification;
    @Autowired
    private FindReviewByUserLoginSpecification findReviewByUserLoginSpecification;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(EntityConfig.class);
        review = applicationContext.getBean(Review.class);
        review.setReviewId(1L);
        Timestamp timestamp = Timestamp.from(new Timestamp(System.currentTimeMillis()).toInstant().plus(1, ChronoUnit.DAYS));
        review.setDate(timestamp);
        review.setText("some text");
    }

    @Test
    public void add_review_by_addReviewSpecification() {
        addReviewSpecification.setReview(review);
        reviewRepository.add(addReviewSpecification);
    }

    @Test
    public void update_review_by_updateReviewSpecification() {
        updateReviewSpecification.setReview(review);
        reviewRepository.update(updateReviewSpecification);
    }

    @Test
    public void remove_review_by_removeReviewSpecification() {
        removeReviewSpecification.setReview(review);
        reviewRepository.remove(removeReviewSpecification);
    }

    @Test
    public void query_review_by_findReviewSpecification() {
        findReviewSpecification.setSpecification(review);
        reviewRepository.query(findReviewSpecification);
    }

    @Test
    public void query_review_by_findReviewByUserIdSpecification() {
        findReviewByUserIdSpecification.setSpecification(1L);
        reviewRepository.query(findReviewByUserIdSpecification);
    }

    @Test
    public void query_review_by_fingReviewByTourIdSpecification() {
        findReviewByTourIdSpecification.setSpecification(1L);
        reviewRepository.query(findReviewByTourIdSpecification);
    }

    @Test
    public void query_review_by_findReviewByUserSpecification() {
        User user = new User();
        user.setUserId(Long.MAX_VALUE);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        findReviewByUserSpecification.setSpecification(user);
        reviewRepository.query(findReviewByUserSpecification);
    }

    @Test
    public void query_review_by_findReviewByUserLoginSpecification() {
        findReviewByUserLoginSpecification.setSpecification("ElonMusk");
        reviewRepository.query(findReviewByUserLoginSpecification);
    }

}