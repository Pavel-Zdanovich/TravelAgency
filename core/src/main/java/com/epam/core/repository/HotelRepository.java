package com.epam.core.repository;

import com.epam.core.entity.Country;
import com.epam.core.entity.Feature;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.Tour;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Profile(value = "dev")
@Transactional
public interface HotelRepository extends CommonRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    Optional<Hotel> findByName(String name);
    List<Hotel> findByStars(Short stars);
    List<Hotel> findByStarsLessThanEqual(Short stars);
    List<Hotel> findByStarsGreaterThanEqual(Short stars);
    List<Hotel> findByStarsBetween(Short minStars, Short maxStars);
    List<Hotel> findByFeatures_Name(String featureName);
    List<Hotel> findByTours_Country(Country country);

}
