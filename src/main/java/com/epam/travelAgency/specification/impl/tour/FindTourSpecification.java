package com.epam.travelAgency.specification.impl.tour;

import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.specification.FindSpecification;
import org.hibernate.Session;
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
                tour.getEndDate(), tour.getDescription(), tour.getCost().val,  tour.getTourType(), tour.getCountry().getCountryId(), tour.getHotel().getHotelId());
    }

    @Override
    public CriteriaQuery<Tour> toCriteriaQuery(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour_id"), tour.getTourId()),
                criteriaBuilder.equal(root.get("photo"), tour.getPhotoPath()),
                criteriaBuilder.equal(root.get("start_date"), tour.getStartDate()),
                criteriaBuilder.equal(root.get("end_date"), tour.getEndDate()),
                criteriaBuilder.equal(root.get("description"), tour.getDescription()),
                criteriaBuilder.equal(root.get("cost"), tour.getCost()),
                criteriaBuilder.equal(root.get("tour_type"), tour.getTourType()),
                criteriaBuilder.equal(root.get("country_id"), tour.getCountry().getCountryId()),
                criteriaBuilder.equal(root.get("hotel_id"), tour.getHotel().getHotelId()));
        return criteriaQuery;
    }
}
