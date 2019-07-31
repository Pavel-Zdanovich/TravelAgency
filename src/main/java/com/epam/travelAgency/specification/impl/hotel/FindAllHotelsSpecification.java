package com.epam.travelAgency.specification.impl.hotel;

import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

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
}
