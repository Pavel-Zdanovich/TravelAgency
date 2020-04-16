package com.zdanovich.core.integration.repository;

import com.zdanovich.core.config.MigrationConfig;
import com.zdanovich.core.config.PersistenceConfig;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.metamodel.Hotel_;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.repository.FeatureRepository;
import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.repository.ReviewRepository;
import com.zdanovich.core.repository.TourRepository;
import com.zdanovich.core.repository.UserRepository;
import com.zdanovich.core.utils.CoreConstants;
import liquibase.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Set;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;

@Test
@ContextConfiguration(classes = {PersistenceConfig.class, MigrationConfig.class, CountryRepository.class,
        FeatureRepository.class, HotelRepository.class, ReviewRepository.class, TourRepository.class, UserRepository.class})
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

    @BeforeClass
    @Transactional
    public void beforeClass() throws DatabaseException, NoSuchFieldException, IllegalAccessException {
        elonFirstTourReview = reviewRepository.findByUser_IdAndTour_Id(1L, 1L).orElseThrow(() ->
                new DatabaseException("Unable to find review 'sounds good'"));

        elonMusk = elonFirstTourReview.getUser();
        //userRepository.findByLogin("ElonMusk").orElseThrow(() -> new DatabaseException("Unable to find user 'ElonMusk'"));

        firstTour = elonFirstTourReview.getTour();
        //tourRepository.findById(1L).orElseThrow(() -> new DatabaseException("Unable to find tour '1L'"));

        cromlixHotel = firstTour.getHotel();
        //hotelRepository.findByName("Cromlix Hotel").orElseThrow(() -> new DatabaseException("Unable to find hotel 'Cromlix Hotel'"));

        parkHyattSaigon = hotelRepository.findByName("Park Hyatt Saigon").orElseThrow(() ->
                new DatabaseException("Unable to find hotel 'Park Hyatt Saigon'"));

        france = firstTour.getCountry();
        //countryRepository.findByName("France").orElseThrow(() -> new DatabaseException("Unable to find country 'France'"));

        spain = countryRepository.findByName("Spain").orElseThrow(() ->
                new DatabaseException("Unable to find country 'Spain'"));
        theGreatBritain = countryRepository.findByName("The Great Britain").orElseThrow(() ->
                new DatabaseException("Unable to find country 'The Great Britain'"));
        theNetherlands = countryRepository.findByName("The Netherlands").orElseThrow(() ->
                new DatabaseException("Unable to find country 'The Netherlands'"));

        Field hotelFeaturesField = Hotel.class.getDeclaredField(Hotel_.FEATURES);
        hotelFeaturesField.setAccessible(true);
        Set<Feature> features = (Set<Feature>) hotelFeaturesField.get(cromlixHotel);
        airConditioner = features.stream().filter(feature -> feature.getName().equals(CoreConstants.AIR_CONDITIONER))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        //featureRepository.findByName(CoreConstants.AIR_CONDITIONER).orElseThrow(() -> new DatabaseException("Unable to find feature 'air conditioner'"));
        cableTv = features.stream().filter(feature -> feature.getName().equals(CoreConstants.CABLE_TV))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        //featureRepository.findByName(CoreConstants.CABLE_TV).orElseThrow(() -> new DatabaseException("Unable to find feature 'cable TV'"));
        carRental = features.stream().filter(feature -> feature.getName().equals(CoreConstants.CAR_RENTAL))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        //featureRepository.findByName(CoreConstants.CAR_RENTAL).orElseThrow(() -> new DatabaseException("Unable to find feature 'car rental'"));
        miniBar = features.stream().filter(feature -> feature.getName().equals(CoreConstants.MINI_BAR))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        //featureRepository.findByName(CoreConstants.MINI_BAR).orElseThrow(() -> new DatabaseException("Unable to find feature 'mini-bar'"));
        parking = features.stream().filter(feature -> feature.getName().equals(CoreConstants.PARKING))
                .findAny().orElseThrow(() -> new EntityNotFoundException("Unable to find feature 'air conditioner'"));
        //featureRepository.findByName(CoreConstants.PARKING).orElseThrow(() -> new DatabaseException("Unable to find feature 'parking'"));

        Object object = entityManager.getProperties().get(STATEMENT_BATCH_SIZE);
        batchSize = object != null ? Integer.parseInt((String) object) : 10;
        entityManager.setProperty(STATEMENT_BATCH_SIZE, TEST_BATCH_SIZE);
    }

    @AfterClass
    public void afterClass() {
        entityManager.setProperty(STATEMENT_BATCH_SIZE, batchSize);
    }

    protected void clearCache() {
        entityManager.flush();
        entityManager.clear();
    }
}
