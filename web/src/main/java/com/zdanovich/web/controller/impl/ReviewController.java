package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Review;
import com.zdanovich.core.repository.ReviewRepository;
import com.zdanovich.core.service.impl.ReviewService;
import com.zdanovich.web.controller.AbstractController;
import com.zdanovich.web.security.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = ReviewController.PATH)
public class ReviewController extends AbstractController<Review, Long, ReviewRepository, ReviewService> {

    public static final String PATH = "/reviews";

    @Autowired
    protected ReviewController(ReviewService reviewService) {
        super(reviewService);
    }

    @GetMapping(params = "date")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<List<Review>> findByReviewDate(
            @RequestParam
            @NotNull(message = "{review.date.notNull}")
            @FutureOrPresent(message = "{review.date.futureOrPresent}") Timestamp date) {
        return ResponseEntity.ok(service.findByReviewDate(date));
    }

    @GetMapping(params = "text")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<List<Review>> findByReviewText(
            @RequestParam
            @NotEmpty(message = "{review.text.notEmpty}")
            @Size(max = 1000, message = "{review.text.size}") String text) {
        return ResponseEntity.ok(service.findByReviewText(text));
    }

    @GetMapping(params = "user_id, tour_id")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<Iterable<Review>> findByUserAndTour(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "tour_id") Long tourId) {

        List<Review> reviews = new ArrayList<>();

        if (userId != null && tourId != null) {

            Optional<Review> optionalReview = service.findByUser_UserId_And_Tour_TourId(userId, tourId);

            if (optionalReview.isPresent()) {
                reviews.add(optionalReview.get());
                return ResponseEntity.ok(reviews);
            } else {
                return ResponseEntity.notFound().build();
            }

        } else if (userId != null) {

            reviews = service.findByUser_Id(userId);
            if (reviews != null && !reviews.isEmpty()) {
                return ResponseEntity.ok(reviews);
            } else {
                return ResponseEntity.notFound().build();
            }

        } else {

            reviews = service.findByTour_TourId(tourId);
            if (reviews != null && !reviews.isEmpty()) {
                return ResponseEntity.ok(reviews);
            } else {
                return ResponseEntity.notFound().build();
            }

        }
    }
}