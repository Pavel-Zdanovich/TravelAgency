package com.zdanovich.core.repository;

import com.zdanovich.core.entity.Country;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends CommonRepository<Country, Long> {

    Optional<Country> findByName(String name);

}
