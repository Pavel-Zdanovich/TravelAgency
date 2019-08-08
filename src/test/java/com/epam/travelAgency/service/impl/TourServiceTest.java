package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.TourType;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class TourServiceTest {

    @Autowired
    private Tour tour;
    @Autowired
    private Repository<Tour> tourRepository;
    @Autowired
    private Service<Tour> tourService;

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
        TourType[] tourTypes = TourType.values();
        tour.setTourType(tourTypes[new Random().nextInt(tourTypes.length)]);
        tourService.update(tour);
    }

    @Test
    public void save() {
        Tour tour = new Tour();
        tour.setTourId(0);
        tour.setTourType(TourType.SCIENTIFIC_EXPEDITION);
        tour.setCost(BigDecimal.valueOf(123));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tour.setStartDate(timestamp);
        tour.setEndDate(Timestamp.valueOf(LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.UTC).plusMonths(1)));
        tour.setPhotoPath("src/main/resources/log4j2.xml");
        tourService.save(tour);
    }

    @Test
    public void delete() {
        tourService.delete(tour);
    }

    @Test
    public void deleteById() {
        tourService.deleteById(0);
    }
}