package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryService extends AbstractService<Long, Country, CountryRepository> {

    @Autowired
    public CountryService(CountryRepository repository) {
        super(repository);
    }

    public Optional<Country> findByName(String name) {
        return this.repository.findByName(name);
    }

    public Page<Country> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public List<Country> findAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    public List<Country> findAll() {
        return this.repository.findAll();
    }
}
