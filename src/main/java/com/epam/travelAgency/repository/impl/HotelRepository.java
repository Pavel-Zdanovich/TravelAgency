package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.pool.ConnectionPool;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.*;
import org.postgis.PGgeometry;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@org.springframework.stereotype.Repository
public class HotelRepository implements Repository<Hotel> {

    private static final Logger logger = LoggerFactory.getLogger(HotelRepository.class);
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public HotelRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(AddSpecification<Hotel> specification) {
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
    public void update(UpdateSpecification<Hotel> specification) {
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
    public void remove(RemoveSpecification<Hotel> specification) {
        Collection<Hotel> removedHotels = new ArrayList<>();
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
    public Collection<Hotel> query(FindSpecification<Hotel, Object> specification) {
        Collection<Hotel> selectedHotels = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(specification.getSQLQuery())) {
            while (resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.setHotelId(resultSet.getLong(1));
                hotel.setName(resultSet.getString(2));
                hotel.setStars(resultSet.getInt(3));
                hotel.setWebsite((PGobject) resultSet.getObject(4));
                hotel.setCoordinate((PGgeometry) resultSet.getObject(5));
                hotel.setFeature(Hotel.Feature.valueOf(resultSet.getString(6)));
                selectedHotels.add(hotel);
            }
        } catch (SQLException e) {
            logger.error("Execute SQL query error");
        }
        return selectedHotels;
    }

}
