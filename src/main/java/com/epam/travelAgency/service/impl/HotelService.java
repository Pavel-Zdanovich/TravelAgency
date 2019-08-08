package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.service.Service;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.impl.hotel.*;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@org.springframework.stereotype.Service
@Transactional(transactionManager = "jpaTransactionManager")
public class HotelService implements Service<Hotel> {

    @Autowired
    private Repository<Hotel> hotelRepository;

    @Transactional
    @Override
    public Collection<Hotel> findAll() {
        FindSpecification<Hotel, Object> findSpecification = new FindAllHotelsSpecification();
        return hotelRepository.query(findSpecification);
    }

    @Transactional
    @Override
    public Hotel findById(long hotelId) {
        FindHotelByIdSpecification findHotelByIdSpecification = new FindHotelByIdSpecification(hotelId);
        return hotelRepository.query(findHotelByIdSpecification).iterator().next();
    }

    @Transactional
    @Override
    public void update(Hotel hotel) {
        UpdateHotelSpecification updateHotelSpecification = new UpdateHotelSpecification(hotel);
        hotelRepository.update(updateHotelSpecification);
    }

    @Transactional
    @Override
    public void save(Hotel hotel) {
        AddHotelSpecification addHotelSpecification = new AddHotelSpecification(hotel);
        hotelRepository.add(addHotelSpecification);
    }

    @Transactional
    @Override
    public void delete(Hotel hotel) {
        RemoveHotelSpecification removeHotelSpecification = new RemoveHotelSpecification(hotel);
        hotelRepository.remove(removeHotelSpecification);
    }

    @Transactional
    @Override
    public void deleteById(long hotelId) {
        RemoveHotelByIdSpecification removeHotelByIdSpecification = new RemoveHotelByIdSpecification(hotelId);
        hotelRepository.remove(removeHotelByIdSpecification);
    }

    @Override
    public Hotel mapRow(ResultSet resultSet, int i) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(resultSet.getLong("hotel_id"));
        hotel.setName(resultSet.getString("name"));
        hotel.setStars(resultSet.getInt("stars"));
        hotel.setWebsite(resultSet.getURL("website"));
        hotel.setCoordinate((PGgeometry) resultSet.getObject("coordinate"));
        Array array = resultSet.getArray("features");
        String[] stringFeatures = (String[]) array.getArray();
        Feature[] features = new Feature[stringFeatures.length];
        for (int j = 0; j < stringFeatures.length; j++) {
            features[j] = Feature.getFeature(stringFeatures[j]);
        }
        hotel.setFeatures(features);
        return hotel;
    }
}
