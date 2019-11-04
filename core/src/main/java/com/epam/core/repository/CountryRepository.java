package com.epam.core.repository;

import com.epam.core.entity.Country;
import com.epam.core.entity.Tour;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile(value = "dev")
@Transactional
public interface CountryRepository extends CommonRepository<Country, Long> {

    Optional<Country> findByName(String name);

}
