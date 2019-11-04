package com.epam.core.service.impl;

import com.epam.core.config.DataSourceConfig;
import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.Country;
import com.epam.core.entity.Feature;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.Tour;
import com.epam.core.entity.metamodel.Country_;
import com.epam.core.entity.metamodel.Feature_;
import com.epam.core.entity.metamodel.Hotel_;
import com.epam.core.entity.metamodel.Tour_;
import com.epam.core.repository.HotelRepository;
import com.epam.core.service.AbstractService;
import com.epam.core.service.specification.FindHotelByCountry;
import com.epam.core.service.specification.FindHotelByFeatureNamesSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Profile(value = "dev")
public class HotelService extends AbstractService<Hotel, Long, HotelRepository> {

    @Autowired
    private EntityManager entityManager;

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
        Specification<Hotel> hotelSpecification = new FindHotelByFeatureNamesSpecification(featureNames);
        return repository.findAll(hotelSpecification);
    }

    public List<Hotel> findByCountry(Country country) {
        return repository.findByTours_Country(country);
    }

    public List<Hotel> findByCountry(String countryName) {
        Specification<Hotel> hotelSpecification = new FindHotelByCountry(countryName);
        return repository.findAll(hotelSpecification);
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev", "oracle");
        context.register(DataSourceConfig.class, PersistenceConfig.class, HotelRepository.class, HotelService.class);
        context.refresh();
        HotelService hotelService = context.getBean(HotelService.class);
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Belmond");
        hotel.setWebsite("https://www.belmond.com");
        hotel.setStars((short) 4);
        hotel.setLatitude(BigDecimal.valueOf(-89).setScale(7, RoundingMode.HALF_UP));
        hotel.setLongitude(BigDecimal.valueOf(-179).setScale(7, RoundingMode.HALF_UP));
        Feature feature1 = new Feature();
        feature1.setId(20L);
        feature1.setName("car rental");
        Feature feature2 = new Feature();
        feature2.setId(19L);
        feature2.setName("cable TV");
        //hotel.addFeature(feature1);
        //hotel.addFeature(feature2);
        //hotelService.save(hotel);
        Set<String> featureNames = new HashSet<>();
        featureNames.add("car rental");
        featureNames.add("air conditioner");
        List<Hotel> hotels = hotelService.findByFeatures(featureNames);
        System.out.println("Hotel : " + hotels);

    }
}
