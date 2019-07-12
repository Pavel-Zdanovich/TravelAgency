package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public class Review {

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
}
