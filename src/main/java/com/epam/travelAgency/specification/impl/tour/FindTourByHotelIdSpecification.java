package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Hotel_;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourByHotelIdSpecification implements FindSpecification<Tour, Long> {

    public static final String SELECT_TOUR_BY_HOTEL_ID = "SELECT * FROM tours WHERE hotel_id = %d";
    private Long hotelId;

    public FindTourByHotelIdSpecification() {
    }

    public FindTourByHotelIdSpecification(Long hotelId) {
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
        return String.format(SELECT_TOUR_BY_HOTEL_ID, this.hotelId);
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        return criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Tour_.HOTEL).get(Hotel_.HOTEL_ID), this.hotelId));
    }

}
