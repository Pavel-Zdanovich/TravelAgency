package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.repository.TourRepository;
import com.zdanovich.core.service.impl.TourService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class TourController extends AbstractController<Tour, Long, TourRepository, TourService> {

    public static final String PATH = "/tours";

    @Autowired
    public TourController(TourService tourService) {
        super(tourService);
    }

    @GetMapping(params = "startDate")
    public ResponseEntity<List<Tour>> findByStartDate(
            @RequestParam
            @NotNull(message = "{tour.startDate.notNull}")
            @FutureOrPresent(message = "{tour.startDate.futureOrPresent}") Timestamp startDate) {
        return ResponseEntity.ok(service.findByStartDate(startDate));
    }

    @GetMapping(params = "endDate")
    public ResponseEntity<List<Tour>> findByEndDate(
            @RequestParam
            @NotNull(message = "{tour.endDate.notNull}")
            @Future(message = "{tour.endDate.future}") Timestamp endDate) {
        return ResponseEntity.ok(service.findByEndDate(endDate));
    }

    @GetMapping(params = "days")
    public ResponseEntity<List<Tour>> findByDuration(@RequestParam int days) {
        return ResponseEntity.ok(service.findByDuration(days));
    }

    @GetMapping(params = "description")
    public ResponseEntity<List<Tour>> findByDescription(
            @RequestParam
            @NotEmpty(message = "{tour.description.notEmpty}")
            @Size(max = 1000, message = "{tour.description.size}") String description) {
        return ResponseEntity.ok(service.findByDescription(description));
    }

    @GetMapping(params = "cost")
    public ResponseEntity<List<Tour>> findByCost(
            @RequestParam
            @NotNull(message = "{tour.cost.notNull}")
            @DecimalMin(value = "0.0000", message = "{tour.cost.min}") BigDecimal cost) {
        return ResponseEntity.ok(service.findByCost(cost));
    }

    @GetMapping(params = "type")
    public ResponseEntity<List<Tour>> findByTourType(@RequestParam TourType type) {
        return ResponseEntity.ok(service.findByTourType(type));
    }

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Long> deleteById(
            @RequestParam
            @NotNull(message = "{entity.id.notNull}") Long id) {
        if (this.service.existsById(id)) {
            this.service.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    /*@DeleteMapping
    public ResponseEntity<Iterable<Tour>> deleteAll(
            @RequestBody
            @Valid Iterable<Tour> iterable) {
        service.deleteAll(iterable);
        return ResponseEntity.ok(iterable);
    }*/
}