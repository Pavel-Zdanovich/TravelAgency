package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;

@Component
public class FindHotelSpecification implements FindSpecification<Hotel, Hotel> {

    public static final String SELECT_HOTEL = "SELECT * FROM hotels WHERE hotel_id = %d AND name = '%s' AND stars = %d AND website = '%s' AND coordinate = '%s' AND features = '%s'";
    @Autowired
    private Hotel hotel;

    public FindHotelSpecification() {}

    public FindHotelSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void setSpecification(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public Hotel getSpecification() {
        return this.hotel;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_HOTEL, hotel.getHotelId(), hotel.getName(), hotel.getStars(), hotel.getWebsite().toString(),
                hotel.getCoordinate(), featuresToString(hotel.getFeatures()));
    }

    @Override
    public CriteriaQuery<Hotel> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = criteriaBuilder.createQuery(Hotel.class);
        Root<Hotel> root = criteriaQuery.from(Hotel.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("hotel_id"), hotel.getHotelId()),
                criteriaBuilder.equal(root.get("name"), hotel.getName()),
                criteriaBuilder.equal(root.get("website"), hotel.getWebsite().toString()),
                criteriaBuilder.equal(root.get("coordinate"), hotel.getCoordinate()),
                criteriaBuilder.equal(root.get("feature"), featuresToString(hotel.getFeatures())));
        return criteriaQuery;
    }

    private String featuresToString(Feature[] array) {
        StringBuilder stringBuilder = new StringBuilder("{");
        Arrays.stream(array).forEach(feature -> stringBuilder.append(feature.toString()).append(","));
        return stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")).append("}").toString();
    }

}
