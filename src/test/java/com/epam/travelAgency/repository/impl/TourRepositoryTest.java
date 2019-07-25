package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.impl.tour.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PGmoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class})
public class TourRepositoryTest {

    @Autowired
    private Tour tour;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private AddTourSpecification addTourSpecification;
    @Autowired
    private UpdateTourSpecification updateTourSpecification;
    @Autowired
    private RemoveTourSpecification removeTourSpecification;
    @Autowired
    private FindTourSpecification findTourSpecification;
    @Autowired
    private FindTourByCostRangeSpecification findTourByCostRangeSpecification;
    @Autowired
    private FindTourByCountryIdSpecification findTourByCountryIdSpecification;
    @Autowired
    private FindtourByDurationSpecification findTourByDurationSpecification;
    @Autowired
    private FindTourByEndDateSpecification findTourByEndDateSpecification;
    @Autowired
    private FindTourByHotelIdSpecification findTourByHotelIdSpecification;
    @Autowired
    private FindTourByMaxCostSpecification findTourByMaxCostSpecification;
    @Autowired
    private FindTourByMinCostSpecification findTourByMinCostSpecification;
    @Autowired
    private FindTourByStarsSpecification findTourByStarsSpecification;
    @Autowired
    private FindTourByStartDateSpecification findTourByStartDateSpecification;
    @Autowired
    private FindTourByTypeSpecification findTourByTypeSpecification;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(EntityConfig.class);
        tour = applicationContext.getBean(Tour.class);
        tour.setTourId(Long.MAX_VALUE);
        tour.setPhotoPath(Paths.get("src/main/resources/applicationContext.xml"));
        Timestamp timestamp = Timestamp.valueOf("2019-08-31 00:00:00");
        tour.setStartDate(timestamp);
        tour.setEndDate(Timestamp.from(timestamp.toInstant().plus(10, ChronoUnit.DAYS)));
        tour.setDescription("description of tour");
        tour.setCost(new PGmoney(100.0));
        tour.setType(Tour.Type.SCIENTIFIC_EXPEDITION);
        tour.setHotelId(Long.MAX_VALUE);
        tour.setCountryId(Long.MAX_VALUE);
    }

    @Test
    public void add_tour_by_addTourSpecification() {
        addTourSpecification.setTour(tour);
        tourRepository.add(addTourSpecification);
    }

    @Test
    public void update_tour_by_updateTourSpecification() {
        updateTourSpecification.setTour(tour);
        tourRepository.update(updateTourSpecification);
    }

    @Test
    public void remove_tour_by_removeTourSpecification() {
        removeTourSpecification.setTour(tour);
        tourRepository.remove(removeTourSpecification);
    }

    @Test
    public void query_tour_by_findTourSpecification() {
        findTourSpecification.setSpecification(tour);
        tourRepository.query(findTourSpecification);
    }

    @Test
    public void query_tour_by_findTourByCostRangeSpecification() {
        findTourByCostRangeSpecification.setSpecification(new FindTourByCostRangeSpecification.CostRange(new PGmoney(100), new PGmoney(200)));
        tourRepository.query(findTourByCostRangeSpecification);
    }

    @Test
    public void query_tour_by_findTourByCountryIdSpecification() {
        findTourByCountryIdSpecification.setSpecification(Long.MAX_VALUE);
        tourRepository.query(findTourByCountryIdSpecification);
    }

    @Test
    public void query_tour_by_findTourByDurationSpecification() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        findTourByDurationSpecification.setSpecification(Period.between(timestamp.toLocalDateTime().toLocalDate(), timestamp.toLocalDateTime().toLocalDate().plusMonths(1)));
        tourRepository.query(findTourByDurationSpecification);
    }

    @Test
    public void query_tour_by_findTourByEndDateSpecification() {
        findTourByEndDateSpecification.setSpecification(new Timestamp(System.currentTimeMillis()));
        tourRepository.query(findTourByEndDateSpecification);
    }

    @Test
    public void query_tour_by_findTourByHotelIdSpecification() {
        findTourByHotelIdSpecification.setSpecification(Long.MAX_VALUE);
        tourRepository.query(findTourByHotelIdSpecification);
    }

    @Test
    public void query_tour_by_findTourByMaxCostSpecification() {
        findTourByMaxCostSpecification.setSpecification(new PGmoney(1000));
        tourRepository.query(findTourByMaxCostSpecification);
    }

    @Test
    public void query_tour_by_findTourByMinCostSpecification() {
        findTourByMinCostSpecification.setSpecification(new PGmoney(0));
        tourRepository.query(findTourByMinCostSpecification);
    }

    @Test
    public void query_tour_by_findTourByStarsSpecification() {
        findTourByStarsSpecification.setSpecification(5);
        tourRepository.query(findTourByStarsSpecification);
    }

    @Test
    public void query_tour_by_findTourByTypeSpecification() {
        findTourByTypeSpecification.setSpecification(Tour.Type.SCIENTIFIC_EXPEDITION);
        tourRepository.query(findTourByTypeSpecification);
    }

}