package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.ApplicationConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.service.HotelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, TransactionConfig.class,
        RepositoryConfig.class, ServiceConfig.class, ApplicationConfig.class})
@ActiveProfiles(profiles = {"dev", "test", "test_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
public class HotelServiceTest {

    private Hotel hotel;
    @Autowired
    private HotelService hotelService;

    @Before
    public void setUp() throws Exception {
        hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setName("Marriott");
        hotel.setWebsite(new URL("https://www.marriot.com"));
        hotel.setStars(5);
        hotel.setCoordinate(PGgeometry.geomFromString("SRID=4326;POINT(37.617635 55.755814 42.419420)"));
        hotel.setFeatures(new Feature[]{Feature.AIR_CONDITIONER, Feature.CAR_RENTAL});
    }

    @Test
    public void findAll() {
        Assert.assertNotNull(hotelService.findAll());
    }

    @Test
    public void findById() {
        Assert.assertNotNull(hotelService.findById(1L));
    }

    @Test
    public void update() throws MalformedURLException {
        hotel.setName("Hilton");
        hotel.setWebsite(new URL("http://www.hilton-hotel.com"));
        hotelService.update(hotel);
    }

    @Test
    public void save() {
        hotelService.save(hotel);
    }

    @Test
    public void delete() {
        hotelService.delete(hotel);
    }

    @Test
    public void deleteById() {
        hotelService.deleteById(1L);
    }
}