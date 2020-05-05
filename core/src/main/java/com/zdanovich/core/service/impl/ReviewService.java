package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.repository.ReviewRepository;
import com.zdanovich.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService extends AbstractService<Review, Long, ReviewRepository> {

    @Autowired
    public ReviewService(ReviewRepository repository) {
        super(repository);
    }

    public Optional<Review> findByUserAndTour(User user, Tour tour) {
        return repository.findByUserAndTour(user, tour);
    }

    public Optional<Review> findByUser_UserId_And_Tour_TourId(Long userId, Long tourId) {
        return repository.findByUser_IdAndTour_Id(userId, tourId);
    }

    public List<Review> findByReviewDate(Timestamp reviewDate) {
        return repository.findByReviewDate(reviewDate);
    }

    public List<Review> findByReviewDateAfter(Timestamp reviewDate) {
        return repository.findByReviewDateAfter(reviewDate);
    }

    public List<Review> findByReviewDateBefore(Timestamp reviewDate) {
        return repository.findByReviewDateBefore(reviewDate);
    }

    public List<Review> findByReviewDateBetween(Timestamp reviewDate1, Timestamp reviewDate2) {
        return repository.findByReviewDateBetween(reviewDate1, reviewDate2);
    }

    public List<Review> findByReviewText(String description) {
        return repository.findByReviewText(description);
    }

    public List<Review> findByReviewTextContaining(String reviewText) {
        return repository.findByReviewTextContaining(reviewText);
    }

    public List<Review> findByReviewTextLike(String regexp) {
        return repository.findByReviewTextLike(regexp);
    }

    public List<Review> findByUser(User user) {
        return repository.findByUser(user);
    }

    public List<Review> findByUser_Id(Long userId) {
        return repository.findByUser_Id(userId);
    }

    public List<Review> findByUser_Login(String userLogin) {
        return repository.findByUser_Login(userLogin);
    }

    public List<Review> findByTour(Tour tour) {
        return repository.findByTour(tour);
    }

    public List<Review> findByTour_TourId(Long tourId) {
        return repository.findByTour_Id(tourId);
    }

    public List<Review> findByTour_TourType(TourType tourType) {
        return repository.findByTour_TourType(tourType);
    }

    public List<Review> findByTour_Hotel(Hotel hotel) {
        return repository.findByTour_Hotel(hotel);
    }

    public List<Review> findByTour_Country(Country country) {
        return repository.findByTour_Country(country);
    }

}
