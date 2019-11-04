package com.epam.web.controller.impl;

import com.epam.core.entity.Feature;
import com.epam.core.repository.FeatureRepository;
import com.epam.core.service.impl.FeatureService;
import com.epam.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class FeatureController extends AbstractController<Feature, Long, FeatureRepository, FeatureService> {

    @Autowired
    public FeatureController(FeatureService service) {
        super(service);
    }

    public Optional<Feature> findByName(String name) {
        return service.findByName(name);
    }

    public Optional<Feature> findById(Long entityId) {
        return service.findById(entityId);
    }

    public boolean existsById(Long entityId) {
        return service.existsById(entityId);
    }

    public long count() {
        return service.count();
    }

    public void deleteById(Long entityId) {
        service.deleteById(entityId);
    }
}
