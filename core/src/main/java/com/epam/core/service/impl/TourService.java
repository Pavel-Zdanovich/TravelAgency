package com.epam.core.service.impl;

import com.epam.core.config.DataSourceConfig;
import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.Tour;
import com.epam.core.entity.enums.TourType;
import com.epam.core.entity.metamodel.Tour_;
import com.epam.core.repository.TourRepository;
import com.epam.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

@Service
@Profile(value = "dev")
public class TourService extends AbstractService<Tour, Long, TourRepository> {

    @Autowired
    public TourService(TourRepository repository) {
        super(repository);
    }

    public List<Tour> findByStartDate(Timestamp startDate) {
        return repository.findByStartDate(startDate);
    }

    public List<Tour> findByStartDateGreaterThanEqual(Timestamp startDate) {
        return repository.findByStartDateGreaterThanEqual(startDate);
    }

    public List<Tour> findByStartDateLessThanEqual(Timestamp startDate) {
        return repository.findByStartDateLessThanEqual(startDate);
    }

    public List<Tour> findByEndDate(Timestamp endDate) {
        return repository.findByEndDate(endDate);
    }

    public List<Tour> findByEndDateGreaterThanEqual(Timestamp endDate) {
        return repository.findByEndDateGreaterThanEqual(endDate);
    }

    public List<Tour> findByEndDateLessThanEqual(Timestamp endDate) {
        return repository.findByEndDateLessThanEqual(endDate);
    }

    public List<Tour> findByStartDateAndEndDate(Timestamp startDate, Timestamp endDate) {
        return repository.findByStartDateAndEndDate(startDate, endDate);
    }

    public List<Tour> findByDuration(int numberOfDays) {
        Specification<Tour> tourSpecification = new Specification<Tour>() {
            @Override
            public Predicate toPredicate(Root<Tour> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Expression<Integer> timestampdiff = criteriaBuilder.diff(root.get(Tour_.END_DATE), root.get(Tour_.START_DATE));
                return criteriaBuilder.equal(timestampdiff, numberOfDays);
            }
        };
        return repository.findAll(tourSpecification);
    }

    public List<Tour> findByDescription(String description) {
        return repository.findByDescription(description);
    }

    public List<Tour> findByDescriptionLike(String descriptionRegex) {
        return repository.findByDescriptionLike(descriptionRegex);
    }

    public List<Tour> findByDescriptionContaining(String description) {
        return repository.findByDescriptionContaining(description);
    }

    public List<Tour> findByCost(BigDecimal cost) {
        return repository.findByCost(cost);
    }

    public List<Tour> findByCostGreaterThanEqual(BigDecimal cost) {
        return repository.findByCostGreaterThanEqual(cost);
    }

    public List<Tour> findByCostLessThanEqual(BigDecimal cost) {
        return repository.findByCostLessThanEqual(cost);
    }

    public List<Tour> findByCostBetween(BigDecimal minCost, BigDecimal maxCost) {
        return repository.findByCostBetween(minCost, maxCost);
    }

    public List<Tour> findByTourType(String tourType) {
        return repository.findByTourType(tourType);
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev", "oracle");
        context.register(DataSourceConfig.class, PersistenceConfig.class, TourRepository.class, TourService.class);
        context.refresh();
        TourService tourService = context.getBean(TourService.class);
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setPhotoPath("src/main/resources/oracle.properties");
        tour.setStartDate(Timestamp.valueOf("2020-12-11 00:00:00"));
        tour.setEndDate(Timestamp.valueOf("2020-12-12 00:00:00"));
        tour.setDescription("Description");
        tour.setCost(BigDecimal.valueOf(333).setScale(4, RoundingMode.HALF_UP));
        tour.setTourType(TourType.TOURISM);
        tourService.save(tour);
        Tour foundTour = tourService.findByDuration(1).stream().findFirst().orElse(null);
        System.out.println("Tour : " + foundTour);

    }
}
