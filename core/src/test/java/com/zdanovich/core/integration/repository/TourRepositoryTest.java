package com.zdanovich.core.integration.repository;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.entity.enums.UserRole;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

public class TourRepositoryTest extends AbstractRepositoryTest {

    @Test
    public void save_Without_Hotel_Country_And_User() {
        Tour expected = new Tour();
        expected.setPhotoPath("src/main/resources/oracle.properties");
        expected.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        expected.setDescription("TestTourDescription1");
        expected.setCost(BigDecimal.valueOf(100.0000));
        expected.setTourType(TourType.ECOTOURISM);
        //expected.addUser();
        //expected.setHotel();
        //expected.setCountry();

        Tour actual = tourRepository.findByDescription(expected.getDescription()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Tour savedTour = tourRepository.save(expected);
        Assert.assertNotNull(savedTour);
        expected.setId(savedTour.getId());
        Assert.assertEquals(expected, savedTour);

        actual = tourRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void save_With_Detached_Hotel_Country_And_User() {
        Tour expected = new Tour();
        expected.setPhotoPath("src/main/resources/oracle.properties");
        expected.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        expected.setDescription("TestTourDescription2");
        expected.setCost(BigDecimal.valueOf(100.0000));
        expected.setTourType(TourType.ECOTOURISM);
        expected.addUser(elonMusk);
        expected.setHotel(cromlixHotel);
        expected.setCountry(france);

        Tour actual = tourRepository.findByDescription(expected.getDescription()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Tour savedTour = tourRepository.save(expected);
        Assert.assertNotNull(savedTour);
        expected.setId(savedTour.getId());
        Assert.assertEquals(expected, savedTour);

        actual = tourRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_Attached_Hotel_Country_And_User() {
        Tour expected = new Tour();
        expected.setPhotoPath("src/main/resources/oracle.properties");
        expected.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        expected.setDescription("TestTourDescription3");
        expected.setCost(BigDecimal.valueOf(100.0000));
        expected.setTourType(TourType.ECOTOURISM);
        User user = userRepository.findById(1L).orElse(null);
        expected.addUser(user);
        Hotel hotel = hotelRepository.findById(1L).orElse(null);
        expected.setHotel(hotel);
        Country country = countryRepository.findById(1L).orElse(null);
        expected.setCountry(country);

        Tour actual = tourRepository.findByDescription(expected.getDescription()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Tour savedTour = tourRepository.save(expected);
        Assert.assertNotNull(savedTour);
        expected.setId(savedTour.getId());
        Assert.assertEquals(expected, savedTour);

        actual = tourRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_New_Hotel_Country_And_User() {
        Tour expected = new Tour();
        expected.setPhotoPath("src/main/resources/oracle.properties");
        expected.setStartDate(Timestamp.valueOf("2021-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2021-01-02 00:00:00"));
        expected.setDescription("TestTourDescription1");
        expected.setCost(BigDecimal.valueOf(100.0000));
        expected.setTourType(TourType.ECOTOURISM);
        User user = new User();
        user.setLogin("TestUserLogin");
        user.setPassword("TestUserPassword");
        user.setRole(UserRole.USER);
        expected.addUser(user);
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName");
        hotel.setStars(Short.valueOf("5"));
        hotel.setWebsite("http://www.test-website.com");
        hotel.setLatitude(BigDecimal.valueOf(12.3456789));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));
        expected.setHotel(hotel);
        Country country = new Country();
        country.setName("TestCountryName");
        expected.setCountry(country);

        Tour actual = tourRepository.findByStartDateAndEndDate(expected.getStartDate(), expected.getEndDate()).stream().findAny().orElse(null);
        Assert.assertNull(actual);

        Tour savedTour = tourRepository.save(expected);
        Assert.assertNotNull(savedTour);
        expected.setId(savedTour.getId());
        Assert.assertEquals(expected, savedTour);

        actual = tourRepository.findById(expected.getId()).orElse(null);
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
        Tour actual = tourRepository.findById(firstTour.getId()).orElse(null);
        Assert.assertEquals(firstTour, actual);
    }

    @Test
    public void findByStartDate() {
        Timestamp startDate = Timestamp.valueOf("2021-01-01 00:00:00");
        List<Tour> tours = tourRepository.findByStartDate(startDate);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getStartDate().equals(startDate)));
    }

    @Test
    public void findByStartDateGreaterThanEqual() {
        Timestamp startDate = Timestamp.valueOf("2021-01-01 00:00:00");
        List<Tour> tours = tourRepository.findByStartDateGreaterThanEqual(startDate);
        Assert.assertTrue(tours.stream().allMatch(tour ->
                tour.getStartDate().after(startDate) || tour.getStartDate().equals(startDate)));
    }

    @Test
    public void findByStartDateLessThanEqual() {
        Timestamp startDate = Timestamp.valueOf("2021-01-01 00:00:00");
        List<Tour> tours = tourRepository.findByStartDateLessThanEqual(startDate);
        Assert.assertTrue(tours.stream().allMatch(tour ->
                tour.getStartDate().before(startDate) || tour.getStartDate().equals(startDate)));
    }

    @Test
    public void findByEndDate() {
        Timestamp endDate = Timestamp.valueOf("2021-01-02 00:00:00");
        List<Tour> tours = tourRepository.findByEndDate(endDate);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getEndDate().equals(endDate)));
    }

    @Test
    public void findByEndDateGreaterThanEqual() {
        Timestamp endDate = Timestamp.valueOf("2021-01-02 00:00:00");
        List<Tour> tours = tourRepository.findByEndDateGreaterThanEqual(endDate);
        Assert.assertTrue(tours.stream().allMatch(tour ->
                tour.getEndDate().after(endDate) || tour.getEndDate().equals(endDate)));
    }

    @Test
    public void findByEndDateLessThanEqual() {
        Timestamp endDate = Timestamp.valueOf("2021-01-02 00:00:00");
        List<Tour> tours = tourRepository.findByEndDateLessThanEqual(endDate);
        Assert.assertTrue(tours.stream().allMatch(tour ->
                tour.getEndDate().before(endDate) || tour.getEndDate().equals(endDate)));
    }

    @Test
    public void findByStartDateAndEndDate() {
        Timestamp startDate = Timestamp.valueOf("2021-01-01 00:00:00");
        Timestamp endDate = Timestamp.valueOf("2021-01-02 00:00:00");
        List<Tour> tours = tourRepository.findByStartDateAndEndDate(startDate, endDate);
        Assert.assertTrue(tours.stream().allMatch(tour ->
                tour.getStartDate().equals(startDate) && tour.getEndDate().equals(endDate)));
    }

    @Test
    public void findByDescription() {
        String description = "good TOUR";
        List<Tour> tours = tourRepository.findByDescription(description);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getDescription().equals(description)));
    }

    @Test
    public void findByDescriptionLike() {
        String descriptionRegex = "good TOUR";
        List<Tour> tours = tourRepository.findByDescriptionLike(descriptionRegex);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getDescription().matches(descriptionRegex)));
    }

    @Test
    public void findByDescriptionContaining() {
        String descriptionContains = "ood TOU";
        List<Tour> tours = tourRepository.findByDescriptionContaining(descriptionContains);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getDescription().contains(descriptionContains)));

    }

    @Test
    public void findByCost() {
        Tour actual = tourRepository.findByCost(firstTour.getCost()).stream().findFirst().orElse(null);
        Assert.assertEquals(firstTour, actual);
    }

    @Test
    public void findByCostGreaterThanEqual() {
        BigDecimal cost = BigDecimal.valueOf(100);
        List<Tour> tours = tourRepository.findByCostGreaterThanEqual(cost);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getCost().compareTo(cost) > -1));
    }

    @Test
    public void findByCostLessThanEqual() {
        BigDecimal cost = BigDecimal.valueOf(1000);
        List<Tour> tours = tourRepository.findByCostLessThanEqual(cost);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getCost().compareTo(cost) <= 0));
    }

    @Test
    public void findByCostBetween() {
        BigDecimal minCost = BigDecimal.valueOf(123);
        BigDecimal maxCost = BigDecimal.valueOf(456);
        List<Tour> tours = tourRepository.findByCostBetween(minCost, maxCost);
        Assert.assertTrue(tours.stream().allMatch(tour ->
                tour.getCost().compareTo(minCost) >= 0 && tour.getCost().compareTo(maxCost) <= 0));
    }

    @Test
    public void findByTourType() {
        TourType tourType = TourType.BUSINESS;
        List<Tour> tours = tourRepository.findByTourType(tourType);
        Assert.assertTrue(tours.stream().allMatch(tour -> tour.getTourType().equals(tourType)));
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<Tour> actual = tourRepository.findAll();
        Assert.assertTrue(actual.size() > 0);
    }

    @Test
    public void findAllById() {
    }

    @Test
    public void findAll_With_Sort() {
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
        Assert.assertTrue(tourRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
    }

    @Test
    public void count() {
        Assert.assertNotEquals(tourRepository.count(), 0);
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update_Without_Hotel_Country_And_Tour() {
        Tour expected = tourRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setPhotoPath("src/main/resources/log4j2.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-02 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-03 00:00:00"));
        expected.setDescription("updatedDescription");
        expected.setCost(BigDecimal.valueOf(101).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.LEISURE);
        tourRepository.save(expected);
        Tour actual = tourRepository.findById(2L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_With_Detached_Hotel_Country_And_Tour() {

    }

    @Test
    public void update_With_Attached_Hotel_Country_And_Tour() {

    }

    @Test
    public void update_With_New_Hotel_Country_And_Tour() {

    }

    @Test
    public void delete() {
        Tour foundTour = tourRepository.findById(3L).orElse(null);
        Assert.assertNotNull(foundTour);
        tourRepository.delete(foundTour);
        Tour deletedTour = tourRepository.findById(3L).orElse(null);
        Assert.assertNull(deletedTour);
    }

    @Test
    public void deleteById() {
        Tour foundTour = tourRepository.findById(12L).orElse(null);
        Assert.assertNotNull(foundTour);
        tourRepository.deleteById(12L);
        Tour deletedTour = tourRepository.findById(12L).orElse(null);
        Assert.assertNull(deletedTour);
    }

    @Test
    public void deleteAll() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteAll_By_Iterable() {

    }

    @Test
    public void deleteAllInBatch() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }
}
