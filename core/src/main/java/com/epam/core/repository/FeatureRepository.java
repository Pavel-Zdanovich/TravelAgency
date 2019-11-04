package com.epam.core.repository;

import com.epam.core.entity.Feature;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
@Profile(value = "dev")
@Transactional
public interface FeatureRepository extends CommonRepository<Feature, Long> {

    Optional<Feature> findByName(@NotEmpty String name);

}
