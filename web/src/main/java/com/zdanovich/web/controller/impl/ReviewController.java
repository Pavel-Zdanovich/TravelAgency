package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.repository.ReviewRepository;
import com.zdanovich.core.service.impl.ReviewService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = ReviewController.PATH)
public class ReviewController extends AbstractController<Review, Long, ReviewRepository, ReviewService> {

    public static final String PATH = "/reviews";

    @Autowired
    protected ReviewController(ReviewService reviewService) {
        super(reviewService);
    }

    @GetMapping(params = "date")
    public ResponseEntity<List<Review>> findByReviewDate(
            @RequestParam
            @NotNull(message = "{review.date.notNull}")
            @FutureOrPresent(message = "{review.date.futureOrPresent}") Timestamp date) {
        return ResponseEntity.ok(service.findByReviewDate(date));
    }

    @GetMapping(params = "text")
    public ResponseEntity<List<Review>> findByReviewText(
            @RequestParam
            @NotEmpty(message = "{review.text.notEmpty}")
            @Size(max = 1000, message = "{review.text.size}") String text) {
        return ResponseEntity.ok(service.findByReviewText(text));
    }

    /*@GetMapping
    public ResponseEntity<List<Review>> findByUser(
            @RequestBody
            @Valid User user) {
        return ResponseEntity.ok(service.findByUser(user));
    }

    @GetMapping
    public ResponseEntity<List<Review>> findByTour(
            @RequestBody
            @Valid Tour tour) {
        return ResponseEntity.ok(service.findByTour(tour));
    }

    @GetMapping()
    public ResponseEntity<Review> findByUserAndTour(
            @RequestBody @Valid User user,
            @RequestBody @Valid Tour tour) {
        return ResponseEntity.of(service.findByUserAndTour(user, tour));
    }*/

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Long> deleteById(
            @RequestParam
            @NotNull(message = "{entity.id.notNull}") Long id) {
        if (this.service.existsById(id)) {
            this.service.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    /*@DeleteMapping
    public ResponseEntity<Iterable<Review>> deleteAll(
            @RequestBody
            @Valid Iterable<Review> iterable) {
        service.deleteAll(iterable);
        return ResponseEntity.ok(iterable);
    }*/
}