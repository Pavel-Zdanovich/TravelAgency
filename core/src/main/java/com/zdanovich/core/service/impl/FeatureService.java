package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.repository.FeatureRepository;
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
public class FeatureService extends AbstractService<Long, Feature, FeatureRepository> {

    @Autowired
    public FeatureService(FeatureRepository repository) {
        super(repository);
    }

    public Optional<Feature> findByName(String name) {
        return this.repository.findByName(name);
    }

    public Page<Feature> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public List<Feature> findAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    public List<Feature> findAll() {
        return this.repository.findAll();
    }
}
