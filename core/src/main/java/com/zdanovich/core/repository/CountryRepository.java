package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Country;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface CountryRepository extends CommonRepository<Long, Country> {

    Optional<Country> findByName(@NotEmpty String name);

}
