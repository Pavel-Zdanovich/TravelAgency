package com.epam.travelAgency.exceptional;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.JDBCConfig;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.impl.HotelRepository;
import com.epam.travelAgency.specification.impl.hotel.AddHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.FindHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.RemoveHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.UpdateHotelSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgis.PGgeometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EntityConfig.class, JDBCConfig.class, HotelRepository.class,
        AddHotelSpecification.class, UpdateHotelSpecification.class, RemoveHotelSpecification.class, FindHotelSpecification.class})
public class HotelRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(HotelRepositoryTest.class);

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
        fillHotel(hotel);
    }

    private void fillHotel(Hotel hotel) throws SQLException {
        hotel.setHotelId(1L);
        hotel.setName("Marriott");
        hotel.setStars(5);
        hotel.setWebsite("https://marriott.com");
        hotel.setCoordinate(new PGgeometry(PGgeometry.geomFromString("SRID=4326;POINT(37.617635 55.755814 42.419420)")));
        hotel.setFeatures(new Hotel.Feature[]{Hotel.Feature.AIR_CONDITIONER, Hotel.Feature.MINI_BAR});
    }

    @Test
    public void add_hotel_by_addHotelSpecification() {
        addHotel(hotel);
    }

    private void addHotel(Hotel hotel) {
        addHotelSpecification.setHotel(hotel);
        hotelRepository.add(addHotelSpecification);
    }

    @Test
    public void update_hotel_by_updateHotelSpecification() {
        addHotel(hotel);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1);
        hotel.setName("Hilton");
        hotel.setStars(3);
        hotel.setWebsite("http://hilton.com");
        try {
            hotel.setCoordinate(new PGgeometry(PGgeometry.geomFromString("SRID=4326;POINT(42.419420 69.666139 27.337318)")));
            hotel.setFeatures(new Hotel.Feature[]{Hotel.Feature.CABLE_TV, Hotel.Feature.PARKING});
            updateHotelSpecification.setHotel(hotel);
            hotelRepository.update(updateHotelSpecification);
        } catch (SQLException e) {
            logger.error("Parsing PostGIS coordinate error");
        }
        removeHotel(hotel);
    }

    @Test
    public void remove_hotel_by_removeHotelSpecification() {
        removeHotel(hotel);
    }

    @Test
    public void query_hotel_by_findHotelSpecification() {
        findHotelSpecification.setSpecification(hotel);
        hotelRepository.query(findHotelSpecification);
    }

    @After
    public void tearDown() throws Exception {
        removeHotel(hotel);
    }

    private void removeHotel(Hotel hotel) {
        removeHotelSpecification.setHotel(hotel);
        hotelRepository.remove(removeHotelSpecification);
    }

}
