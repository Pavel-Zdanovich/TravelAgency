package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.ServiceConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class, RepositoryConfig.class, ServiceConfig.class})
public class HotelServiceTest {

    @Autowired
    private Hotel hotel;
    @Autowired
    private Repository<Hotel> hotelRepository;
    @Autowired
    private Service<Hotel> hotelService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAll() {
        Assert.assertNotNull(hotelService.findAll());
    }

    @Test
    public void findById() {
        hotel = hotelService.findById(1L);
        Assert.assertNotNull(hotel);
    }

    @Test
    public void update() {
        hotel.setName(hotel.getName() + "LOL");
        hotelService.update(hotel);
    }

    @Test
    public void save() {
        Hotel hotel = new Hotel();
        hotel.setHotelId(0);
        hotel.setName("ProgMathist");
        hotel.setFeatures(new Feature[]{Feature.AIR_CONDITIONER, Feature.MINI_BAR});
        hotel.setStars(5);
        hotelService.save(hotel);
    }

    @Test
    public void delete() {
        hotelService.delete(hotel);
    }

    @Test
    public void deleteById() {
        hotelService.deleteById(0);
    }
}