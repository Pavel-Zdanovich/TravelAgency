package com.epam.travelAgency.specification.impl.review;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.metamodel.Review_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindReviewSpecification implements FindSpecification<Review, Review> {

    public static final String SELECT_REVIEW = "SELECT * FROM reviews WHERE review_id = %d AND date = '%s' AND text = '%s' AND user_id = %d AND tour_id = %d";
    @Autowired
    private Review review;

    public FindReviewSpecification() {}

    public FindReviewSpecification(Review review) {
        this.review = review;
    }

    @Override
    public void setSpecification(Review review) {
        this.review = review;
    }

    @Override
    public Review getSpecification() {
        return this.review;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_REVIEW, review.getReviewId(), review.getDate(), review.getText(), review.getUser().getUserId(), review.getTour().getTourId());
    }

    @Override
    public CriteriaQuery<Review> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Review_.REVIEW_ID), review.getReviewId()),
                criteriaBuilder.equal(root.get(Review_.DATE), review.getDate()),
                criteriaBuilder.equal(root.get(Review_.TEXT), review.getText()),
                criteriaBuilder.equal(root.get(Review_.USER), review.getUser()),
                criteriaBuilder.equal(root.get(Review_.TOUR), review.getTour()));
        return criteriaQuery;
    }
}
