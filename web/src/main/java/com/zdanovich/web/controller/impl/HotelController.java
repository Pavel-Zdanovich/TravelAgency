package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.service.impl.HotelService;
import com.zdanovich.web.controller.AbstractController;
import com.zdanovich.web.security.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class HotelController extends AbstractController<Long, Hotel, HotelRepository, HotelService> {

    public static final String PATH = "/hotels";

    @Autowired
    protected HotelController(HotelService hotelService) {
        super(hotelService);
    }

    @GetMapping(params = "name")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByName(
            @RequestParam
            @NotEmpty(message = "{hotel.name.notEmpty}")
            @Size(min = 2, max = 50, message = "{hotel.name.size}") String name) {
        return ResponseEntity.of(this.service.findByName(name));
    }

    @GetMapping(params = "stars")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByStars(
            @RequestParam
            @NotNull(message = "{hotel.stars.notNull}")
            @Min(value = 1, message = "{hotel.stars.min}")
            @Max(value = 5, message = "{hotel.stars.max}") Short stars) {
        return ResponseEntity.ok(this.service.findByStars(stars));
    }

    @GetMapping(params = {"minLatitude", "maxLatitude", "minLongitude", "maxLongitude"})
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByArea(
            @RequestParam @DecimalMin("-90.0000000") BigDecimal minLatitude,
            @RequestParam @DecimalMax("90.0000000") BigDecimal maxLatitude,
            @RequestParam @DecimalMin("-180.0000000") BigDecimal minLongitude,
            @RequestParam @DecimalMax("180.0000000") BigDecimal maxLongitude) {
        return ResponseEntity.ok(this.service.findByArea(minLatitude, maxLatitude, minLongitude, maxLongitude));
    }


    @GetMapping(params = "features")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByFeatures(
            @RequestParam
            @NotEmpty(message = "{hotel.name.notEmpty}")
            @Size(min = 2, max = 50, message = "{hotel.name.size}") Set<String> features) {
        return ResponseEntity.ok(this.service.findByFeatures(features));
    }

    @GetMapping(params = "countryName")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<?> findByCountry(@RequestParam String countryName) {
        return ResponseEntity.ok(this.service.findByCountry(countryName));
    }
}