package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Component
public class Review extends Entity {

    private long reviewId;
    private Timestamp date;//TODO find timestamp with time zone mapping type in java
    private String text;

    public Review() {
        this.reviewId = UUID.randomUUID().timestamp();
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        } else {
            Review review = (Review) o;
            return getReviewId() == review.getReviewId() &&
                    getDate().equals(review.getDate()) &&
                    getText().equals(review.getText());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviewId(), getDate(), getText());
    }

}
