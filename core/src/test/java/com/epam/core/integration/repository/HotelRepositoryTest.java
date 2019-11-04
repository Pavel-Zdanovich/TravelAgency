package com.epam.core.integration.repository;

import com.epam.core.entity.Country;
import com.epam.core.entity.Hotel;
import com.epam.core.integration.config.EntityManagerConfig;
import com.epam.core.integration.config.MigrationConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import com.epam.core.repository.HotelRepository;
import de.flapdoodle.embed.process.collections.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, MigrationConfig.class, EntityManagerConfig.class, HotelRepository.class})
@ActiveProfiles(profiles = {"test", "postgresql"})
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void save() {
        Hotel expected = new Hotel();
        expected.setName("TestHotelName");
        expected.setWebsite("https://www.testhotel.com");
        expected.setStars(Short.valueOf("5"));
        expected.setLatitude(BigDecimal.valueOf(89).setScale(7, RoundingMode.HALF_UP));
        expected.setLongitude(BigDecimal.valueOf(189).setScale(7, RoundingMode.HALF_UP));
        hotelRepository.save(expected);
        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
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
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByName() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findByName(expected.getName()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStars() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findByStars(expected.getStars()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStarsLessThanEqual() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findByStarsLessThanEqual(expected.getStars()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStarsGreaterThanEqual() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findByStarsGreaterThanEqual(expected.getStars()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStarsBetween() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findByStarsBetween(Short.valueOf("4"), expected.getStars()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByFeatures_Name() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Hotel actual = hotelRepository.findByFeatures_Name("air conditioner").stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTours_Country() {
        Hotel expected = new Hotel();
        expected.setId(1L);
        expected.setName("Marriott");
        expected.setWebsite("https://www.marriott.com");
        expected.setStars(Short.valueOf("5"));
        Country country = new Country();
        country.setId(1L);
        country.setName("France");
        Hotel actual = hotelRepository.findByTours_Country(country).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<Hotel> actual = hotelRepository.findAll();
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
        Assert.assertTrue(hotelRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
    }

    @Test
    public void count() {
        Assert.assertNotEquals(hotelRepository.count(), 0);
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update() {
        Hotel expected = hotelRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setName("UpdatedCountryName");
        hotelRepository.save(expected);
        Hotel actual = hotelRepository.findById(2L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Hotel foundHotel = hotelRepository.findById(11L).orElse(null);
        Assert.assertNotNull(foundHotel);
        hotelRepository.delete(foundHotel);
        Hotel deletedHotel = hotelRepository.findById(11L).orElse(null);
        Assert.assertNull(deletedHotel);
    }

    @Test
    public void deleteById() {
        Hotel foundHotel = hotelRepository.findById(12L).orElse(null);
        Assert.assertNotNull(foundHotel);
        hotelRepository.deleteById(12L);
        Hotel deletedHotel = hotelRepository.findById(12L).orElse(null);
        Assert.assertNull(deletedHotel);
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void deleteAll_By_Iterable() {
        long numberOfHotelsBeforeDeletion = hotelRepository.count();
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("Marriott");
        hotel1.setWebsite("https://www.marriott.com");
        hotel1.setStars(Short.valueOf("5"));
        Hotel hotel2 = new Hotel();
        hotel2.setId(1L);
        hotel2.setName("Marriott");
        hotel2.setWebsite("https://www.marriott.com");
        hotel2.setStars(Short.valueOf("5"));
        hotelRepository.deleteAll(Collections.newArrayList(hotel1, hotel2));
        long numberOfHotelsAfterDeletion = hotelRepository.count();
        Assert.assertEquals(numberOfHotelsBeforeDeletion - numberOfHotelsAfterDeletion, 2);
    }

    @Test
    public void deleteAllInBatch() {
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }

}
