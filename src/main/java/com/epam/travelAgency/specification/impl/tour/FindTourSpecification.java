package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.metamodel.Tour_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindTourSpecification implements FindSpecification<Tour, Tour> {

    public static final String SELECT_TOUR = "SELECT * FROM tours WHERE tour_id = %d AND photo = '%s' AND start_date = '%s' AND end_date = '%s' AND description = '%s' AND cost = '%s' AND tour_type = '%s' AND hotel_id = %d AND country_id = %d";
    @Autowired
    private Tour tour;

    public FindTourSpecification() {}

    public FindTourSpecification(Tour tour) {
        this.tour = tour;
    }

    @Override
    public void setSpecification(Tour tour) {
        this.tour = tour;
    }

    @Override
    public Tour getSpecification() {
        return this.tour;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_TOUR, tour.getTourId(), tour.getPhotoPath(), tour.getStartDate(),
                tour.getEndDate(), tour.getDescription(), tour.getCost(),  tour.getTourType(), tour.getCountry().getCountryId(), tour.getHotel().getHotelId());//can produce null pointer
    }

    @Override
    public CriteriaQuery<Tour> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        return criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Tour_.TOUR_ID), tour.getTourId()),
                criteriaBuilder.equal(root.get(Tour_.PHOTO_PATH), tour.getPhotoPath()),
                criteriaBuilder.equal(root.get(Tour_.START_DATE), tour.getStartDate()),
                criteriaBuilder.equal(root.get(Tour_.END_DATE), tour.getEndDate()),
                criteriaBuilder.equal(root.get(Tour_.DESCRIPTION), tour.getDescription()),
                criteriaBuilder.equal(root.get(Tour_.COST), tour.getCost()),
                criteriaBuilder.equal(root.get(Tour_.TOUR_TYPE), tour.getTourType()));//criteriaBuilder.equal(root.get(Tour_.COUNTRY).get(Country_.COUNTRY_ID), tour.getCountry().getCountryId()),
                                                                                      //criteriaBuilder.equal(root.get(Tour_.HOTEL).get(Hotel_.HOTEL_ID), tour.getHotel().getHotelId())
    }
}
