package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.repository.ReviewRepository;
import com.epam.travelAgency.service.ReviewService;
import com.epam.travelAgency.specification.impl.review.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service(value = "reviewService")
@Transactional(transactionManager = "jpaTransactionManager")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    @Override
    public Collection<Review> findAll() {
        FindAllReviewsSpecification findAllReviewsSpecification = new FindAllReviewsSpecification();
        return reviewRepository.query(findAllReviewsSpecification);
    }

    @Transactional
    @Override
    public Review findById(long reviewId) {
        FindReviewByIdSpecification reviewByIdSpecification = new FindReviewByIdSpecification(reviewId);
        Optional<Review> optionalReview = reviewRepository.query(reviewByIdSpecification).stream().findFirst();
        return optionalReview.orElse(null);
    }

    @Transactional
    @Override
    public void update(Review review) {
        UpdateReviewSpecification updateReviewSpecification = new UpdateReviewSpecification(review);
        reviewRepository.update(updateReviewSpecification);
    }

    @Transactional
    @Override
    public void save(Review review) {
        AddReviewSpecification addReviewSpecification = new AddReviewSpecification(review);
        reviewRepository.add(addReviewSpecification);
    }

    @Transactional
    @Override
    public void delete(Review review) {
        RemoveReviewSpecification removeReviewSpecification = new RemoveReviewSpecification(review);
        reviewRepository.remove(removeReviewSpecification);
    }

    @Transactional
    @Override
    public void deleteById(long reviewId) {
        RemoveReviewByIdSpecification removeReviewByIdSpecification = new RemoveReviewByIdSpecification(reviewId);
        reviewRepository.remove(removeReviewByIdSpecification);
    }

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getLong("review_id"));
        review.setDate(resultSet.getTimestamp("date"));
        review.setText(resultSet.getString("text"));
        return review;
    }
}
