package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.service.impl.HotelService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class HotelController extends AbstractController<Hotel, Long, HotelRepository, HotelService> {

    @Autowired
    protected HotelController(HotelService service) {
        super(service);
    }

    public Optional<Hotel> findByName(String name) {
        return service.findByName(name);
    }

    public List<Hotel> findByStars(Short stars) {
        return service.findByStars(stars);
    }

    public List<Hotel> findByArea(@DecimalMin("-90.0000000") BigDecimal minLatitude, @DecimalMax("90.0000000") BigDecimal maxLatitude, @DecimalMin("-180.0000000") BigDecimal minLongitude, @DecimalMax("180.0000000") BigDecimal maxLongitude) {
        return service.findByArea(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    public List<Hotel> findByFeatures(Set<String> featureNames) {
        return service.findByFeatures(featureNames);
    }

    public List<Hotel> findByCountry(Country country) {
        return service.findByCountry(country);
    }

    public Optional<Hotel> findById(Long entityId) {
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
