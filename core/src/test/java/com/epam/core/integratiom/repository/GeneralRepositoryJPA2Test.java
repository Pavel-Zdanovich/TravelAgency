package com.epam.core.integratiom.repository;

import com.epam.core.entity.*;
import com.epam.core.entity.metamodel.*;
import com.epam.core.integratiom.embedded.EmbeddedPostgresConfig;
import com.epam.core.integratiom.embedded.EntityManagerConfig;
import com.epam.core.integratiom.embedded.FlywayConfig;
import com.epam.core.repository.GeneralRepository;
import com.epam.core.repository.GeneralRepositoryJPA2;
import com.epam.core.util.GeneralCriterion;
import com.epam.core.util.RelationType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import static com.epam.core.entity.Feature.*;
import static com.epam.core.entity.TourType.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityManagerConfig.class, GeneralRepositoryJPA2.class})
@ActiveProfiles(profiles = {"test", "test_dataSource"})
@Slf4j
public class GeneralRepositoryJPA2Test {

    @Autowired
    private GeneralRepository generalRepositoryJPA2;
    private Iterable<Country> foundCountries;
    private Iterable<Hotel> foundHotels;
    private Iterable<Review> foundReviews;
    private Iterable<Tour> foundTours;
    private Iterable<User> foundUsers;

    @Test
    public void save_Country() {
        Country country = new Country();
        country.setName("TestCountryName");
        generalRepositoryJPA2.save(country);
    }

    @Test
    public void save_Hotel() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName");
        hotel.setWebsite("http://www.test-hotel.com");
        hotel.setStars(5);
        hotel.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        hotel.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, ROOM_SERVICE, RESTAURANT});
        generalRepositoryJPA2.save(hotel);
    }

    @Test
    public void save_Review() {
        Review review = new Review();
        review.setText("TestReviewText");
        review.setDate(Timestamp.valueOf("2019-12-29 00:00:00"));
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.LOGIN, RelationType.EQUAL, "ElonMusk");
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User foundUser = userIterator.hasNext() ? userIterator.next() : null;
        review.setUser(foundUser);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.DESCRIPTION, RelationType.EQUAL, "good tour");
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour foundTour = tourIterator.hasNext() ? tourIterator.next() : null;
        review.setTour(foundTour);
        generalRepositoryJPA2.save(review);
    }

    @Test
    public void save_Tour() {
        Tour tour = new Tour();
        tour.setPhotoPath("src/main/resources/application.properties");
        tour.setStartDate(Timestamp.valueOf("2019-12-29 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-01-02 00:00:00"));
        tour.setDescription("TestTourDescription");
        tour.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(SCIENTIFIC_EXPEDITION);
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.NAME, RelationType.EQUAL, "Marriott");
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel foundHotel = hotelIterator.hasNext() ? hotelIterator.next() : null;
        tour.setHotel(foundHotel);
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.NAME, RelationType.EQUAL, "Brazil");
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country foundCountry = countryIterator.hasNext() ? countryIterator.next() : null;
        tour.setCountry(foundCountry);
        generalRepositoryJPA2.save(tour);
    }

    @Test
    public void save_User() {
        User user = new User();
        user.setLogin("TestUserLogin");
        user.setPassword("TestUserPassword");
        generalRepositoryJPA2.save(user);
    }

    @Test
    public void saveAll_Countries() {
        Country country1 = new Country();
        country1.setName("TestCountryName1");
        Country country2 = new Country();
        country2.setName("TestCountryName2");
        Country country3 = new Country();
        country3.setName("TestCountryName3");
        generalRepositoryJPA2.saveAll(List.of(country1, country2, country3));
    }

    @Test
    public void saveAll_Hotels() {
        Hotel hotel1 = new Hotel();
        hotel1.setName("TestHotelName1");
        hotel1.setWebsite("http://www.test-hotel1.com");
        hotel1.setStars(1);
        hotel1.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        hotel1.setFeatures(new Feature[]{AIR_CONDITIONER, CABLE_TV, CAR_RENTAL});
        Hotel hotel2 = new Hotel();
        hotel2.setName("TestHotelName2");
        hotel2.setWebsite("http://www.test-hotel2.com");
        hotel2.setStars(2);
        hotel2.setCoordinate("SRID=4326;POINT(55.755814 37.617635 42.419420)".getBytes(Charset.defaultCharset()));
        hotel2.setFeatures(new Feature[]{MINI_BAR, PARKING, ROOM_SERVICE, RESTAURANT});
        Hotel hotel3 = new Hotel();
        hotel3.setName("TestHotelName3");
        hotel3.setWebsite("http://www.test-hotel3.com");
        hotel3.setStars(3);
        hotel3.setCoordinate("SRID=4326;POINT(42.419420 55.755814 37.617635)".getBytes(Charset.defaultCharset()));
        hotel3.setFeatures(new Feature[]{SPA, SWIMMING_POOL, WI_FI});
        generalRepositoryJPA2.saveAll(List.of(hotel1, hotel2, hotel3));
    }

    @Test
    public void saveAll_Reviews() {
        Review review1 = new Review();
        review1.setText("TestReviewText1");
        review1.setDate(Timestamp.valueOf("2019-12-29 00:00:00"));
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.LOGIN, RelationType.EQUAL, "StephenHawking");
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User foundUser1 = userIterator.hasNext() ? userIterator.next() : null;
        review1.setUser(foundUser1);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.DESCRIPTION, RelationType.EQUAL, "best sale");
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour foundTour1 = tourIterator.hasNext() ? tourIterator.next() : null;
        review1.setTour(foundTour1);
        Review review2 = new Review();
        review2.setText("TestReviewText1");
        review2.setDate(Timestamp.valueOf("2019-12-29 00:00:00"));
        userGeneralCriterion.remove(User_.LOGIN, RelationType.EQUAL);
        userGeneralCriterion.addCriterion(User_.LOGIN, RelationType.EQUAL, "CharlesDarwin");
        userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        userIterator = userIterable.iterator();
        User foundUser2 = userIterator.hasNext() ? userIterator.next() : null;
        review2.setUser(foundUser2);
        tourGeneralCriterion.remove(Tour_.DESCRIPTION, RelationType.EQUAL);
        tourGeneralCriterion.addCriterion(Tour_.DESCRIPTION, RelationType.EQUAL, "back to the future");
        tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        tourIterator = tourIterable.iterator();
        Tour foundTour2 = tourIterator.hasNext() ? tourIterator.next() : null;
        review2.setTour(foundTour2);
        generalRepositoryJPA2.saveAll(List.of(review1, review2));
    }

    @Test
    public void saveAll_Tours() {
        Tour tour1 = new Tour();
        tour1.setPhotoPath("src/main/resources/application.properties");
        tour1.setStartDate(Timestamp.valueOf("2019-12-29 00:00:00"));
        tour1.setEndDate(Timestamp.valueOf("2020-01-02 00:00:00"));
        tour1.setDescription("TestTourDescription1");
        tour1.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        tour1.setTourType(SCIENTIFIC_EXPEDITION);
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.NAME, RelationType.EQUAL, "Emirates Palace");
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel foundHotel1 = hotelIterator.hasNext() ? hotelIterator.next() : null;
        tour1.setHotel(foundHotel1);
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.NAME, RelationType.EQUAL, "Thailand");
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country foundCountry1 = countryIterator.hasNext() ? countryIterator.next() : null;
        tour1.setCountry(foundCountry1);
        Tour tour2 = new Tour();
        tour2.setPhotoPath("src/main/resources/application.xml");
        tour2.setStartDate(Timestamp.valueOf("2019-11-20 00:00:00"));
        tour2.setEndDate(Timestamp.valueOf("2020-11-22 00:00:00"));
        tour2.setDescription("TestTourDescription1");
        tour2.setCost(BigDecimal.valueOf(200).setScale(4, RoundingMode.HALF_UP));
        tour2.setTourType(BUSINESS);
        hotelGeneralCriterion.remove(Hotel_.NAME, RelationType.EQUAL);
        hotelGeneralCriterion.addCriterion(Hotel_.NAME, RelationType.EQUAL, "Marriott");
        hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        hotelIterator = hotelIterable.iterator();
        Hotel foundHotel2 = hotelIterator.hasNext() ? hotelIterator.next() : null;
        tour2.setHotel(foundHotel2);
        countryGeneralCriterion.remove(Country_.NAME, RelationType.EQUAL);
        countryGeneralCriterion.addCriterion(Country_.NAME, RelationType.EQUAL, "Mexico");
        countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        countryIterator = countryIterable.iterator();
        Country foundCountry2 = countryIterator.hasNext() ? countryIterator.next() : null;
        tour2.setCountry(foundCountry2);
        generalRepositoryJPA2.saveAll(List.of(tour1, tour2));
    }

    @Test
    public void saveAll_Users() {
        User user1 = new User();
        user1.setLogin("TestUserLogin1");
        user1.setPassword("TestUserPassword1");
        User user2 = new User();
        user2.setLogin("TestUserLogin2");
        user2.setPassword("TestUserPassword2");
        User user3 = new User();
        user3.setLogin("TestUserLogin3");
        user3.setPassword("TestUserPassword3");
        generalRepositoryJPA2.saveAll(List.of(user1, user2, user3));
    }

    @Test
    public void find_Country_By_CountryId() {
        Country expected = new Country();
        expected.setCountryId(1);
        expected.setName("France");
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.COUNTRY_ID, RelationType.EQUAL, 1);
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country actual = countryIterator.hasNext() ? countryIterator.next() : null;
        Assert.assertEquals(expected.getCountryId(), actual.getCountryId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void find_Country_By_Name() {
        Country expected = new Country();
        expected.setCountryId(1);
        expected.setName("France");
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.NAME, RelationType.EQUAL, "France");
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country actual = countryIterator.hasNext() ? countryIterator.next() : null;
        Assert.assertEquals(expected.getCountryId(), actual.getCountryId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void find_Hotel_By_HotelId() {
        Hotel expected = new Hotel();
        expected.setHotelId(1);
        expected.setName("Marriott");
        expected.setWebsite("http://www.marriott.com");
        expected.setStars(5);
        expected.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        expected.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING});
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.HOTEL_ID, RelationType.EQUAL, 1);
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel actual = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertEquals(expected.getHotelId(), actual.getHotelId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
        Assert.assertEquals(expected.getStars(), actual.getStars());
        Assert.assertEquals(new String(expected.getCoordinate()), new String(actual.getCoordinate()));
        Assert.assertEquals(expected.getFeatures()[0], actual.getFeatures()[0]);
    }

    @Test
    public void find_Hotel_By_Name() {
        Hotel expected = new Hotel();
        expected.setHotelId(1);
        expected.setName("Marriott");
        expected.setWebsite("http://www.marriott.com");
        expected.setStars(5);
        expected.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        expected.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING});
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.NAME, RelationType.EQUAL, "Marriott");
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel actual = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertEquals(expected.getHotelId(), actual.getHotelId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
        Assert.assertEquals(expected.getStars(), actual.getStars());
        Assert.assertEquals(new String(expected.getCoordinate()), new String(actual.getCoordinate()));
        Assert.assertEquals(expected.getFeatures()[0], actual.getFeatures()[0]);
    }

    @Test
    public void find_Hotel_By_Website() {
        Hotel expected = new Hotel();
        expected.setHotelId(1);
        expected.setName("Marriott");
        expected.setWebsite("http://www.marriott.com");
        expected.setStars(5);
        expected.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        expected.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING});
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.WEBSITE, RelationType.EQUAL, "http://www.marriott.com");
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel actual = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertEquals(expected.getHotelId(), actual.getHotelId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
        Assert.assertEquals(expected.getStars(), actual.getStars());
        Assert.assertEquals(new String(expected.getCoordinate()), new String(actual.getCoordinate()));
        Assert.assertEquals(expected.getFeatures()[0], actual.getFeatures()[0]);
    }

    @Test
    public void find_Hotel_By_Stars() {
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.STARS, RelationType.EQUAL, 4);
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        int numbersOfHotels = 0;
        while (hotelIterator.hasNext()) {
            hotelIterator.next();
            numbersOfHotels++;
        }
        Assert.assertNotEquals(numbersOfHotels, 0);
    }

    @Test
    public void find_Hotel_By_Coordinate() {
        Hotel expected = new Hotel();
        expected.setHotelId(1);
        expected.setName("Marriott");
        expected.setWebsite("http://www.marriott.com");
        expected.setStars(5);
        expected.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        expected.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING});
        byte[] coordinate = "SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset());
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.COORDINATE, RelationType.EQUAL, coordinate);
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel actual = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertEquals(expected.getHotelId(), actual.getHotelId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
        Assert.assertEquals(expected.getStars(), actual.getStars());
        Assert.assertEquals(new String(expected.getCoordinate()), new String(actual.getCoordinate()));
        Assert.assertEquals(expected.getFeatures()[0], actual.getFeatures()[0]);
    }

    @Test
    public void find_Hotel_By_Features() {
        Hotel expected = new Hotel();
        expected.setHotelId(1);
        expected.setName("Marriott");
        expected.setWebsite("http://www.marriott.com");
        expected.setStars(5);
        expected.setCoordinate("SRID=4326;POINT(37.617635 55.755814 42.419420)".getBytes(Charset.defaultCharset()));
        expected.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING});
        Feature[] features = new Feature[]{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING};
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.FEATURES, RelationType.EQUAL, features);
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel actual = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertEquals(expected.getHotelId(), actual.getHotelId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
        Assert.assertEquals(expected.getStars(), actual.getStars());
        Assert.assertEquals(new String(expected.getCoordinate()), new String(actual.getCoordinate()));
        Assert.assertEquals(expected.getFeatures()[0], actual.getFeatures()[0]);
    }

    @Test
    public void find_Review_By_ReviewId() {
        Review expected = new Review();
        expected.setReviewId(1);
        expected.setText("sounds good");
        expected.setDate(Timestamp.valueOf("2019-09-10 00:00:00"));
        GeneralCriterion<Review> reviewGeneralCriterion = new GeneralCriterion<>(Review_.REVIEW_ID, RelationType.EQUAL, 1);
        Iterable<Review> reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        Iterator<Review> reviewIterator = reviewIterable.iterator();
        Review actual = reviewIterator.hasNext() ? reviewIterator.next() : null;
        Assert.assertEquals(expected.getReviewId(), actual.getReviewId());
        Assert.assertEquals(expected.getText(), actual.getText());
        Assert.assertEquals(expected.getDate(), actual.getDate());
    }

    @Test
    public void find_Review_By_Text() {
        Review expected = new Review();
        expected.setReviewId(1);
        expected.setText("sounds good");
        expected.setDate(Timestamp.valueOf("2019-09-10 00:00:00"));
        GeneralCriterion<Review> reviewGeneralCriterion = new GeneralCriterion<>(Review_.TEXT, RelationType.EQUAL, "sounds good");
        Iterable<Review> reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        Iterator<Review> reviewIterator = reviewIterable.iterator();
        Review actual = reviewIterator.hasNext() ? reviewIterator.next() : null;
        Assert.assertEquals(expected.getReviewId(), actual.getReviewId());
        Assert.assertEquals(expected.getText(), actual.getText());
        Assert.assertEquals(expected.getDate(), actual.getDate());
    }

    @Test
    public void find_Review_By_Date() {
        Review expected = new Review();
        expected.setReviewId(1);
        expected.setText("sounds good");
        expected.setDate(Timestamp.valueOf("2019-09-10 00:00:00"));
        GeneralCriterion<Review> reviewGeneralCriterion = new GeneralCriterion<>(Review_.DATE, RelationType.EQUAL, Timestamp.valueOf("2019-09-10 00:00:00"));
        Iterable<Review> reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        Iterator<Review> reviewIterator = reviewIterable.iterator();
        Review actual = reviewIterator.hasNext() ? reviewIterator.next() : null;
        Assert.assertEquals(expected.getReviewId(), actual.getReviewId());
        Assert.assertEquals(expected.getText(), actual.getText());
        Assert.assertEquals(expected.getDate(), actual.getDate());
    }

    @Test
    public void find_Tour_By_TourId() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.TOUR_ID, RelationType.EQUAL, 1);
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_Tour_By_PhotoPath() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.PHOTO_PATH, RelationType.EQUAL, "src/main/resources/application.properties");
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_Tour_By_StartDate() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.START_DATE, RelationType.EQUAL, Timestamp.valueOf("2019-10-01 00:00:00"));
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_Tour_By_EndDate() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.END_DATE, RelationType.EQUAL, Timestamp.valueOf("2019-11-30 00:00:00"));
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_Tour_By_Description() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.DESCRIPTION, RelationType.EQUAL, "good tour");
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_Tour_By_Cost() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.COST, RelationType.EQUAL, BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_Tour_By_TourType() {
        Tour expected = new Tour();
        expected.setTourId(1);
        expected.setPhotoPath("src/main/resources/application.properties");
        expected.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        expected.setDescription("good tour");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(LEISURE);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.TOUR_TYPE, RelationType.EQUAL, LEISURE);
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour actual = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(expected.getTourId(), actual.getTourId());
        Assert.assertEquals(expected.getPhotoPath(), actual.getPhotoPath());
        Assert.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getTourType(), actual.getTourType());
    }

    @Test
    public void find_User_By_UserId() {
        User expected = new User();
        expected.setUserId(1);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.USER_ID, RelationType.EQUAL, 1);
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User actual = userIterator.hasNext() ? userIterator.next() : null;
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getLogin(), actual.getLogin());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    public void find_User_By_Login() {
        User expected = new User();
        expected.setUserId(1);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.LOGIN, RelationType.EQUAL, "ElonMusk");
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User actual = userIterator.hasNext() ? userIterator.next() : null;
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getLogin(), actual.getLogin());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    public void find_User_By_Password() {
        User expected = new User();
        expected.setUserId(1);
        expected.setLogin("ElonMusk");
        expected.setPassword("SpaceXXX");
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.PASSWORD, RelationType.EQUAL, "SpaceXXX");
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User actual = userIterator.hasNext() ? userIterator.next() : null;
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getLogin(), actual.getLogin());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    public void findAll_Countries() {
        foundCountries = generalRepositoryJPA2.findAll(Country.class);
        Assert.assertNotNull(foundCountries);
    }

    @Test
    public void findAll_Hotels() {
        foundHotels = generalRepositoryJPA2.findAll(Hotel.class);
        Assert.assertNotNull(foundHotels);
    }

    @Test
    public void findAll_Reviews() {
        foundReviews = generalRepositoryJPA2.findAll(Review.class);
        Assert.assertNotNull(foundReviews);
    }

    @Test
    public void findAll_Tours() {
        foundTours = generalRepositoryJPA2.findAll(Tour.class);
        Assert.assertNotNull(foundTours);
    }

    @Test
    public void findAll_Users() {
        foundUsers = generalRepositoryJPA2.findAll(User.class);
        Assert.assertNotNull(foundUsers);
    }

    @Test
    public void update_Country() {
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.COUNTRY_ID, RelationType.EQUAL, 19);
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country previous = countryIterator.hasNext() ? countryIterator.next() : null;

        Country country = new Country();
        country.setCountryId(19);
        country.setName("Iraq");
        generalRepositoryJPA2.update(country);

        countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        countryIterator = countryIterable.iterator();
        Country current = countryIterator.hasNext() ? countryIterator.next() : null;
        Assert.assertEquals(country.getName(), current.getName());
        Assert.assertNotEquals(previous.getName(), current.getName());
    }

    @Test
    public void update_Hotel() {
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.HOTEL_ID, RelationType.EQUAL, 6);
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel previous = hotelIterator.hasNext() ? hotelIterator.next() : null;

        Hotel hotel = new Hotel();
        hotel.setHotelId(6);
        hotel.setName("The Trash Can");
        hotel.setWebsite("http://www.thetrashcan.com");
        hotel.setStars(5);
        hotel.setCoordinate("SRID=4326;POINT(10.574832 19.234718 12.238345)".getBytes(Charset.defaultCharset()));
        hotel.setFeatures(new Feature[]{WI_FI, MINI_BAR, RESTAURANT});
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.TOUR_ID, RelationType.EQUAL, 6);
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour foundTour = tourIterator.hasNext() ? tourIterator.next() : null;
        hotel.setTours(List.of(foundTour));
        generalRepositoryJPA2.update(hotel);

        hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        hotelIterator = hotelIterable.iterator();
        Hotel current = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertEquals(hotel.getName(), current.getName());
        Assert.assertEquals(hotel.getWebsite(), current.getWebsite());
        Assert.assertEquals(hotel.getStars(), current.getStars());
        Assert.assertNotEquals(previous.getName(), current.getName());
        Assert.assertNotEquals(previous.getWebsite(), current.getWebsite());
        Assert.assertNotEquals(previous.getStars(), current.getStars());
    }

    @Test
    public void update_Review() {
        GeneralCriterion<Review> reviewGeneralCriterion = new GeneralCriterion<>(Review_.REVIEW_ID, RelationType.EQUAL, 6);
        Iterable<Review> reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        Iterator<Review> reviewIterator = reviewIterable.iterator();
        Review previous = reviewIterator.hasNext() ? reviewIterator.next() : null;

        Review review = new Review();
        review.setReviewId(6);
        review.setText("death better 100%");
        review.setDate(Timestamp.valueOf("2019-10-10 00:00:00"));
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.USER_ID, RelationType.EQUAL, 6);
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User foundUser = userIterator.hasNext() ? userIterator.next() : null;
        review.setUser(foundUser);
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.TOUR_ID, RelationType.EQUAL, 6);
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour foundTour = tourIterator.hasNext() ? tourIterator.next() : null;
        review.setTour(foundTour);
        generalRepositoryJPA2.update(review);

        reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        reviewIterator = reviewIterable.iterator();
        Review current = reviewIterator.hasNext() ? reviewIterator.next() : null;
        Assert.assertEquals(review.getText(), current.getText());
        Assert.assertEquals(review.getDate(), current.getDate());
        Assert.assertNotEquals(previous.getText(), current.getText());
        Assert.assertEquals(previous.getDate(), current.getDate());
    }

    @Test
    public void update_Tour() {
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.TOUR_ID, RelationType.EQUAL, 2);
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour previous = tourIterator.hasNext() ? tourIterator.next() : null;

        Tour tour = new Tour();
        tour.setTourId(2);
        tour.setPhotoPath("src/main/resources/application.bundle");
        tour.setStartDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2019-11-30 00:00:00"));
        tour.setDescription("the best sale");
        tour.setCost(BigDecimal.valueOf(200).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(LEISURE);
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.USER_ID, RelationType.EQUAL, 2);
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User foundUser = userIterator.hasNext() ? userIterator.next() : null;
        foundUser.setTours(List.of(tour));
        tour.setUsers(List.of(foundUser));
        generalRepositoryJPA2.update(tour);

        tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        tourIterator = tourIterable.iterator();
        Tour current = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertEquals(tour.getPhotoPath(), current.getPhotoPath());
        Assert.assertEquals(tour.getDescription(), current.getDescription());
        Assert.assertEquals(tour.getCost(), current.getCost());
        Assert.assertNotEquals(previous.getPhotoPath(), current.getPhotoPath());
        Assert.assertNotEquals(previous.getDescription(), current.getDescription());
        Assert.assertEquals(previous.getCost(), current.getCost());
    }

    @Test
    public void update_User() {
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.USER_ID, RelationType.EQUAL, 2);
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User previous = userIterator.hasNext() ? userIterator.next() : null;

        User user = new User();
        user.setUserId(2);
        user.setLogin("StephenHawking");
        user.setPassword("BlackHole");
        generalRepositoryJPA2.update(user);

        userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        userIterator = userIterable.iterator();
        User current = userIterator.hasNext() ? userIterator.next() : null;
        Assert.assertEquals(user.getLogin(), current.getLogin());
        Assert.assertEquals(user.getPassword(), current.getPassword());
        Assert.assertNotEquals(previous.getPassword(), current.getPassword());
        Assert.assertEquals(previous.getLogin(), current.getLogin());
    }

    @Test
    public void update_Country_By_CountryId() {
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.COUNTRY_ID, RelationType.EQUAL, 2);
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country previous = countryIterator.hasNext() ? countryIterator.next() : null;

        Country country = new Country();
        country.setCountryId(2);
        country.setName("Spain");
        generalRepositoryJPA2.update(country);

        countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        countryIterator = countryIterable.iterator();
        Country current = countryIterator.hasNext() ? countryIterator.next() : null;
        Assert.assertEquals(country.getName(), current.getName());
        Assert.assertNotEquals(previous.getName(), current.getName());
    }

    @Test
    public void updateAll() {

    }

    @Test
    public void delete_Country() {
        GeneralCriterion<Country> countryGeneralCriterion = new GeneralCriterion<>(Country_.COUNTRY_ID, RelationType.EQUAL, 3);
        Iterable<Country> countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        Iterator<Country> countryIterator = countryIterable.iterator();
        Country foundCountry = countryIterator.hasNext() ? countryIterator.next() : null;

        Country country = new Country();
        country.setCountryId(3);
        country.setName("USA");
        Assert.assertEquals(country.getCountryId(), foundCountry.getCountryId());
        Assert.assertEquals(country.getName(), foundCountry.getName());
        generalRepositoryJPA2.delete(country);

        countryIterable = generalRepositoryJPA2.findBy(Country.class, countryGeneralCriterion);
        countryIterator = countryIterable.iterator();
        Country deletedCountry = countryIterator.hasNext() ? countryIterator.next() : null;
        Assert.assertNull(deletedCountry);
    }

    @Test
    public void delete_Hotel() {
        GeneralCriterion<Hotel> hotelGeneralCriterion = new GeneralCriterion<>(Hotel_.HOTEL_ID, RelationType.EQUAL, 3);
        Iterable<Hotel> hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        Iterator<Hotel> hotelIterator = hotelIterable.iterator();
        Hotel foundHotel = hotelIterator.hasNext() ? hotelIterator.next() : null;

        Hotel hotel = new Hotel();
        hotel.setHotelId(3);
        hotel.setName("Grand Budapest");
        hotel.setWebsite("http://www.grand.com");
        hotel.setStars(3);
        hotel.setCoordinate("SRID=4326;POINT(24.832485 12.894357 67.934827)".getBytes(Charset.defaultCharset()));
        hotel.setFeatures(new Feature[]{AIR_CONDITIONER, WI_FI, RESTAURANT});
        Assert.assertEquals(hotel.getHotelId(), foundHotel.getHotelId());
        Assert.assertEquals(hotel.getName(), foundHotel.getName());
        Assert.assertEquals(hotel.getWebsite(), foundHotel.getWebsite());
        Assert.assertEquals(hotel.getStars(), foundHotel.getStars());
        Assert.assertEquals(new String(hotel.getCoordinate()), new String(foundHotel.getCoordinate()));
        Assert.assertEquals(hotel.getFeatures()[0], foundHotel.getFeatures()[0]);
        generalRepositoryJPA2.delete(hotel);

        hotelIterable = generalRepositoryJPA2.findBy(Hotel.class, hotelGeneralCriterion);
        hotelIterator = hotelIterable.iterator();
        Hotel deletedHotel = hotelIterator.hasNext() ? hotelIterator.next() : null;
        Assert.assertNull(deletedHotel);
    }

    @Test
    public void delete_Review() {
        GeneralCriterion<Review> reviewGeneralCriterion = new GeneralCriterion<>(Review_.REVIEW_ID, RelationType.EQUAL, 5);
        Iterable<Review> reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        Iterator<Review> reviewIterator = reviewIterable.iterator();
        Review foundReview = reviewIterator.hasNext() ? reviewIterator.next() : null;

        Review review = new Review();
        review.setReviewId(5);
        review.setText("It was relatively well");
        review.setDate(Timestamp.valueOf("2019-10-15 00:00:00"));
        Assert.assertEquals(review.getReviewId(), foundReview.getReviewId());
        Assert.assertEquals(review.getText(), foundReview.getText());
        Assert.assertEquals(review.getDate(), foundReview.getDate());
        generalRepositoryJPA2.delete(review);

        reviewIterable = generalRepositoryJPA2.findBy(Review.class, reviewGeneralCriterion);
        reviewIterator = reviewIterable.iterator();
        Review deletedReview = reviewIterator.hasNext() ? reviewIterator.next() : null;
        Assert.assertNull(deletedReview);
    }

    @Test
    public void delete_Tour() {
        GeneralCriterion<Tour> tourGeneralCriterion = new GeneralCriterion<>(Tour_.TOUR_ID, RelationType.EQUAL, 3);
        Iterable<Tour> tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        Iterator<Tour> tourIterator = tourIterable.iterator();
        Tour foundTour = tourIterator.hasNext() ? tourIterator.next() : null;

        Tour tour = new Tour();
        tour.setTourId(3);
        tour.setPhotoPath("src/main/resources/application.properties");
        tour.setStartDate(Timestamp.valueOf("2019-09-20 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2019-10-01 00:00:00"));
        tour.setDescription("two for one deal");
        tour.setCost(BigDecimal.valueOf(300).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(SPORT_COMPETITION);
        Assert.assertEquals(tour.getTourId(), foundTour.getTourId());
        Assert.assertEquals(tour.getPhotoPath(), foundTour.getPhotoPath());
        Assert.assertEquals(tour.getStartDate(), foundTour.getStartDate());
        Assert.assertEquals(tour.getEndDate(), foundTour.getEndDate());
        Assert.assertEquals(tour.getDescription(), foundTour.getDescription());
        Assert.assertEquals(tour.getTourType(), foundTour.getTourType());
        generalRepositoryJPA2.delete(tour);

        tourIterable = generalRepositoryJPA2.findBy(Tour.class, tourGeneralCriterion);
        tourIterator = tourIterable.iterator();
        Tour deletedTour = tourIterator.hasNext() ? tourIterator.next() : null;
        Assert.assertNull(deletedTour);
    }

    @Test
    public void delete_User() {
        GeneralCriterion<User> userGeneralCriterion = new GeneralCriterion<>(User_.USER_ID, RelationType.EQUAL, 4);
        Iterable<User> userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        Iterator<User> userIterator = userIterable.iterator();
        User foundUser = userIterator.hasNext() ? userIterator.next() : null;

        User user = new User();
        user.setUserId(4);
        user.setLogin("CharlesDarwin");
        user.setPassword("Evolution");
        Assert.assertEquals(user.getUserId(), foundUser.getUserId());
        Assert.assertEquals(user.getLogin(), foundUser.getLogin());
        Assert.assertEquals(user.getPassword(), foundUser.getPassword());
        generalRepositoryJPA2.delete(user);

        userIterable = generalRepositoryJPA2.findBy(User.class, userGeneralCriterion);
        userIterator = userIterable.iterator();
        User deletedUser = userIterator.hasNext() ? userIterator.next() : null;
        Assert.assertNull(deletedUser);
    }

    @Test
    public void deleteBy() {

    }

    @Test
    public void deleteAll() {
    }
}