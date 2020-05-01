package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.metamodel.Hotel_;
import com.zdanovich.core.repository.HotelRepository;
import com.zdanovich.core.service.AbstractService;
import com.zdanovich.core.service.specification.FindHotelByCountry;
import com.zdanovich.core.service.specification.FindHotelByFeatureNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HotelService extends AbstractService<Hotel, Long, HotelRepository> {

    @Autowired
    public HotelService(HotelRepository repository) {
        super(repository);
    }

    public Optional<Hotel> findByName(String name) {
        return super.repository.findByName(name);
    }

    public List<Hotel> findByStars(Short stars) {
        return repository.findByStars(stars);
    }

    public List<Hotel> findByStarsLessThanEqual(Short stars) {
        return repository.findByStarsLessThanEqual(stars);
    }

    public List<Hotel> findByStarsGreaterThanEqual(Short stars) {
        return repository.findByStarsGreaterThanEqual(stars);
    }

    public List<Hotel> findByStarsRange(Short minStars, Short maxStars) {
        return repository.findByStarsBetween(minStars, maxStars);
    }

    public List<Hotel> findByArea(@DecimalMin("-90.0000000") BigDecimal minLatitude, @DecimalMax("90.0000000") BigDecimal maxLatitude,
                                  @DecimalMin("-180.0000000") BigDecimal minLongitude, @DecimalMax("180.0000000") BigDecimal maxLongitude) {
        Specification<Hotel> hotelSpecification = new Specification<Hotel>() {
            @Override
            public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate latitudePredicate = criteriaBuilder.between(root.get(Hotel_.LATITUDE), minLatitude, maxLatitude);
                Predicate longitudePredicate = criteriaBuilder.between(root.get(Hotel_.LONGITUDE), minLongitude, maxLongitude);
                return criteriaBuilder.and(latitudePredicate, longitudePredicate);
            }
        };
        return repository.findAll(hotelSpecification);
    }

//    SELECT H.HOTEL_ID, H.NAME, H.WEBSITE, H.STARS, H.LATITUDE, H.LONGITUDE FROM HOTELS H
//    INNER JOIN HOTELS_FEATURES HF ON H.HOTEL_ID = HF.HOTEL_ID
//    INNER JOIN FEATURES F ON HF.FEATURE_ID = F.FEATURE_ID
//    WHERE F.NAME IN ('air conditioner', 'car rental')
//    GROUP BY (H.HOTEL_ID, H.NAME, H.WEBSITE, H.STARS, H.LATITUDE, H.LONGITUDE) HAVING COUNT(H.HOTEL_ID) = 2;
    public List<Hotel> findByFeatures(Set<String> featureNames) {
        Specification<Hotel> hotelSpecification = new FindHotelByFeatureNames(featureNames);
        return repository.findAll(hotelSpecification);
    }

    public List<Hotel> findByCountry(Country country) {
        return repository.findByTours_Country(country);
    }

    public List<Hotel> findByCountry(String countryName) {
        Specification<Hotel> hotelSpecification = new FindHotelByCountry(countryName);
        return repository.findAll(hotelSpecification);
    }

}
