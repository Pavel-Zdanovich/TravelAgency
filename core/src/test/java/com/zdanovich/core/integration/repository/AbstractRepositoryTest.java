package com.zdanovich.core.integration.repository;

import com.zdanovich.core.config.CoreModuleConfiguration;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.repository.FeatureRepository;
import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.repository.ReviewRepository;
import com.zdanovich.core.repository.TourRepository;
import com.zdanovich.core.repository.UserRepository;
import com.zdanovich.core.utils.Utils;
import liquibase.exception.DatabaseException;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Test
@ContextConfiguration(classes = CoreModuleConfiguration.class)
public abstract class AbstractRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    protected CountryRepository countryRepository;
    @Autowired
    protected FeatureRepository featureRepository;
    @Autowired
    protected HotelRepository hotelRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected TourRepository tourRepository;
    @Autowired
    protected UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    protected Country france;
    protected Country spain;
    protected Country theGreatBritain;
    protected Country theNetherlands;

    protected Feature airConditioner;
    protected Feature cableTv;
    protected Feature carRental;
    protected Feature miniBar;
    protected Feature parking;

    protected Hotel cromlixHotel;
    protected Hotel parkHyattSaigon;

    protected Review elonFirstTourReview;

    protected Tour firstTour;

    protected User elonMusk;

    public static final int TEST_BATCH_SIZE = 2;

    private int batchSize;

    @BeforeSuite
    public void beforeSuite() {
        logger.info("<---------- Before suite ---------->");
        if (entityManager != null) {
            logger.info("Spring is started");
        }
    }

    @BeforeGroups
    public void beforeGroups() {
        logger.info("<---------- Before groups ---------->");
        if (entityManager != null) {
            logger.info("Spring is started");
        }
    }

    @BeforeClass
    @Transactional
    public void beforeClass() throws DatabaseException, NoSuchFieldException, IllegalAccessException {
        logger.info("<---------- Before class ---------->");
        if (entityManager != null) {
            logger.info("Spring is started");
        }

        elonFirstTourReview = reviewRepository.findByUser_IdAndTour_Id(1L, 1L).orElseThrow(() ->
                new EntityNotFoundException("Unable to find review 'sounds good'"));

        elonMusk = elonFirstTourReview.getUser();
        //userRepository.findByLogin("ElonMusk").orElseThrow(() -> new DatabaseException("Unable to find user 'ElonMusk'"));

        firstTour = elonFirstTourReview.getTour();
        //tourRepository.findById(1L).orElseThrow(() -> new DatabaseException("Unable to find tour '1L'"));

        cromlixHotel = firstTour.getHotel();
        //hotelRepository.findByName("Cromlix Hotel").orElseThrow(() -> new DatabaseException("Unable to find hotel 'Cromlix Hotel'"));

        parkHyattSaigon = hotelRepository.findByName("Park Hyatt Saigon").orElseThrow(() ->
                new EntityNotFoundException("Unable to find hotel 'Park Hyatt Saigon'"));

        france = firstTour.getCountry();
        //countryRepository.findByName("France").orElseThrow(() -> new DatabaseException("Unable to find country 'France'"));

        spain = countryRepository.findByName("Spain").orElseThrow(() ->
                new EntityNotFoundException("Unable to find country 'Spain'"));
        theGreatBritain = countryRepository.findByName("The Great Britain").orElseThrow(() ->
                new EntityNotFoundException("Unable to find country 'The Great Britain'"));
        theNetherlands = countryRepository.findByName("The Netherlands").orElseThrow(() ->
                new EntityNotFoundException("Unable to find country 'The Netherlands'"));

        airConditioner = cromlixHotel.getFeature(Utils.AIR_CONDITIONER).orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));

        cableTv = cromlixHotel.getFeature(Utils.CABLE_TV).orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));

        carRental = cromlixHotel.getFeature(Utils.CAR_RENTAL).orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));

        miniBar = cromlixHotel.getFeature(Utils.MINI_BAR).orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));

        parking = cromlixHotel.getFeature(Utils.PARKING).orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));

        Object object = entityManager.getProperties().get(AvailableSettings.STATEMENT_BATCH_SIZE);
        batchSize = object != null ? Integer.parseInt((String) object) : 10;
        entityManager.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, TEST_BATCH_SIZE);
    }

    @BeforeTest
    public void beforeTest() {
        logger.info("<---------- Before test ---------->");
        if (entityManager != null) {
            logger.info("Spring is started");
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        logger.info("<---------- Before method ---------->");
        if (entityManager != null) {
            logger.info("Spring is started");
        }
    }

    @AfterSuite
    public void afterSuite() {
        logger.info("<---------- After suite ---------->");
    }

    @AfterGroups
    public void afterGroups() {
        logger.info("<---------- After groups ---------->");
    }

    @AfterClass
    public void afterClass() {
        logger.info("<---------- After class ---------->");
        entityManager.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, batchSize);
    }

    @AfterTest
    public void afterTest() {
        logger.info("<---------- After test ---------->");
    }

    @AfterMethod
    public void afterMethod() {
        logger.info("<---------- After method ---------->");
    }

    protected void clearCache() {
        entityManager.flush();
        entityManager.clear();
    }
}
