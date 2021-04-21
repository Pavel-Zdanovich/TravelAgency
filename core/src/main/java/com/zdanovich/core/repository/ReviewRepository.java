package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.TourType;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CommonRepository<Long, Review> {

    Optional<Review> findByUserAndTour(User user, Tour tour);

    Optional<Review> findByUser_IdAndTour_Id(Long userId, Long tourId);

    List<Review> findByReviewDate(Timestamp reviewDate);

    List<Review> findByReviewDateAfter(Timestamp reviewDate);

    List<Review> findByReviewDateBefore(Timestamp reviewDate);

    List<Review> findByReviewDateBetween(Timestamp reviewDate1, Timestamp reviewDate2);

    List<Review> findByReviewText(String description);

    List<Review> findByReviewTextContaining(String reviewText);

    List<Review> findByReviewTextLike(String regexp);

    List<Review> findByUser(User user);

    List<Review> findByUser_Id(Long userId);

    List<Review> findByUser_Login(String userLogin);

    List<Review> findByTour(Tour tour);

    List<Review> findByTour_Id(Long tourId);

    List<Review> findByTour_TourType(TourType tourType);

    List<Review> findByTour_Hotel(Hotel hotel);

    List<Review> findByTour_Country(Country country);

}
