package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Country;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.pool.ConnectionPool;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.*;
import org.postgresql.util.PGmoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class TourRepository implements Repository<Tour> {

    private static final Logger logger = LoggerFactory.getLogger(TourRepository.class);

    @Override
    public void add(AddSpecification<Tour> specification) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++){
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public void update(UpdateSpecification<Tour> specification) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++){
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public void remove(RemoveSpecification<Tour> specification) {
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(specification.getSQLQuery())) {
            int batches = specification.specified(preparedStatement);
            for (int i = 0; i < batches; i++){
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
    }

    @Override
    public Collection<Tour> query(FindSpecification<Tour, Object> specification) {
        Collection<Tour> selectedTours = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(specification.getSQLQuery())) {
            while (resultSet.next()) {//TODO services
                Tour tour = new Tour();
                tour.setTourId(resultSet.getLong(1));
                tour.setPhotoPath(convertBinaryStreamToFile(resultSet.getBinaryStream(2), String.valueOf(tour.getTourId())));
                tour.setStartDate(resultSet.getTimestamp(3));
                tour.setEndDate(resultSet.getTimestamp(4));
                tour.setDescription(resultSet.getString(5));
                tour.setCost(new PGmoney(resultSet.getDouble(6)));//TODO pgObject check
                tour.setType(Tour.defineType(resultSet.getString(7)));
                Country country = new Country();
                country.setCountryId(resultSet.getLong(8));
                country.setName(resultSet.getString("country.name"));//TODO nested query
                tour.setCountry(country);
                tour.setHotel(new Hotel());
                selectedTours.add(tour);
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
        return selectedTours;
    }

    private Path convertBinaryStreamToFile(InputStream inputStream, String fileName) {
        File file = new File(fileName);
        try {
            Files.copy(inputStream, file.toPath());
        } catch (IOException e) {
            logger.error("Reading file by stream error");
        }
        return file.toPath();
    }

}
