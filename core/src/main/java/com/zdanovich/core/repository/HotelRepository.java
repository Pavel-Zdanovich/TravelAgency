package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Hotel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends CommonRepository<Long, Hotel>, JpaSpecificationExecutor<Hotel> {

    Optional<Hotel> findByName(@NotEmpty String name);

    List<Hotel> findByStars(@NotNull Short stars);

    List<Hotel> findByStarsLessThanEqual(@NotNull Short stars);

    List<Hotel> findByStarsGreaterThanEqual(@NotNull Short stars);

    List<Hotel> findByStarsBetween(@NotNull Short minStars, @NotNull Short maxStars);

    List<Hotel> findByFeatures_Name(@NotEmpty String featureName);

}
