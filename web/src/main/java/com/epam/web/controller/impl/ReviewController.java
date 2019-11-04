package com.epam.web.controller.impl;

import com.epam.core.entity.Review;
import com.epam.core.entity.Tour;
import com.epam.core.entity.User;
import com.epam.core.repository.ReviewRepository;
import com.epam.core.service.impl.ReviewService;
import com.epam.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController extends AbstractController<Review, Long, ReviewRepository, ReviewService> {

    @Autowired
    protected ReviewController(ReviewService service) {
        super(service);
    }

    public Optional<Review> findByUserAndTour(User user, Tour tour) {
        return service.findByUserAndTour(user, tour);
    }

    public List<Review> findByReviewDate(Timestamp reviewDate) {
        return service.findByReviewDate(reviewDate);
    }

    public List<Review> findByReviewText(String description) {
        return service.findByReviewText(description);
    }

    public List<Review> findByUser(User user) {
        return service.findByUser(user);
    }

    public List<Review> findByTour(Tour tour) {
        return service.findByTour(tour);
    }

    public Optional<Review> findById(Long entityId) {
        return service.findById(entityId);
    }

    public boolean existsById(Long entityId) {
        return service.existsById(entityId);
    }

    public long count() {
        return service.count();
    }

    public void deleteById(Long entityId) {
        service.deleteById(entityId);
    }
}
