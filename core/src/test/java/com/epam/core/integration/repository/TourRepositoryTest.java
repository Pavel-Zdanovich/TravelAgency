package com.epam.core.integration.repository;

import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.Tour;
import com.epam.core.entity.enums.TourType;
import com.epam.core.config.MigrationConfig;
import com.epam.core.repository.TourRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, MigrationConfig.class, TourRepository.class})
public class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Test
    public void save() {
        Tour expected = new Tour();
        expected.setPhotoPath("src/main/resources/log4j2.xml");
        expected.setStartDate(Timestamp.valueOf("2020-10-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-10-11 00:00:00"));
        expected.setDescription("TestTourDescription");
        expected.setCost(BigDecimal.valueOf(123.456).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByDescription(expected.getDescription()).stream().findFirst().orElse(null);
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
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStartDate() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByStartDate(expected.getStartDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStartDateGreaterThanEqual() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByStartDateGreaterThanEqual(expected.getStartDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStartDateLessThanEqual() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByStartDateLessThanEqual(expected.getStartDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByEndDate() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByEndDate(expected.getEndDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByEndDateGreaterThanEqual() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByEndDateGreaterThanEqual(expected.getEndDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByEndDateLessThanEqual() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByEndDateLessThanEqual(expected.getEndDate()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByStartDateAndEndDate() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByStartDateAndEndDate(expected.getStartDate(), expected.getEndDate())
                .stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByDescription() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByDescription(expected.getDescription()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByDescriptionLike() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByDescriptionLike("%description%").stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByDescriptionContaining() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByDescriptionContaining(expected.getDescription().substring(0, 5)).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByCost() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByCost(expected.getCost()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByCostGreaterThanEqual() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByCostGreaterThanEqual(expected.getCost()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByCostLessThanEqual() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByCostLessThanEqual(expected.getCost()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByCostBetween() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByCostBetween(expected.getCost().subtract(BigDecimal.ONE), expected.getCost().add(BigDecimal.ONE))
                .stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByTourType() {
        Tour expected = new Tour();
        expected.setId(1L);
        expected.setPhotoPath("src/main/resources/log4j.xml");
        expected.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        expected.setEndDate(Timestamp.valueOf("2020-01-30 00:00:00"));
        expected.setDescription("description");
        expected.setCost(BigDecimal.valueOf(100).setScale(4, RoundingMode.HALF_UP));
        expected.setTourType(TourType.TOURISM);
        Tour actual = tourRepository.findByTourType(expected.getDescription()).stream().findFirst().orElse(null);
        Assert.assertEquals(expected, actual);
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
    public void update() {
        Tour expected = tourRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setPhotoPath("src/main/resources/");
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
    }

    @Test
    public void deleteAll_By_Iterable() {
        long numberOfToursBeforeDeletion = tourRepository.count();
        Tour tour1 = new Tour();
        tour1.setPhotoPath("src/main/resources/");
        tour1.setStartDate(Timestamp.valueOf("2020-01-02 00:00:00"));
        tour1.setEndDate(Timestamp.valueOf("2020-01-03 00:00:00"));
        tour1.setDescription("updatedDescription");
        tour1.setCost(BigDecimal.valueOf(101).setScale(4, RoundingMode.HALF_UP));
        tour1.setTourType(TourType.LEISURE);
        Tour tour2 = new Tour();
        tour2.setPhotoPath("src/main/resources/");
        tour2.setStartDate(Timestamp.valueOf("2020-01-02 00:00:00"));
        tour2.setEndDate(Timestamp.valueOf("2020-01-03 00:00:00"));
        tour2.setDescription("updatedDescription");
        tour2.setCost(BigDecimal.valueOf(101).setScale(4, RoundingMode.HALF_UP));
        tour2.setTourType(TourType.LEISURE);
        tourRepository.deleteAll(new ArrayList<Tour>() {{
            add(tour1);
            add(tour2);
        }});
        long numberOfToursAfterDeletion = tourRepository.count();
        Assert.assertEquals(numberOfToursBeforeDeletion - numberOfToursAfterDeletion, 2);
    }

    @Test
    public void deleteAllInBatch() {
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }

}
