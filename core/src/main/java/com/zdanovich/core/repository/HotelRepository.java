package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends CommonRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    Optional<Hotel> findByName(String name);

    List<Hotel> findByStars(Short stars);

    List<Hotel> findByStarsLessThanEqual(Short stars);

    List<Hotel> findByStarsGreaterThanEqual(Short stars);

    List<Hotel> findByStarsBetween(Short minStars, Short maxStars);

    List<Hotel> findByFeatures_Name(String featureName);

    List<Hotel> findByTours_Country(Country country);

}
