package com.epam.core.service.impl;

import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.*;
import com.epam.core.entity.enums.TourType;
import com.epam.core.entity.enums.UserRole;
import com.epam.core.repository.ReviewRepository;
import com.epam.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
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

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev", "oracle");
        context.register(PersistenceConfig.class, ReviewRepository.class, ReviewService.class);
        context.refresh();
        ReviewService reviewService = context.getBean(ReviewService.class);
        Review review = new Review();
        review.setId(1L);
        review.setReviewText("It was awesome!");
        review.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        user.setRole(UserRole.USER);
        review.addUser(user);
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resource/oracle.properties");
        tour.setStartDate(Timestamp.valueOf("2019-10-11 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2019-15-11 00:00:00"));
        tour.setCost(BigDecimal.valueOf(123).setScale(4, RoundingMode.HALF_UP));
        tour.setDescription("just tour");
        tour.setTourType(TourType.RURAL_TOURISM);
        reviewService.save(review);
        Review foundReview = reviewService.findByReviewDate(review.getReviewDate()).stream().findAny().orElse(null);
        System.out.println("Review : " + foundReview);

    }
}
