package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService extends AbstractService<Country, Long, CountryRepository> {

    @Autowired
    public CountryService(CountryRepository repository) {
        super(repository);
    }

    public Optional<Country> findByName(String name) {
        return repository.findByName(name);
    }

}
