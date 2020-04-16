package com.zdanovich.core.service.impl;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.repository.FeatureRepository;
import com.zdanovich.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService extends AbstractService<Feature, Long, FeatureRepository> {

    @Autowired
    public FeatureService(FeatureRepository repository) {
        super(repository);
    }

    public Optional<Feature> findByName(String name) {
        return repository.findByName(name);
    }

}
