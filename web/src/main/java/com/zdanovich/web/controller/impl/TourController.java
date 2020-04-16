package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.repository.TourRepository;
import com.zdanovich.core.service.impl.TourService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class TourController extends AbstractController<Tour, Long, TourRepository, TourService> {

    @Autowired
    public TourController(TourService service) {
        super(service);
    }

    public List<Tour> findByStartDate(Timestamp startDate) {
        return service.findByStartDate(startDate);
    }

    public List<Tour> findByEndDate(Timestamp endDate) {
        return service.findByEndDate(endDate);
    }

    public List<Tour> findByDuration(int numberOfDays) {
        return service.findByDuration(numberOfDays);
    }

    public List<Tour> findByDescription(String description) {
        return service.findByDescription(description);
    }

    public List<Tour> findByCost(BigDecimal cost) {
        return service.findByCost(cost);
    }

    public List<Tour> findByTourType(String tourType) {
        return service.findByTourType(tourType);
    }

    public Optional<Tour> findById(Long entityId) {
        return service.findById(entityId);
    }

    public boolean existsById(Long entityId) {
        return service.existsById(entityId);
    }

    public long count() {
        return service.count();
    }

    public void deleteById(Long entityId) {
        service.deleteById(entityId);
    }
}
