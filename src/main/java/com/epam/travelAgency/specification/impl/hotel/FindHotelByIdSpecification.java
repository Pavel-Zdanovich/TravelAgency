package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindHotelByIdSpecification implements FindSpecification<Hotel, Long> {

    public static final String SELECT_HOTEL_BY_ID = "SELECT * FROM hotels WHERE hotel_id = %d";
    private Long hotelId;

    public FindHotelByIdSpecification() {
    }

    public FindHotelByIdSpecification(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public void setSpecification(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public Long getSpecification() {
        return this.hotelId;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_HOTEL_BY_ID, this.hotelId);
    }

    @Override
    public CriteriaQuery<Hotel> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = criteriaBuilder.createQuery(Hotel.class);
        Root<Hotel> root = criteriaQuery.from(Hotel.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("hotel_id"), this.hotelId));
        return criteriaQuery;
    }
}
