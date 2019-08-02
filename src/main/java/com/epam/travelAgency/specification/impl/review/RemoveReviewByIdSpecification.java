package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.specification.RemoveSpecification;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RemoveReviewByIdSpecification implements RemoveSpecification<Review> {

    public static final String DELETE_REVIEW_BY_ID = "DELETE FROM reviews WHERE review_id = ?";
    private Long reviewId;

    public RemoveReviewByIdSpecification() {
    }

    public RemoveReviewByIdSpecification(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public Review getEntity() {
        Review review = new Review();
        review.setReviewId(this.reviewId);
        return review;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, this.reviewId);
    }

    @Override
    public String getSQLQuery() {
        return DELETE_REVIEW_BY_ID;
    }

}
