package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.repository.TourRepository;
import com.zdanovich.core.service.impl.TourService;
import com.zdanovich.web.controller.AbstractController;
import com.zdanovich.web.security.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = TourController.PATH)
public class TourController extends AbstractController<Long, Tour, TourRepository, TourService> {

    public static final String PATH = "/tours";

    @Autowired
    public TourController(TourService tourService) {
        super(tourService);
    }

    @GetMapping(params = "startDate")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByStartDate(
            @RequestParam
            @NotNull(message = "{tour.startDate.notNull}")
            @FutureOrPresent(message = "{tour.startDate.futureOrPresent}") Timestamp startDate) {
        return ResponseEntity.ok(this.service.findByStartDate(startDate));
    }

    @GetMapping(params = "endDate")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByEndDate(
            @RequestParam
            @NotNull(message = "{tour.endDate.notNull}")
            @Future(message = "{tour.endDate.future}") Timestamp endDate) {
        return ResponseEntity.ok(this.service.findByEndDate(endDate));
    }

    @GetMapping(params = "days")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByDuration(@RequestParam int days) {
        return ResponseEntity.ok(this.service.findByDuration(days));
    }

    @GetMapping(params = "description")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByDescription(
            @RequestParam
            @NotEmpty(message = "{tour.description.notEmpty}")
            @Size(max = 1000, message = "{tour.description.size}") String description) {
        return ResponseEntity.ok(this.service.findByDescription(description));
    }

    @GetMapping(params = "cost")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByCost(
            @RequestParam
            @NotNull(message = "{tour.cost.notNull}")
            @DecimalMin(value = "0.0000", message = "{tour.cost.min}") BigDecimal cost) {
        return ResponseEntity.ok(this.service.findByCost(cost));
    }

    @GetMapping(params = "type")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByTourType(@RequestParam TourType type) {
        return ResponseEntity.ok(this.service.findByTourType(type));
    }
}