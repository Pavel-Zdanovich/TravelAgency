package com.epam.travelAgency.exceptional;

import com.epam.travelAgency.config.EntityConfig;
import com.epam.travelAgency.config.HibernateConfig;
import com.epam.travelAgency.config.JDBCConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.impl.HotelRepository;
import com.epam.travelAgency.specification.impl.hotel.AddHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.FindHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.RemoveHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.UpdateHotelSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EntityConfig.class, RepositoryConfig.class, JDBCConfig.class, HibernateConfig.class,
        AddHotelSpecification.class, UpdateHotelSpecification.class, RemoveHotelSpecification.class, FindHotelSpecification.class})
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
        fillHotel(hotel);
    }

    private void fillHotel(Hotel hotel) throws SQLException, MalformedURLException {
        hotel.setHotelId(1L);
        hotel.setName("Marriott");
        hotel.setStars(5);
        hotel.setWebsite(new URL("https://marriott.com"));
        hotel.setCoordinate(new PGgeometry(PGgeometry.geomFromString("SRID=4326;POINT(37.617635 55.755814 42.419420)")));
        hotel.setFeatures(new Feature[]{Feature.AIR_CONDITIONER, Feature.MINI_BAR});
    }

    @Test
    public void add_hotel_by_addHotelSpecification() {
        addHotelSpecification.setHotel(hotel);
        addHotel(hotel);
    }

    private void addHotel(Hotel hotel) {
        addHotelSpecification.setHotel(hotel);
        hotelRepository.add(addHotelSpecification);
    }

    @Test
    public void update_hotel_by_updateHotelSpecification() throws MalformedURLException {
        addHotel(hotel);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1);
        hotel.setName("Hilton");
        hotel.setStars(3);
        hotel.setWebsite(new URL("http://hilton.com"));
        try {
            hotel.setCoordinate(new PGgeometry(PGgeometry.geomFromString("SRID=4326;POINT(42.419420 69.666139 27.337318)")));
            hotel.setFeatures(new Feature[]{Feature.CABLE_TV, Feature.PARKING});
            updateHotelSpecification.setHotel(hotel);
            hotelRepository.update(updateHotelSpecification);
        } catch (SQLException e) {
            log.error("Parsing PostGIS coordinate error");
        }
        removeHotel(hotel);
    }

    @Test
    public void remove_hotel_by_removeHotelSpecification() {
        removeHotel(hotel);
    }

    @Test
    public void query_hotel_by_findHotelSpecification() {
        HotelRepository hotelRepository = Mockito.mock(HotelRepository.class);
        findHotelSpecification.setSpecification(hotel);
        Assert.assertEquals(hotelRepository.query(findHotelSpecification), List.of(hotel));
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
