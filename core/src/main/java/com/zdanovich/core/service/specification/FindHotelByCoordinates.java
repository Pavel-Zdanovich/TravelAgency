package com.zdanovich.core.service.specification;

import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.metamodel.Hotel_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FindHotelByCoordinates implements Specification<Hotel> {

    private final BigDecimal minLatitude;
    private final BigDecimal maxLatitude;
    private final BigDecimal minLongitude;
    private final BigDecimal maxLongitude;

    public FindHotelByCoordinates(@NotNull @DecimalMin(value = "-90.0000000", message = "{hotel.latitude.min}") BigDecimal minLatitude,
                                  @NotNull @DecimalMax(value = "90.0000000", message = "{hotel.latitude.max}") BigDecimal maxLatitude,
                                  @NotNull @DecimalMin(value = "-180.0000000", message = "{hotel.longitude.min}") BigDecimal minLongitude,
                                  @NotNull @DecimalMax(value = "180.0000000", message = "{hotel.longitude.max}") BigDecimal maxLongitude) {
        this.maxLatitude = maxLatitude;
        this.minLatitude = minLatitude;
        this.maxLongitude = maxLongitude;
        this.minLongitude = minLongitude;
    }

    public FindHotelByCoordinates(@NotNull
                                  @DecimalMin(value = "-90.0000000", message = "{hotel.latitude.min}")
                                  @DecimalMax(value = "90.0000000", message = "{hotel.latitude.max}")
                                          BigDecimal latitude,
                                  @NotNull
                                  @DecimalMin(value = "-180.0000000", message = "{hotel.longitude.min}")
                                  @DecimalMax(value = "180.0000000", message = "{hotel.longitude.max}")
                                          BigDecimal longitude) {
        this.minLatitude = latitude;
        this.maxLatitude = latitude;
        this.minLongitude = longitude;
        this.maxLongitude = longitude;
    }

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<BigDecimal> latitude = root.get(Hotel_.LATITUDE);
        Path<BigDecimal> longitude = root.get(Hotel_.LONGITUDE);
        Predicate latitudeBetween = criteriaBuilder.between(latitude, this.minLatitude, this.maxLatitude);
        Predicate longitudeBetween = criteriaBuilder.between(longitude, this.minLongitude, this.maxLongitude);
        return criteriaBuilder.and(latitudeBetween, longitudeBetween);
    }
}
