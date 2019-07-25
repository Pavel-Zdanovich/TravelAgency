package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.embedded.EmbeddedPostgresConfig;
import com.epam.travelAgency.embedded.FlywayConfig;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.impl.hotel.AddHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.FindHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.RemoveHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.UpdateHotelSpecification;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedPostgresConfig.class, FlywayConfig.class, EntityConfig.class})
public class HotelRepositoryTest {

    @Autowired
    private Hotel hotel;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AddHotelSpecification addHotelSpecification;
    @Autowired
    private UpdateHotelSpecification updateHotelSpecification;
    @Autowired
    private RemoveHotelSpecification removeHotelSpecification;
    @Autowired
    private FindHotelSpecification findHotelSpecification;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(EntityConfig.class);
        hotel = applicationContext.getBean(Hotel.class);
        hotel.setHotelId(1);
        hotel.setName("Marriott");
        hotel.setStars(5);
        hotel.setWebsite("https://marriott.com");
        Hotel.Feature[] features = {Hotel.Feature.AIR_CONDITIONER, Hotel.Feature.MINI_BAR};
        hotel.setFeatures(features);
    }

    @Ignore
    @Test
    public void add_hotel_by_addHotelSpecification() {
        addHotelSpecification.setHotel(hotel);
        hotelRepository.add(addHotelSpecification);

    }

    @Ignore
    @Test
    public void update_hotel_by_UpdateHotelSpecification() {
        updateHotelSpecification.setHotel(hotel);
        hotelRepository.update(updateHotelSpecification);
    }

    @Ignore
    @Test
    public void remove_hotel_by_RemoveHotelSpecification() {
        removeHotelSpecification.setHotel(hotel);
        hotelRepository.remove(removeHotelSpecification);
    }

    @Ignore
    @Test
    public void query() {
        findHotelSpecification.setSpecification(hotel);
        hotelRepository.query(findHotelSpecification);
    }
}