package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.enums.TourType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TourRepository extends CommonRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {

    List<Tour> findByStartDate(Timestamp startDate);
    List<Tour> findByStartDateGreaterThanEqual(Timestamp startDate);
    List<Tour> findByStartDateLessThanEqual(Timestamp startDate);
    List<Tour> findByEndDate(Timestamp endDate);
    List<Tour> findByEndDateGreaterThanEqual(Timestamp endDate);
    List<Tour> findByEndDateLessThanEqual(Timestamp endDate);
    List<Tour> findByStartDateAndEndDate(Timestamp startDate, Timestamp endDate);
    List<Tour> findByDescription(String description);
    List<Tour> findByDescriptionLike(String descriptionRegex);
    List<Tour> findByDescriptionContaining(String description);
    List<Tour> findByCost(BigDecimal cost);
    List<Tour> findByCostGreaterThanEqual(BigDecimal cost);
    List<Tour> findByCostLessThanEqual(BigDecimal cost);
    List<Tour> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);
    List<Tour> findByTourType(TourType tourType);

}
