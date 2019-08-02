package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindAllHotelsSpecification implements FindSpecification<Hotel, Object> {

    public static final String SELECT_ALL_HOTELS = "SELECT * FROM hotels";

    @Override
    public void setSpecification(Object specification) {
        //TODO
    }

    @Override
    public Object getSpecification() {
        return null;//TODO
    }

    @Override
    public String getSQLQuery() {
        return SELECT_ALL_HOTELS;
    }

    @Override
    public CriteriaQuery<Hotel> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = criteriaBuilder.createQuery(Hotel.class);
        Root<Hotel> root = criteriaQuery.from(Hotel.class);
        criteriaQuery.select(root);
        return criteriaQuery;
    }
}
