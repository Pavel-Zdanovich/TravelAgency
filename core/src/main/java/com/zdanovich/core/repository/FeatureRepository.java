package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Feature;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface FeatureRepository extends CommonRepository<Long, Feature> {

    Optional<Feature> findByName(@NotEmpty String name);

}
