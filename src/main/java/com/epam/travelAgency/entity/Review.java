package com.epam.travelAgency.entity;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;

@Component
public class Review extends Entity {

    private long reviewId;
    private Timestamp date;
    private String text;
    private long userId;
    private long tourId;

    public Review() {
        this.reviewId = Generators.timeBasedGenerator().generate().timestamp();
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
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
