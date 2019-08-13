package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.ApplicationConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Country;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.TourType;
import com.epam.travelAgency.service.TourService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, TransactionConfig.class, RepositoryConfig.class,
        ServiceConfig.class, ApplicationConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
public class TourServiceTest {

    private Tour tour;
    @Autowired
    private TourService tourService;

    @Before
    public void setUp() throws Exception {
        tour = new Tour();
        tour.setTourId(1L);
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tour.setStartDate(timestamp);
        tour.setEndDate(new Timestamp(timestamp.toLocalDateTime().plusMonths(1).toEpochSecond(ZoneOffset.UTC)));
        tour.setDescription("best tour");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.SPORT_COMPETITION);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);
        tour.setHotel(hotel);
        Country country = new Country();
        country.setCountryId(1L);
        tour.setCountry(country);
    }

    @Test
    public void findAll() {
        Assert.assertNotNull(tourService.findAll());
    }

    @Test
    public void findById() {
        Assert.assertNotNull(tourService.findById(1L));
    }

    @Test
    public void update() {
        tour.setCost(BigDecimal.valueOf(200));
        tour.setStartDate(new Timestamp(System.currentTimeMillis() + 10000));
        TourType[] tourTypes = TourType.values();
        tour.setTourType(tourTypes[new Random().nextInt(tourTypes.length)]);
        tourService.update(tour);
    }

    @Test
    public void save() {
        tourService.save(tour);
    }

    @Test
    public void delete() {
        tourService.delete(tour);
    }

    @Test
    public void deleteById() {
        tourService.deleteById(1L);
    }
}