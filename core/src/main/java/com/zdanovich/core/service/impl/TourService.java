package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.entity.metamodel.Tour_;
import com.zdanovich.core.repository.TourRepository;
import com.zdanovich.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
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
        return findByTourType(TourType.valueOf(tourType));
    }

    public List<Tour> findByTourType(TourType tourType) {
        return repository.findByTourType(tourType);
    }
}
