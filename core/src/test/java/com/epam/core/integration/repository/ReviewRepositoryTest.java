package com.epam.core.integration.repository;

import com.epam.core.entity.*;
import com.epam.core.entity.enums.TourType;
import com.epam.core.integration.config.EntityManagerConfig;
import com.epam.core.integration.config.MigrationConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import com.epam.core.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, MigrationConfig.class, EntityManagerConfig.class, ReviewRepository.class})
@ActiveProfiles(profiles = {"test", "postgresql"})
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void save() {
        Review expected = new Review();
        expected.setReviewText("TestReviewText");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        expected.addUser(user);
        expected.addTour(tour);
        Review actual = reviewRepository.findByUser_IdAndTour_Id(1L, 1L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_By_Iterable() {
    }

    @Test
    public void saveAndFlush() {
    }

    @Test
    public void flush() {
    }

    @Test
    public void findById() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUserAndTour() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        expected.addUser(user);
        expected.addTour(tour);
        Review actual = reviewRepository.findByUserAndTour(user, tour).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUser_UserIdAndTour_TourId() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        expected.addUser(user);
        expected.addTour(tour);
        Review actual = reviewRepository.findByUser_IdAndTour_Id(user.getId(), tour.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewDate() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewDate(expected.getReviewDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewDateAfter() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewDateAfter(expected.getReviewDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewDateBefore() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewDateBefore(expected.getReviewDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewDateBetween() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewDateBetween(expected.getReviewDate(), expected.getReviewDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewText() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewText(expected.getReviewText()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewTextContaining() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewTextContaining(expected.getReviewText().substring(0, 5)).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByReviewTextLike() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review actual = reviewRepository.findByReviewTextLike("%review%").stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUser() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        expected.addUser(user);
        Review actual = reviewRepository.findByUser(user).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUser_Id() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        expected.addUser(user);
        Review actual = reviewRepository.findByUser_Id(user.getId()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUser_Login() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        User user = new User();
        user.setId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        expected.addUser(user);
        Review actual = reviewRepository.findByUser_Login(user.getLogin()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTour() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        expected.addTour(tour);
        Review actual = reviewRepository.findByTour(tour).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTour_TourId() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        expected.addTour(tour);
        Review actual = reviewRepository.findByTour_Id(tour.getId()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTour_TourType() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        expected.addTour(tour);
        Review actual = reviewRepository.findByTour_TourType(tour.getTourType()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTour_Hotel() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Marriott");
        hotel.setWebsite("https://www.marriott.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(89).setScale(7, RoundingMode.HALF_UP));
        hotel.setLongitude(BigDecimal.valueOf(189).setScale(7, RoundingMode.HALF_UP));
        tour.addHotel(hotel);
        expected.addTour(tour);
        Review actual = reviewRepository.findByTour_Hotel(tour.getHotel()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTour_Country() {
        Review expected = new Review();
        expected.setId(1L);
        expected.setReviewText("review text");
        expected.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tour.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-10 00:00:00"));
        tour.setDescription("description");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        Country country = new Country();
        country.setId(1L);
        country.setName("France");
        tour.addCountry(country);
        expected.addTour(tour);
        Review actual = reviewRepository.findByTour_Country(tour.getCountry()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<Review> actual = reviewRepository.findAll();
        Assert.assertTrue(actual.size() > 0);
    }

    @Test
    public void findAllById() {
    }

    @Test
    public void findAll_With_Sort () {
    }

    @Test
    public void findAll_By_Example() {
    }

    @Test
    public void findAll_By_Example_With_Sort() {
    }

    @Test
    public void findAll_By_Pageable() {
    }

    @Test
    public void findAll_By_Example_And_Pageable() {
    }

    @Test
    public void existsById() {
        Assert.assertTrue(reviewRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
    }

    @Test
    public void count() {
        Assert.assertNotEquals(reviewRepository.count(), 0);
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update() {
        Review expected = reviewRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setReviewText("UpdatedReviewText");
        reviewRepository.save(expected);
        Review actual = reviewRepository.findById(2L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Review foundReview = reviewRepository.findById(3L).orElse(null);
        Assert.assertNotNull(foundReview);
        reviewRepository.delete(foundReview);
        Review deletedReview = reviewRepository.findById(3L).orElse(null);
        Assert.assertNull(deletedReview);
    }

    @Test
    public void deleteById() {
        Review foundReview = reviewRepository.findById(4L).orElse(null);
        Assert.assertNotNull(foundReview);
        reviewRepository.deleteById(4L);
        Review deletedReview = reviewRepository.findById(4L).orElse(null);
        Assert.assertNull(deletedReview);
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void deleteAll_By_Iterable() {
        long numberOfReviewsBeforeDeletion = reviewRepository.count();
        Review review1 = new Review();
        review1.setId(5L);
        review1.setReviewText("review text");
        review1.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Review review2 = new Review();
        review2.setId(6L);
        review2.setReviewText("review text");
        review2.setReviewDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        long numberOfReviewsAfterDeletion = reviewRepository.count();
        Assert.assertEquals(numberOfReviewsBeforeDeletion - numberOfReviewsAfterDeletion, 2);
    }

    @Test
    public void deleteAllInBatch() {
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }

}
