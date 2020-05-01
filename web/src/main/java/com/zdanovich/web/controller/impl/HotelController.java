package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.service.impl.HotelService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = HotelController.PATH)
public class HotelController extends AbstractController<Hotel, Long, HotelRepository, HotelService> {

    public static final String PATH = "/hotels";

    @Autowired
    protected HotelController(HotelService hotelService) {
        super(hotelService);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Hotel> findByName(
            @RequestParam
            @NotEmpty(message = "{hotel.name.notEmpty}")
            @Size(min = 2, max = 50, message = "{hotel.name.size}") String name) {
        return ResponseEntity.of(service.findByName(name));
    }

    @GetMapping(params = "stars")
    public ResponseEntity<List<Hotel>> findByStars(
            @RequestParam
            @NotNull(message = "{hotel.stars.notNull}")
            @Min(value = 1, message = "{hotel.stars.min}")
            @Max(value = 5, message = "{hotel.stars.max}") Short stars) {
        return ResponseEntity.ok(service.findByStars(stars));
    }

    @GetMapping(params = { "minLatitude", "maxLatitude", "minLongitude", "maxLongitude" })
    public ResponseEntity<List<Hotel>> findByArea(
            @RequestParam @DecimalMin("-90.0000000") BigDecimal minLatitude,
            @RequestParam @DecimalMax("90.0000000") BigDecimal maxLatitude,
            @RequestParam @DecimalMin("-180.0000000") BigDecimal minLongitude,
            @RequestParam @DecimalMax("180.0000000") BigDecimal maxLongitude) {
        return ResponseEntity.ok(service.findByArea(minLatitude, maxLatitude, minLongitude, maxLongitude));
    }


    @GetMapping(params = "features")
    public ResponseEntity<List<Hotel>> findByFeatures(
            @RequestParam
            @NotEmpty(message = "{hotel.name.notEmpty}")
            @Size(min = 2, max = 50, message = "{hotel.name.size}") Set<String> features) {
        return ResponseEntity.ok(service.findByFeatures(features));
    }

    /*@GetMapping
    public ResponseEntity<List<Hotel>> findByCountry(
            @RequestBody
            @Valid Country country) {
        return ResponseEntity.ok(service.findByCountry(country));
    }*/

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
    public ResponseEntity<Iterable<Hotel>> deleteAll(
            @RequestBody
            @Valid Iterable<Hotel> iterable) {
        service.deleteAll(iterable);
        return ResponseEntity.ok(iterable);
    }*/
}