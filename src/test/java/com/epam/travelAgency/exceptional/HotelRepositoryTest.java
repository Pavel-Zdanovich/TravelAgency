package com.epam.travelAgency.exceptional;

import com.epam.travelAgency.config.JDBCConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.HotelRepository;
import com.epam.travelAgency.specification.impl.hotel.AddHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.FindHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.RemoveHotelSpecification;
import com.epam.travelAgency.specification.impl.hotel.UpdateHotelSpecification;
import org.junit.After;
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
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JDBCConfig.class, TransactionConfig.class, RepositoryConfig.class})
@ActiveProfiles(profiles = {"dev", "real_dataSource"})
@Transactional(transactionManager = "jpaTransactionManager")
public class HotelRepositoryTest {

    private Hotel hotel;
    @Autowired
    private HotelRepository hotelRepository;
    private AddHotelSpecification addHotelSpecification;
    private UpdateHotelSpecification updateHotelSpecification;
    private RemoveHotelSpecification removeHotelSpecification;
    private FindHotelSpecification findHotelSpecification;

    @Before
    public void setUp() throws Exception {
        hotel = new Hotel();
        addHotelSpecification = new AddHotelSpecification();
        updateHotelSpecification = new UpdateHotelSpecification();
        removeHotelSpecification = new RemoveHotelSpecification();
        findHotelSpecification = new FindHotelSpecification();
        fillHotel(hotel);
    }

    private void fillHotel(Hotel hotel) throws SQLException, MalformedURLException {
        hotel.setName("Marriott");
        hotel.setHotelId(1L);
        hotel.setStars(5);
        hotel.setWebsite(new URL("https://www.marriott.com"));
        hotel.setCoordinate(PGgeometry.geomFromString("SRID=4326;POINT(37.617635 55.755814 42.419420)"));
        hotel.setFeatures(new Feature[]{Feature.AIR_CONDITIONER, Feature.CAR_RENTAL});
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
    public void update_hotel_by_updateHotelSpecification() throws MalformedURLException, SQLException {
        addHotel(hotel);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setName("Hilton");
        hotel.setStars(3);
        hotel.setWebsite(new URL("http://hilton.com"));
        hotel.setCoordinate(PGgeometry.geomFromString("SRID=4326;POINT(42.419420 69.666139 27.337318)"));
        hotel.setFeatures(new Feature[]{Feature.CABLE_TV, Feature.PARKING});
        updateHotelSpecification.setHotel(hotel);
        hotelRepository.update(updateHotelSpecification);
        removeHotel(hotel);
    }

    @Test
    public void remove_hotel_by_removeHotelSpecification() {
        removeHotel(hotel);
    }

    @Test
    public void query_hotel_by_findHotelSpecification() {
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
