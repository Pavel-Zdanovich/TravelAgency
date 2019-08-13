package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.ApplicationConfig;
import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.*;
import com.epam.travelAgency.repository.TourRepository;
import com.epam.travelAgency.specification.impl.common.FindTourByStarsSpecification;
import com.epam.travelAgency.specification.impl.common.FindTourByUserIdSpecification;
import com.epam.travelAgency.specification.impl.common.FindTourByUserLoginSpecification;
import com.epam.travelAgency.specification.impl.common.FindTourByUserSpecification;
import com.epam.travelAgency.specification.impl.tour.*;
import com.epam.travelAgency.util.CostRange;
import com.epam.travelAgency.util.Criterion;
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
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, TransactionConfig.class, EntityConfig.class,
        RepositoryConfig.class, ApplicationConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
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
    private FindAllToursSpecification findAllToursSpecification;
    @Autowired
    private FindTourSpecification findTourSpecification;
    @Autowired
    private FindTourByIdSpecification findTourByIdSpecification;
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
    @Autowired
    private FindTourByUserIdSpecification findTourByUserIdSpecification;
    @Autowired
    private FindTourByUserLoginSpecification findTourByUserLoginSpecification;
    @Autowired
    private FindTourByUserSpecification findTourByUserSpecification;
    @Autowired
    private FindTourByCriterionSpecification findTourByCriterionSpecification;

    @Before
    public void setUp() throws Exception {
        tour.setPhotoPath("src/main/resources/renamed.applicationContext.xml");
        Timestamp timestamp = Timestamp.valueOf("2019-08-31 00:00:00");
        tour.setStartDate(timestamp);
        tour.setEndDate(Timestamp.from(timestamp.toInstant().plus(10, ChronoUnit.DAYS)));
        tour.setDescription("description of tour");
        tour.setCost(BigDecimal.valueOf(100));
        tour.setTourType(TourType.SCIENTIFIC_EXPEDITION);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);
        tour.setHotel(hotel);
        Country country = new Country();
        country.setCountryId(1L);
        tour.setCountry(country);
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
    public void query_all_tours_by_findAllToursSpecification() {
        tourRepository.query(findAllToursSpecification);
    }

    @Test
    public void query_tour_by_findTourSpecification() {
        findTourSpecification.setSpecification(tour);
        tourRepository.query(findTourSpecification);
    }

    @Test
    public void query_tour_by_findTourByIdSpecification() {
        findTourByIdSpecification.setSpecification(1L);
        tourRepository.query(findTourByIdSpecification);
    }

    @Test
    public void query_tour_by_findTourByCostRangeSpecification() {
        findTourByCostRangeSpecification.setSpecification(new CostRange(BigDecimal.valueOf(100), BigDecimal.valueOf(200)));
        tourRepository.query(findTourByCostRangeSpecification);
    }

    @Test
    public void query_tour_by_findTourByCountryIdSpecification() {
        findTourByCountryIdSpecification.setSpecification(1L);
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
        findTourByHotelIdSpecification.setSpecification(1L);
        tourRepository.query(findTourByHotelIdSpecification);
    }

    @Test
    public void query_tour_by_findTourByMaxCostSpecification() {
        findTourByMaxCostSpecification.setSpecification(BigDecimal.valueOf(1000));
        tourRepository.query(findTourByMaxCostSpecification);
    }

    @Test
    public void query_tour_by_findTourByMinCostSpecification() {
        findTourByMinCostSpecification.setSpecification(BigDecimal.valueOf(100));
        tourRepository.query(findTourByMinCostSpecification);
    }

    @Test
    public void query_tour_by_findTourByStarsSpecification() {
        findTourByStarsSpecification.setSpecification(5);
        tourRepository.query(findTourByStarsSpecification);
    }

    @Test
    public void query_tour_by_findTourByTypeSpecification() {
        findTourByTypeSpecification.setSpecification(TourType.SCIENTIFIC_EXPEDITION);
        tourRepository.query(findTourByTypeSpecification);
    }

    @Test
    public void query_tour_by_findTourByUserIdSpecification() {
        findTourByUserIdSpecification.setSpecification(1L);
        tourRepository.query(findTourByUserIdSpecification);
    }

    @Test
    public void query_tour_by_findTourByUserLoginSpecification() {
        findTourByUserLoginSpecification.setSpecification("ElonMusk");
        tourRepository.query(findTourByUserLoginSpecification);
    }

    @Test
    public void query_tour_by_findTourByUserSpecification() {
        User user = new User();
        user.setUserId(1L);
        user.setLogin("ElonMusk");
        user.setPassword("SpaceXXX");
        findTourByUserSpecification.setSpecification(user);
        tourRepository.query(findTourByUserSpecification);
    }

    @Test
    public void query_tour_by_findTourByCriterionCpesification() {
        Criterion criterion = new Criterion();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        criterion.setStartDate(timestamp);
        criterion.setEndDate(Timestamp.valueOf(LocalDateTime.of(2019, 8, 31, 0, 0, 0)));
        criterion.setTourType(TourType.TOURISM);
        findTourByCriterionSpecification.setSpecification(criterion);
        tourRepository.query(findTourByCriterionSpecification);
    }

}