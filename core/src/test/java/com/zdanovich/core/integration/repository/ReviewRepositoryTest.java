package com.zdanovich.core.integration.repository;

import com.zdanovich.core.repository.ReviewRepository;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.entity.enums.UserRole;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryTest extends AbstractRepositoryTest {

    @Test
    public void save_Without_User_And_Tour() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText1");
        expected.setReviewDate(Timestamp.valueOf("2021-01-01 00:00:00"));

        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(expected);
        Assert.assertNotNull(savedReview);
        expected.setId(savedReview.getId());
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void save_With_Detached_User_And_Tour() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText2");
        expected.setReviewDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        User elonMusk = new User();
        elonMusk.setId(1L);
        elonMusk.setLogin("ElonMusk");
        elonMusk.setPassword("SpaceXXX");
        elonMusk.setRole(UserRole.USER);
        Tour firstTour = new Tour();
        firstTour.setId(1L);
        firstTour.setPhotoPath("src/main/resources/log4j2.xml");
        firstTour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        firstTour.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        firstTour.setDescription("good TOUR");
        firstTour.setCost(BigDecimal.valueOf(100.0000));
        firstTour.setTourType(TourType.BUSINESS);
        firstTour.addUser(elonMusk);
        expected.setUser(elonMusk);
        expected.setTour(firstTour);

        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(expected);
        Assert.assertNotNull(savedReview);
        expected.setId(savedReview.getId());
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_Attached_User_And_Tour() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText3");
        expected.setReviewDate(Timestamp.valueOf("2021-01-03 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        expected.setUser(elonMusk);
        expected.setTour(firstTour);

        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(expected);
        Assert.assertNotNull(savedReview);
        expected.setId(savedReview.getId());
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_New_User_And_Tour() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText4");
        expected.setReviewDate(Timestamp.valueOf("2021-01-04 00:00:00"));
        User elonMusk = new User();
        elonMusk.setLogin("PavelZdanovich");
        elonMusk.setPassword("Admin009");
        elonMusk.setRole(UserRole.USER);
        Tour firstTour = new Tour();
        firstTour.setPhotoPath("src/main/resources/log4j2.xml");
        firstTour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        firstTour.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        firstTour.setDescription("TestTourDescription");
        firstTour.setCost(BigDecimal.valueOf(100.0000));
        firstTour.setTourType(TourType.BUSINESS);
        firstTour.addUser(elonMusk);

        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(expected);
        Assert.assertNotNull(savedReview);
        expected.setId(savedReview.getId());
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_By_Iterable() {
        Review expected1 = new Review();
        expected1.setReviewText("TestReviewText5");
        expected1.setReviewDate(Timestamp.valueOf("2021-01-05 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        expected1.setUser(elonMusk);
        expected1.setTour(firstTour);
        Review expected2 = new Review();
        expected2.setReviewText("TestReviewText6");
        expected2.setReviewDate(Timestamp.valueOf("2021-01-06 00:00:00"));
        expected2.setUser(elonMusk);
        expected2.setTour(firstTour);
        List<Review> reviews = new ArrayList<>();
        reviews.add(expected1);
        reviews.add(expected2);

        Review actual1 = reviewRepository.findByReviewText(expected1.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual1);
        Review actual2 = reviewRepository.findByReviewText(expected2.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual2);

        List<Review> savedReviews = reviewRepository.saveAll(reviews);
        Assert.assertNotNull(savedReviews);
        Assert.assertEquals(reviews.size(), savedReviews.size());
        actual1 = savedReviews.get(0);
        Assert.assertNotNull(actual1);
        expected1.setId(actual1.getId());
        Assert.assertEquals(expected1, actual1);
        actual2 = savedReviews.get(1);
        Assert.assertNotNull(actual2);
        expected2.setId(actual2.getId());
        Assert.assertEquals(expected2, actual2);

        actual1 = reviewRepository.findById(expected1.getId()).orElse(null);
        Assert.assertEquals(expected1, actual1);
        actual2 = reviewRepository.findById(expected2.getId()).orElse(null);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void saveAndFlush() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText7");
        expected.setReviewDate(Timestamp.valueOf("2021-01-07 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        expected.setUser(elonMusk);
        expected.setTour(firstTour);

        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.saveAndFlush(expected);
        Assert.assertNotNull(savedReview);
        expected.setId(savedReview.getId());
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
        actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flush() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText8");
        expected.setReviewDate(Timestamp.valueOf("2021-01-08 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        expected.setUser(elonMusk);
        expected.setTour(firstTour);

        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(expected);
        Assert.assertNotNull(savedReview);
        expected.setId(savedReview.getId());
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);

        /**{@link CrudRepository#findById(Object)} synchronized with cache, but
         * {@link ReviewRepository#findByReviewText(String)} not synchronized and required flush
         */

        actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        reviewRepository.flush();

        actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findAny().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findById() {
        Review actual = reviewRepository.findById(elonFirstTourReview.getId()).orElse(null);
        Assert.assertEquals(elonFirstTourReview, actual);
    }

    @Test
    public void findByUserAndTour() {
        Review actual = reviewRepository.findByUserAndTour(elonMusk, firstTour).orElse(null);
        Assert.assertEquals(elonFirstTourReview, actual);
    }

    @Test
    public void findByUser_UserIdAndTour_TourId() {
        Review actual = reviewRepository.findByUser_IdAndTour_Id(elonMusk.getId(), firstTour.getId()).orElse(null);
        Assert.assertEquals(elonFirstTourReview, actual);
    }

    @Test
    public void findByReviewDate() {
        List<Review> actual = reviewRepository.findByReviewDate(elonFirstTourReview.getReviewDate());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewDate().equals(elonFirstTourReview.getReviewDate())));
    }

    @Test
    public void findByReviewDateAfter() {
        List<Review> actual = reviewRepository.findByReviewDateAfter(elonFirstTourReview.getReviewDate());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewDate().after(elonFirstTourReview.getReviewDate())));
    }

    @Test
    public void findByReviewDateBefore() {
        List<Review> actual = reviewRepository.findByReviewDateBefore(elonFirstTourReview.getReviewDate());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewDate().before(elonFirstTourReview.getReviewDate())));
    }

    @Test
    public void findByReviewDateBetween() {
        Timestamp start = Timestamp.valueOf("2021-01-01 00:00:00");
        Timestamp end = Timestamp.valueOf("2021-02-01 00:00:00");
        List<Review> actual = reviewRepository.findByReviewDateBetween(start, end);
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewDate().after(start) && review.getReviewDate().before(end)));
    }

    @Test
    public void findByReviewText() {
        List<Review> actual = reviewRepository.findByReviewText(elonFirstTourReview.getReviewText());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewText().equals(elonFirstTourReview.getReviewText())));
    }

    @Test
    public void findByReviewTextContaining() {
        List<Review> actual = reviewRepository.findByReviewTextContaining(elonFirstTourReview.getReviewText());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewText().contains(elonFirstTourReview.getReviewText())));
    }

    @Test
    public void findByReviewTextLike() {
        String regex = "";
        List<Review> actual = reviewRepository.findByReviewTextLike(regex);
        Assert.assertTrue(actual.stream().allMatch(review -> review.getReviewText().matches(regex)));
    }

    @Test
    public void findByUser() {
        List<Review> actual = reviewRepository.findByUser(elonFirstTourReview.getUser());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getUser().equals(elonFirstTourReview.getUser())));
    }

    @Test
    public void findByUser_Id() {
        List<Review> actual = reviewRepository.findByUser_Id(elonFirstTourReview.getUser().getId());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getUser().equals(elonFirstTourReview.getUser())));

    }

    @Test
    public void findByUser_Login() {
        List<Review> actual = reviewRepository.findByUser_Login(elonFirstTourReview.getUser().getLogin());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getUser().equals(elonFirstTourReview.getUser())));

    }

    @Test
    public void findByTour() {
        List<Review> actual = reviewRepository.findByTour(elonFirstTourReview.getTour());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getTour().equals(elonFirstTourReview.getTour())));
    }

    @Test
    public void findByTour_TourId() {
        List<Review> actual = reviewRepository.findByTour_Id(elonFirstTourReview.getTour().getId());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getTour().equals(elonFirstTourReview.getTour())));
    }

    @Ignore
    @Test
    public void findByTour_TourType() {
        List<Review> actual = reviewRepository.findByTour_TourType(elonFirstTourReview.getTour().getTourType());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getTour().equals(elonFirstTourReview.getTour())));
    }

    @Test
    public void findByTour_Hotel() {
        List<Review> actual = reviewRepository.findByTour_Hotel(elonFirstTourReview.getTour().getHotel());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getTour().equals(elonFirstTourReview.getTour())));
    }

    @Ignore
    @Test
    public void findByTour_Country() {
        List<Review> actual = reviewRepository.findByTour_Country(elonFirstTourReview.getTour().getCountry());
        Assert.assertTrue(actual.stream().allMatch(review -> review.getTour().equals(elonFirstTourReview.getTour())));
    }

    @Test
    public void findOne_ById() {
        Review example = new Review();
        example.setId(elonFirstTourReview.getId());
        Review actual = reviewRepository.findOne(Example.of(example)).orElse(null);
        Assert.assertEquals(elonFirstTourReview, actual);
    }

    @Test
    public void findOne_ByReviewDate() {
        Review example = new Review();
        example.setReviewDate(elonFirstTourReview.getReviewDate());
        Review actual = reviewRepository.findOne(Example.of(example)).orElse(null);
        Assert.assertEquals(elonFirstTourReview, actual);
    }

    @Test
    public void findOne_ByReviewText() {
        Review example = new Review();
        example.setReviewText(elonFirstTourReview.getReviewText());
        Review actual = reviewRepository.findOne(Example.of(example)).orElse(null);
        Assert.assertEquals(elonFirstTourReview, actual);
    }

    @Test
    public void findAll() {
        List<Review> actual = reviewRepository.findAll();
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findAllById() {
        List<Long> reviewIds = new ArrayList<>();
        reviewIds.add(elonFirstTourReview.getId());
        reviewIds.add(2L);
        List<Review> actual = reviewRepository.findAllById(reviewIds);
        Assert.assertNotNull(actual);
        reviewIds.forEach(reviewId -> Assert.assertTrue(actual.stream().anyMatch(review -> review.getId().equals(reviewId))));
    }

    @Test
    public void findAll_With_Sort_ById () {

    }

    @Test
    public void findAll_With_Sort_ByReviewDate () {

    }

    @Test
    public void findAll_With_Sort_ByReviewText() {

    }

    @Test
    public void findAll_By_Example_Id() {
    }

    @Test
    public void findAll_By_Example_ReviewDate() {
    }

    @Test
    public void findAll_By_Example_ReviewText() {
    }

    @Test
    public void findAll_By_Example_With_Sort() {
    }

    @Test
    public void findAll_With_Pagination() {
    }

    @Test
    public void findAll_By_Example_With_Pagination() {
    }

    @Test
    public void existsById() {
        Assert.assertTrue(reviewRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
        Review example = new Review();
        example.setReviewText(elonFirstTourReview.getReviewText());
        Assert.assertTrue(reviewRepository.exists(Example.of(example)));
    }

    @Test
    public void count() {
        Assert.assertNotEquals(0, reviewRepository.count());
    }

    @Test
    public void count_By_Example() {
        Review example = new Review();
        example.setReviewText(elonFirstTourReview.getReviewText());
        Assert.assertEquals(1, reviewRepository.count(Example.of(example)));
    }

    @Test
    public void update_Without_User_And_Tour() {

    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void update_With_Detached_User_And_Tour() {
        Review review = new Review();
        review.setReviewText("TestReviewText9");
        review.setReviewDate(Timestamp.valueOf("2021-01-09 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setRole(UserRole.USER);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        review.setUser(user);
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        tour.setDescription("good TOUR");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        review.setTour(tour);

        Review actual = reviewRepository.findByReviewText(review.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(review);
        Assert.assertNotNull(savedReview);
        review.setId(savedReview.getId());

        Review expected = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(review, expected);

        expected.setReviewText("TestReviewText10");
        expected.setReviewDate(Timestamp.valueOf("2021-01-10 00:00:00"));
        savedReview = reviewRepository.save(review);
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update_With_Attached_User_And_Tour() {
        Review review = new Review();
        review.setReviewText("TestReviewText9");
        review.setReviewDate(Timestamp.valueOf("2021-01-09 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        review.setUser(elonMusk);
        review.setTour(firstTour);

        Review actual = reviewRepository.findByReviewText(review.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(review);
        Assert.assertNotNull(savedReview);
        review.setId(savedReview.getId());

        Review expected = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(review, expected);

        expected.setReviewText("TestReviewText10");
        expected.setReviewDate(Timestamp.valueOf("2021-01-10 00:00:00"));
        savedReview = reviewRepository.save(review);
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update_With_New_User_And_Tour() {
        Review review = new Review();
        review.setReviewText("TestReviewText9");
        review.setReviewDate(Timestamp.valueOf("2021-01-09 00:00:00"));
        User user = new User();
        user.setRole(UserRole.USER);
        user.setLogin("TestUser");
        user.setPassword("TestPassword");
        review.setUser(user);
        Tour tour = new Tour();
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        review.setTour(tour);

        Review actual = reviewRepository.findByReviewText(review.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Review savedReview = reviewRepository.save(review);
        Assert.assertNotNull(savedReview);
        review.setId(savedReview.getId());

        Review expected = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(review, expected);

        expected.setReviewText("TestReviewText10");
        expected.setReviewDate(Timestamp.valueOf("2021-01-10 00:00:00"));
        savedReview = reviewRepository.save(review);
        Assert.assertEquals(expected, savedReview);

        actual = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void delete() {
        Review review = new Review();
        review.setReviewText("TestReviewText11");
        review.setReviewDate(Timestamp.valueOf("2021-01-11 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        review.setUser(elonMusk);
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        review.setTour(firstTour);

        Review foundReview = reviewRepository.findByReviewText(review.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(foundReview);

        Review savedReview = reviewRepository.save(review);
        Assert.assertNotNull(savedReview);
        review.setId(savedReview.getId());

        foundReview = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNotNull(foundReview);
        Assert.assertEquals(review, foundReview);

        reviewRepository.delete(review);

        Review deletedReview = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNull(deletedReview);
    }

    @Test
    public void deleteById() {
        Review review = new Review();
        review.setReviewText("TestReviewText12");
        review.setReviewDate(Timestamp.valueOf("2021-01-12 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        review.setUser(elonMusk);
        review.setTour(firstTour);

        Review foundReview = reviewRepository.findByReviewText(review.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(foundReview);

        Review savedReview = reviewRepository.save(review);
        Assert.assertNotNull(savedReview);
        review.setId(savedReview.getId());

        foundReview = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNotNull(foundReview);
        Assert.assertEquals(review, foundReview);

        reviewRepository.deleteById(review.getId());

        Review deletedReview = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertNull(deletedReview);
    }

    @Test
    public void deleteAll() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteAll_By_Iterable() {
        Review review1 = new Review();
        review1.setReviewText("TestReviewText13");
        review1.setReviewDate(Timestamp.valueOf("2021-01-13 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        review1.setUser(elonMusk);
        review1.setTour(firstTour);
        Review review2 = new Review();
        review2.setReviewText("TestReviewText14");
        review2.setReviewDate(Timestamp.valueOf("2021-01-14 00:00:00"));
        review2.setUser(elonMusk);
        review2.setTour(firstTour);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        Review foundReview = reviewRepository.findByReviewText(review1.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(foundReview);
        foundReview = reviewRepository.findByReviewText(review2.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(foundReview);

        Review savedReview = reviewRepository.save(review1);
        Assert.assertNotNull(savedReview);
        review1.setId(savedReview.getId());
        savedReview = reviewRepository.save(review2);
        Assert.assertNotNull(savedReview);
        review2.setId(savedReview.getId());

        foundReview = reviewRepository.findById(review1.getId()).orElse(null);
        Assert.assertNotNull(foundReview);
        Assert.assertEquals(review1, foundReview);
        foundReview = reviewRepository.findById(review2.getId()).orElse(null);
        Assert.assertNotNull(foundReview);
        Assert.assertEquals(review2, foundReview);

        reviewRepository.deleteAll(reviews);

        Review deletedReview = reviewRepository.findById(review1.getId()).orElse(null);
        Assert.assertNull(deletedReview);
        deletedReview = reviewRepository.findById(review2.getId()).orElse(null);
        Assert.assertNull(deletedReview);
    }

    @Test
    public void deleteAllInBatch() {
        //I hope I'll never use this method
    }

    @Ignore
    @Test
    public void deleteInBatch_By_Iterable() {
        Review review1 = new Review();
        review1.setReviewText("TestReviewText15");
        review1.setReviewDate(Timestamp.valueOf("2021-01-15 00:00:00"));
        User elonMusk = userRepository.findByLogin("ElonMusk").orElseThrow(() ->
                new EntityNotFoundException("Unable to find user 'ElonMusk'"));
        Tour firstTour = tourRepository.findById(1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find tour '1L'"));
        review1.setUser(elonMusk);
        review1.setTour(firstTour);
        Review review2 = new Review();
        review2.setReviewText("TestReviewText16");
        review2.setReviewDate(Timestamp.valueOf("2021-01-16 00:00:00"));
        review2.setUser(elonMusk);
        review2.setTour(firstTour);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        Review foundReview = reviewRepository.findByReviewText(review1.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(foundReview);
        foundReview = reviewRepository.findByReviewText(review2.getReviewText()).stream().findAny().orElse(null);
        Assert.assertNull(foundReview);

        Review savedReview = reviewRepository.save(review1);
        Assert.assertNotNull(savedReview);
        review1.setId(savedReview.getId());
        savedReview = reviewRepository.save(review2);
        Assert.assertNotNull(savedReview);
        review2.setId(savedReview.getId());

        foundReview = reviewRepository.findById(review1.getId()).orElse(null);
        Assert.assertNotNull(foundReview);
        Assert.assertEquals(review1, foundReview);
        foundReview = reviewRepository.findById(review2.getId()).orElse(null);
        Assert.assertNotNull(foundReview);
        Assert.assertEquals(review2, foundReview);

        reviewRepository.deleteInBatch(reviews);

        Review deletedReview = reviewRepository.findById(review1.getId()).orElse(null);
        Assert.assertNull(deletedReview);
        deletedReview = reviewRepository.findById(review2.getId()).orElse(null);
        Assert.assertNull(deletedReview);

        reviewRepository.flush();

        deletedReview = reviewRepository.findById(review1.getId()).orElse(null);
        Assert.assertNull(deletedReview);
        deletedReview = reviewRepository.findById(review2.getId()).orElse(null);
        Assert.assertNull(deletedReview);
    }
}
