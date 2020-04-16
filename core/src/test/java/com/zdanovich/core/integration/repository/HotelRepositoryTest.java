package com.zdanovich.core.integration.repository;

import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.metamodel.Hotel_;
import com.zdanovich.core.service.specification.FindHotelByCoordinates;
import com.zdanovich.core.utils.CoreConstants;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HotelRepositoryTest extends AbstractRepositoryTest {

    @Test
    public void save_Without_Features() {
        Hotel expected = new Hotel();
        expected.setName("TestHotelName1");
        expected.setWebsite("http://www.test-website-1.com");
        expected.setStars(Short.valueOf("5"));
        expected.setLatitude(BigDecimal.valueOf(12.3456789));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));

        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedCountry = hotelRepository.save(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = hotelRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void save_With_Detached_Features() {
        Hotel expected = new Hotel();
        expected.setName("TestHotelName2");
        expected.setWebsite("http://www.test-website-2.com");
        expected.setStars(Short.valueOf("5"));
        expected.setLatitude(BigDecimal.valueOf(12.3456789));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = new Feature();
        airConditioner.setId(1L);
        airConditioner.setName(CoreConstants.AIR_CONDITIONER);
        expected.addFeature(airConditioner);
        Feature cableTv = new Feature();
        cableTv.setId(2L);
        cableTv.setName(CoreConstants.CABLE_TV);
        expected.addFeature(cableTv);

        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedCountry = hotelRepository.save(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = hotelRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_Attached_Features() {
        Hotel expected = new Hotel();
        expected.setName("TestHotelName3");
        expected.setWebsite("http://www.test-website-3.com");
        expected.setStars(Short.valueOf("5"));
        expected.setLatitude(BigDecimal.valueOf(12.3456789));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        expected.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        expected.addFeature(cableTv);

        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedCountry = hotelRepository.save(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = hotelRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void save_With_New_Features() {
        Hotel expected = new Hotel();
        expected.setName("TestHotelName4");
        expected.setWebsite("http://www.test-website-4.com");
        expected.setStars(Short.valueOf("5"));
        expected.setLatitude(BigDecimal.valueOf(12.3456789));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature newFeature1 = new Feature();
        newFeature1.setName("NewFeatureName1");
        Feature newFeature2 = new Feature();
        newFeature2.setName("NewFeatureName2");
        expected.addFeature(newFeature1);
        expected.addFeature(newFeature2);

        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedCountry = hotelRepository.save(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = hotelRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);

        Feature actualFeature1 = featureRepository.findById(newFeature1.getId()).orElse(null);
        Assert.assertEquals(newFeature1, actualFeature1);
        Feature actualFeature2 = featureRepository.findById(newFeature2.getId()).orElse(null);
        Assert.assertEquals(newFeature2, actualFeature2);
    }

    @Test
    public void saveAll_By_Iterable() {
        Hotel expected1 = new Hotel();
        expected1.setName("TestHotelName5");
        expected1.setWebsite("http://www.test-website-5.com");
        expected1.setStars(Short.valueOf("5"));
        expected1.setLatitude(BigDecimal.valueOf(12.3456789));
        expected1.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        expected1.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        expected1.addFeature(cableTv);
        Hotel expected2 = new Hotel();
        expected2.setName("TestHotelName6");
        expected2.setWebsite("http://www.test-website-6.com");
        expected2.setStars(Short.valueOf("5"));
        expected2.setLatitude(BigDecimal.valueOf(23.4567890));
        expected2.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        expected1.addFeature(carRental);
        Feature miniBar = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        expected1.addFeature(miniBar);
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(expected1);
        hotels.add(expected2);

        Hotel actual1 = hotelRepository.findByName(expected1.getName()).orElse(null);
        Assert.assertNull(actual1);
        Hotel actual2 = hotelRepository.findByName(expected2.getName()).orElse(null);
        Assert.assertNull(actual2);

        List<Hotel> savedHotels = hotelRepository.saveAll(hotels);
        Assert.assertNotNull(savedHotels);
        Assert.assertEquals(hotels.size(), savedHotels.size());
        actual1 = savedHotels.get(0);
        Assert.assertNotNull(actual1);
        expected1.setId(actual1.getId());
        Assert.assertEquals(expected1, actual1);
        actual2 = savedHotels.get(1);
        Assert.assertNotNull(actual2);
        expected2.setId(actual2.getId());
        Assert.assertEquals(expected2, actual2);

        actual1 = hotelRepository.findById(expected1.getId()).orElse(null);
        Assert.assertEquals(expected1, actual1);
        actual2 = hotelRepository.findById(expected2.getId()).orElse(null);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void saveAndFlush() {
        Hotel expected = new Hotel();
        expected.setName("TestHotelName7");
        expected.setWebsite("http://www.test-website-7.com");
        expected.setStars(Short.valueOf("5"));
        expected.setLatitude(BigDecimal.valueOf(34.5678901));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        expected.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        expected.addFeature(cableTv);

        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedCountry = hotelRepository.saveAndFlush(expected);
        Assert.assertNotNull(savedCountry.getId());
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = hotelRepository.findById(expected.getId()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(savedCountry, actual);
        actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(savedCountry, actual);
    }

    @Test
    public void flush() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName8");
        hotel.setWebsite("http://www.test-website-8.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        hotel.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        hotel.addFeature(cableTv);

        Hotel actual = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedHotel = hotelRepository.save(hotel);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());
        Assert.assertEquals(hotel, savedHotel);

        actual = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertEquals(hotel, actual);

        /**{@link CrudRepository#findById(Object)} synchronized with cache, but
         * {@link HotelRepository#findByName(String)} not synchronized and required flush
         */

        actual = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(actual);

        hotelRepository.flush();

        actual = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertEquals(hotel, actual);
    }

    @Test
    public void findById() {
        Hotel actual = hotelRepository.findById(cromlixHotel.getId()).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findByName() {
        Hotel actual = hotelRepository.findByName(cromlixHotel.getName()).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findByStars() {
        Short fiveStarts = Short.valueOf("5");
        List<Hotel> hotels = hotelRepository.findByStars(fiveStarts);
        Assert.assertFalse(hotels.isEmpty());
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getStars().equals(fiveStarts)));
    }

    @Test
    public void findByStarsLessThanEqual() {
        Short twoStars = Short.valueOf("2");
        List<Hotel> hotels = hotelRepository.findByStarsLessThanEqual(twoStars);
        Assert.assertNotNull(hotels);
        Assert.assertFalse(hotels.isEmpty());
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getStars() <= twoStars));
    }

    @Test
    public void findByStarsGreaterThanEqual() {
        Short fourStars = Short.valueOf("4");
        List<Hotel> hotels = hotelRepository.findByStarsGreaterThanEqual(fourStars);
        Assert.assertNotNull(hotels);
        Assert.assertFalse(hotels.isEmpty());
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getStars() >= fourStars));
    }

    @Test
    public void findByStarsBetween() {
        Short twoStars = Short.valueOf("2");
        Short fourStars = Short.valueOf("4");
        List<Hotel> hotels = hotelRepository.findByStarsBetween(twoStars, fourStars);
        Assert.assertNotNull(hotels);
        Assert.assertFalse(hotels.isEmpty());
        Assert.assertTrue(hotels.stream().allMatch(hotel -> twoStars.compareTo(hotel.getStars()) <= 0
                && fourStars.compareTo(hotel.getStars()) >= 0));
    }

    @Test
    public void findByFeatures_Name() {
        List<Hotel> hotels = hotelRepository.findByFeatures_Name(CoreConstants.AIR_CONDITIONER);
        Assert.assertNotNull(hotels);
        Assert.assertFalse(hotels.isEmpty());
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.containsFeature(CoreConstants.AIR_CONDITIONER)));
    }

    @Test
    public void findByTours_Country() {
        Hotel actual = hotelRepository.findByTours_Country(france).stream().findFirst().orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findOne_ById() {
        Hotel expected = new Hotel();
        expected.setId(cromlixHotel.getId());

        Hotel actual = hotelRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findOne_ByName() {
        Hotel expected = new Hotel();
        expected.setName(cromlixHotel.getName());

        Hotel actual = hotelRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findOne_ByWebsite() {
        Hotel expected = new Hotel();
        expected.setWebsite(cromlixHotel.getWebsite());

        Hotel actual = hotelRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    @Transactional
    public void findOne_BySpecification() {
        Hotel actual = hotelRepository.findOne(new FindHotelByCoordinates(cromlixHotel.getLatitude(), cromlixHotel.getLongitude())).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findOne_ByLatitude() {
        Hotel expected = new Hotel();
        expected.setLatitude(cromlixHotel.getLatitude());

        Hotel actual = hotelRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findOne_ByLongitude() {
        Hotel expected = new Hotel();
        expected.setLongitude(cromlixHotel.getLongitude());

        Hotel actual = hotelRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(cromlixHotel, actual);
    }

    @Test
    public void findAll() {
        List<Hotel> actual = hotelRepository.findAll();
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findAllById() {
        List<Long> hotelIds = new ArrayList<>();
        hotelIds.add(cromlixHotel.getId());
        hotelIds.add(parkHyattSaigon.getId());

        List<Hotel> foundHotels = hotelRepository.findAllById(hotelIds);
        Assert.assertNotNull(foundHotels);
        Assert.assertEquals(hotelIds.size(), foundHotels.size());
        Hotel actual1 = foundHotels.stream().filter(hotel -> hotel.getId().equals(cromlixHotel.getId())).findAny().orElse(null);
        Hotel actual2 = foundHotels.stream().filter(hotel -> hotel.getId().equals(parkHyattSaigon.getId())).findAny().orElse(null);
        Assert.assertEquals(cromlixHotel, actual1);
        Assert.assertEquals(parkHyattSaigon, actual2);
    }

    @Test
    public void findAll_With_Sort_ById () {
        List<Hotel> unsortedHotels = hotelRepository.findAll();
        Assert.assertNotNull(unsortedHotels);
        Assert.assertFalse(unsortedHotels.isEmpty());

        List<Hotel> sortedHotels = hotelRepository.findAll(Sort.by(Hotel_.HOTEL_ID));
        Assert.assertNotNull(sortedHotels);
        Assert.assertEquals(unsortedHotels.size(), sortedHotels.size());

        unsortedHotels.sort(Comparator.comparingLong(Hotel::getId));
        Assert.assertEquals(unsortedHotels, sortedHotels);
    }

    @Test
    public void findAll_With_Sort_ByName () {
        List<Hotel> unsortedHotels = hotelRepository.findAll();
        Assert.assertNotNull(unsortedHotels);
        Assert.assertFalse(unsortedHotels.isEmpty());

        List<Hotel> sortedHotels = hotelRepository.findAll(Sort.by(Hotel_.NAME));
        Assert.assertNotNull(sortedHotels);
        Assert.assertEquals(unsortedHotels.size(), sortedHotels.size());

        unsortedHotels.sort(Comparator.comparing(Hotel::getName));
        Assert.assertEquals(unsortedHotels, sortedHotels);
    }

    @Test
    public void findAll_With_Sort_ByWebsite () {
        List<Hotel> unsortedHotels = hotelRepository.findAll();
        Assert.assertNotNull(unsortedHotels);
        Assert.assertFalse(unsortedHotels.isEmpty());

        List<Hotel> sortedHotels = hotelRepository.findAll(Sort.by(Hotel_.WEBSITE));
        Assert.assertNotNull(sortedHotels);
        Assert.assertEquals(unsortedHotels.size(), sortedHotels.size());

        unsortedHotels.sort(Comparator.comparing(Hotel::getWebsite));
        Assert.assertEquals(unsortedHotels, sortedHotels);
    }

    @Test
    public void findAll_With_Sort_ByStars () {
        List<Hotel> unsortedHotels = hotelRepository.findAll();
        Assert.assertNotNull(unsortedHotels);
        Assert.assertFalse(unsortedHotels.isEmpty());

        List<Hotel> sortedHotels = hotelRepository.findAll(Sort.by(Hotel_.STARS));
        Assert.assertNotNull(sortedHotels);
        Assert.assertEquals(unsortedHotels.size(), sortedHotels.size());

        unsortedHotels.sort(Comparator.comparingInt(Hotel::getStars));
        Assert.assertEquals(unsortedHotels, sortedHotels);
    }

    @Test
    public void findAll_With_Sort_ByLatitude () {
        List<Hotel> unsortedHotels = hotelRepository.findAll();
        Assert.assertNotNull(unsortedHotels);
        Assert.assertFalse(unsortedHotels.isEmpty());

        List<Hotel> sortedHotels = hotelRepository.findAll(Sort.by(Hotel_.LATITUDE));
        Assert.assertNotNull(sortedHotels);
        Assert.assertEquals(unsortedHotels.size(), sortedHotels.size());

        unsortedHotels.sort(Comparator.comparing(Hotel::getLatitude));
        Assert.assertEquals(unsortedHotels, sortedHotels);
    }

    @Test
    public void findAll_With_Sort_ByLongitude () {
        List<Hotel> unsortedHotels = hotelRepository.findAll();
        Assert.assertNotNull(unsortedHotels);
        Assert.assertFalse(unsortedHotels.isEmpty());

        List<Hotel> sortedHotels = hotelRepository.findAll(Sort.by(Hotel_.LONGITUDE));
        Assert.assertNotNull(sortedHotels);
        Assert.assertEquals(unsortedHotels.size(), sortedHotels.size());

        unsortedHotels.sort(Comparator.comparing(Hotel::getLongitude));
        Assert.assertEquals(unsortedHotels, sortedHotels);
    }

    @Test
    public void findAll_By_Example_Id() {
        Hotel example = new Hotel();
        example.setId(cromlixHotel.getId());

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example));
        Assert.assertNotNull(hotels);
        Assert.assertEquals(1, hotels.size());
        Assert.assertEquals(cromlixHotel, hotels.get(0));
    }

    @Test
    public void findAll_By_Example_Name() {
        Hotel example = new Hotel();
        example.setName(cromlixHotel.getName());

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example));
        Assert.assertNotNull(hotels);
        Assert.assertEquals(1, hotels.size());
        Assert.assertEquals(cromlixHotel, hotels.get(0));
    }

    @Test
    public void findAll_By_Example_Website() {
        Hotel example = new Hotel();
        example.setWebsite(cromlixHotel.getWebsite());

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example));
        Assert.assertNotNull(hotels);
        Assert.assertEquals(1, hotels.size());
        Assert.assertEquals(cromlixHotel, hotels.get(0));
    }

    @Test
    public void findAll_By_Example_Stars() {
        Hotel example = new Hotel();
        example.setStars(cromlixHotel.getStars());

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example));
        Assert.assertNotNull(hotels);
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getStars().equals(cromlixHotel.getStars())));
    }

    @Test
    public void findAll_By_Example_Latitude() {
        Hotel example = new Hotel();
        example.setLatitude(cromlixHotel.getLatitude());

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example));
        Assert.assertNotNull(hotels);
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getLatitude().equals(cromlixHotel.getLatitude())));
    }

    @Test
    public void findAll_By_Example_Longitude() {
        Hotel example = new Hotel();
        example.setLongitude(cromlixHotel.getLongitude());

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example));
        Assert.assertNotNull(hotels);
        Assert.assertTrue(hotels.stream().allMatch(hotel -> hotel.getLongitude().equals(cromlixHotel.getLongitude())));
    }

    @Test
    public void findAll_By_Example_With_Sort() {
        Hotel example = new Hotel();
        example.setName("The");

        List<Hotel> hotels = hotelRepository.findAll(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)), Sort.by(Hotel_.NAME));
        hotels.forEach(hotel -> Assert.assertTrue(hotel.getName().startsWith(example.getName())));
    }

    @Test
    public void findAll_By_Example_With_Pagination() {
    }

    @Test
    public void findAll_With_Pagination() {
    }

    @Test
    public void existsById() {
        Assert.assertTrue(hotelRepository.existsById(cromlixHotel.getId()));
    }

    @Test
    public void exists_By_Example() {
        Hotel example = new Hotel();
        example.setName(cromlixHotel.getName());
        Assert.assertTrue(hotelRepository.exists((Example.of(example))));
    }

    @Test
    public void count() {
        Assert.assertNotEquals(0, hotelRepository.count());
    }

    @Test
    public void count_By_Example() {
        Hotel example = new Hotel();
        example.setName(cromlixHotel.getName());
        Assert.assertEquals(1, hotelRepository.count(Example.of(example)));
    }

    @Test
    public void count_By_Specification() {

    }

    @Test
    public void update_Without_Features() {

    }

    @Test
    public void update_With_Attached_Features() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName9");
        hotel.setWebsite("http://www.test-website-9.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));

        Hotel actual = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedHotel = hotelRepository.save(hotel);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());

        Hotel expected = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(hotel, expected);

        expected.setName("TestHotelName10");
        expected.setWebsite("http://www.test-website-10.com");
        expected.setStars(Short.valueOf("4"));
        expected.setLatitude(BigDecimal.valueOf(34.5678901));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        expected.removeFeature(airConditioner);
        expected.removeFeature(cableTv);
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        hotel.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        hotel.addFeature(cableTv);
        savedHotel = hotelRepository.save(expected);
        Assert.assertEquals(expected, savedHotel);

        actual = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void update_With_Detached_Features() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName9");
        hotel.setWebsite("http://www.test-website-9.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = new Feature();
        airConditioner.setId(1L);
        airConditioner.setName(CoreConstants.AIR_CONDITIONER);
        hotel.addFeature(airConditioner);
        Feature cableTv = new Feature();
        cableTv.setId(1L);
        cableTv.setName(CoreConstants.CABLE_TV);
        hotel.addFeature(cableTv);

        Hotel actual = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedHotel = hotelRepository.save(hotel);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());

        Hotel expected = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(hotel, expected);

        expected.setName("TestHotelName10");
        expected.setWebsite("http://www.test-website-10.com");
        expected.setStars(Short.valueOf("4"));
        expected.setLatitude(BigDecimal.valueOf(34.5678901));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        expected.removeFeature(airConditioner);
        expected.removeFeature(cableTv);
        Feature carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        expected.addFeature(carRental);
        Feature miniBar = featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        expected.addFeature(miniBar);
        savedHotel = hotelRepository.save(expected);
        Assert.assertEquals(expected, savedHotel);

        actual = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update_With_New_Features() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName9");
        hotel.setWebsite("http://www.test-website-9.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature feature1 = new Feature();
        feature1.setName("TestFeature1");
        hotel.addFeature(feature1);
        Feature feature2 = new Feature();
        feature2.setName("TestFeature2");
        hotel.addFeature(feature2);

        Hotel actual = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(actual);

        Hotel savedHotel = hotelRepository.save(hotel);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());

        Hotel expected = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(hotel, expected);

        expected.setName("TestHotelName10");
        expected.setWebsite("http://www.test-website-10.com");
        expected.setStars(Short.valueOf("4"));
        expected.setLatitude(BigDecimal.valueOf(34.5678901));
        expected.setLongitude(BigDecimal.valueOf(123.4567890));
        expected.removeFeature(airConditioner);
        expected.removeFeature(cableTv);
        Feature carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        expected.addFeature(carRental);
        Feature miniBar = featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        expected.addFeature(miniBar);
        savedHotel = hotelRepository.save(expected);
        Assert.assertEquals(expected, savedHotel);

        actual = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void delete() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName11");
        hotel.setWebsite("http://www.test-website-11.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        hotel.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        hotel.addFeature(cableTv);

        Hotel foundHotel = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(foundHotel);

        Hotel savedHotel = hotelRepository.save(hotel);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());

        foundHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNotNull(foundHotel);
        Assert.assertEquals(hotel, foundHotel);

        hotelRepository.delete(hotel);

        Hotel deletedHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNull(deletedHotel);
        airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        Assert.assertNotNull(airConditioner);
        cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        Assert.assertNotNull(cableTv);
    }

    @Test
    public void deleteById() {
        Hotel hotel = new Hotel();
        hotel.setName("TestHotelName12");
        hotel.setWebsite("http://www.test-website-12.com");
        hotel.setStars(Short.valueOf("5"));
        hotel.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        hotel.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        hotel.addFeature(cableTv);

        Hotel foundHotel = hotelRepository.findByName(hotel.getName()).orElse(null);
        Assert.assertNull(foundHotel);

        Hotel savedHotel = hotelRepository.save(hotel);
        Assert.assertNotNull(savedHotel);
        hotel.setId(savedHotel.getId());

        foundHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNotNull(foundHotel);
        Assert.assertEquals(hotel, foundHotel);

        hotelRepository.deleteById(hotel.getId());

        Hotel deletedHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        Assert.assertNull(deletedHotel);
        airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        Assert.assertNotNull(airConditioner);
        cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        Assert.assertNotNull(cableTv);
    }

    @Test
    public void deleteAll() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteAll_By_Iterable() {
        Hotel hotel1 = new Hotel();
        hotel1.setName("TestHotelName13");
        hotel1.setWebsite("http://www.test-website-13.com");
        hotel1.setStars(Short.valueOf("5"));
        hotel1.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel1.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        hotel1.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        hotel1.addFeature(cableTv);
        Hotel hotel2 = new Hotel();
        hotel2.setName("TestHotelName14");
        hotel2.setWebsite("http://www.test-website-14.com");
        hotel2.setStars(Short.valueOf("5"));
        hotel2.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel2.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        hotel2.addFeature(carRental);
        Feature miniBar = featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        hotel2.addFeature(miniBar);
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel1);
        hotels.add(hotel2);

        Hotel foundHotel = hotelRepository.findByName(hotel1.getName()).orElse(null);
        Assert.assertNull(foundHotel);
        foundHotel = hotelRepository.findByName(hotel2.getName()).orElse(null);
        Assert.assertNull(foundHotel);

        Hotel savedHotel = hotelRepository.save(hotel1);
        Assert.assertNotNull(savedHotel);
        hotel1.setId(savedHotel.getId());
        savedHotel = hotelRepository.save(hotel2);
        Assert.assertNotNull(savedHotel);
        hotel2.setId(savedHotel.getId());

        foundHotel = hotelRepository.findById(hotel1.getId()).orElse(null);
        Assert.assertNotNull(foundHotel);
        Assert.assertEquals(hotel1, foundHotel);
        foundHotel = hotelRepository.findById(hotel2.getId()).orElse(null);
        Assert.assertNotNull(foundHotel);
        Assert.assertEquals(hotel2, foundHotel);

        hotelRepository.deleteAll(hotels);

        Hotel deletedHotel = hotelRepository.findById(hotel1.getId()).orElse(null);
        Assert.assertNull(deletedHotel);
        airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        Assert.assertNotNull(airConditioner);
        cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        Assert.assertNotNull(cableTv);

        deletedHotel = hotelRepository.findById(hotel2.getId()).orElse(null);
        Assert.assertNull(deletedHotel);
        carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        Assert.assertNotNull(carRental);
        miniBar = featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        Assert.assertNotNull(miniBar);
    }

    @Test
    public void deleteAllInBatch() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteInBatch_By_Iterable() {
        Hotel hotel1 = new Hotel();
        hotel1.setName("TestHotelName15");
        hotel1.setWebsite("http://www.test-website-15.com");
        hotel1.setStars(Short.valueOf("5"));
        hotel1.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel1.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        hotel1.addFeature(airConditioner);
        Feature cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        hotel1.addFeature(cableTv);
        Hotel hotel2 = new Hotel();
        hotel2.setName("TestHotelName16");
        hotel2.setWebsite("http://www.test-website-16.com");
        hotel2.setStars(Short.valueOf("5"));
        hotel2.setLatitude(BigDecimal.valueOf(34.5678901));
        hotel2.setLongitude(BigDecimal.valueOf(123.4567890));
        Feature carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        hotel2.addFeature(carRental);
        Feature miniBar = featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel1);
        hotels.add(hotel2);

        Hotel foundHotel = hotelRepository.findByName(hotel1.getName()).orElse(null);
        Assert.assertNull(foundHotel);
        foundHotel = hotelRepository.findByName(hotel2.getName()).orElse(null);
        Assert.assertNull(foundHotel);

        Hotel savedHotel = hotelRepository.save(hotel1);
        Assert.assertNotNull(savedHotel);
        hotel1.setId(savedHotel.getId());
        savedHotel = hotelRepository.save(hotel2);
        Assert.assertNotNull(savedHotel);
        hotel2.setId(savedHotel.getId());

        foundHotel = hotelRepository.findById(hotel1.getId()).orElse(null);
        Assert.assertNotNull(foundHotel);
        Assert.assertEquals(hotel1, foundHotel);
        foundHotel = hotelRepository.findById(hotel2.getId()).orElse(null);
        Assert.assertNotNull(foundHotel);
        Assert.assertEquals(hotel2, foundHotel);

        clearCache();

        hotelRepository.deleteInBatch(hotels);

        Hotel deletedHotel = hotelRepository.findById(hotel1.getId()).orElse(null);
        Assert.assertNull(deletedHotel);
        airConditioner = featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        Assert.assertNotNull(airConditioner);
        cableTv = featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'cable TV'"));
        Assert.assertNotNull(cableTv);

        deletedHotel = hotelRepository.findById(hotel2.getId()).orElse(null);
        Assert.assertNull(deletedHotel);
        carRental = featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'car rental'"));
        Assert.assertNotNull(carRental);
        miniBar = featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() ->
                new EntityNotFoundException("Unable to find feature 'mini-bar'"));
        Assert.assertNotNull(miniBar);
    }
}
