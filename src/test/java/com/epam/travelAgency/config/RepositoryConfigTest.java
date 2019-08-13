package com.epam.travelAgency.config;

import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TransactionConfig.class, JDBCConfig.class, EmbeddedPostgresConfig.class, FlywayConfig.class, RepositoryConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional
public class RepositoryConfigTest {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getHotelRepository() {
        Assert.assertNotNull(hotelRepository);
    }

    @Test
    public void getReviewRepository() {
        Assert.assertNotNull(reviewRepository);
    }

    @Test
    public void getTourRepository() {
        Assert.assertNotNull(tourRepository);
    }

    @Test
    public void getUserRepository() {
        Assert.assertNotNull(userRepository);
    }

}
