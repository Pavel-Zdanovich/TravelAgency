package com.epam.travelAgency.service.impl;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.TourType;
import com.epam.travelAgency.repository.impl.TourRepository;
import com.epam.travelAgency.service.Service;
import com.epam.travelAgency.specification.impl.tour.*;
import org.postgresql.util.PGmoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@org.springframework.stereotype.Service
@Transactional
public class TourService implements Service<Tour> {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public Collection<Tour> findAll() {
        FindAllToursSpecification findAllToursSpecification = new FindAllToursSpecification();
        return tourRepository.query(findAllToursSpecification);
    }

    @Override
    public Tour findById(long tourId) {
        FindTourByIdSpecification findTourByIdSpecification = new FindTourByIdSpecification(tourId);
        return tourRepository.query(findTourByIdSpecification).iterator().next();
    }

    @Override
    public void update(Tour tour) {
        UpdateTourSpecification updateTourSpecification = new UpdateTourSpecification(tour);
        tourRepository.update(updateTourSpecification);
    }

    @Override
    public void save(Tour tour) {
        AddTourSpecification addTourSpecification = new AddTourSpecification(tour);
        tourRepository.add(addTourSpecification);
    }

    @Override
    public void delete(Tour tour) {
        RemoveTourSpecification removeTourSpecification = new RemoveTourSpecification(tour);
        tourRepository.remove(removeTourSpecification);
    }

    @Override
    public void deleteById(long tourId) {
        RemoveTourByIdSpecification removeTourByIdSpecification = new RemoveTourByIdSpecification(tourId);
        tourRepository.remove(removeTourByIdSpecification);
    }

    @Override
    public Tour mapRow(ResultSet resultSet, int i) throws SQLException {
        Tour tour = new Tour();
        tour.setTourId(resultSet.getLong("tour_id"));
        tour.setPhotoPath(resultSet.getString("photo"));
        tour.setStartDate(resultSet.getTimestamp("start_date"));
        tour.setEndDate(resultSet.getTimestamp("end_date"));
        tour.setDescription(resultSet.getString("description"));
        tour.setCost(new PGmoney((double) resultSet.getObject("cost")));
        tour.setTourType(TourType.getTourType(resultSet.getString("tour_type")));
        tour.getHotel().setHotelId(resultSet.getLong("hotel_id"));
        tour.getCountry().setCountryId(resultSet.getLong("country_id"));
        return tour;
    }

}