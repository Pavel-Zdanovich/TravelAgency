package com.epam.core.service.impl;

import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.Feature;
import com.epam.core.repository.FeatureRepository;
import com.epam.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
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

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev", "oracle");
        context.register(PersistenceConfig.class, FeatureRepository.class, FeatureService.class);
        context.refresh();
        FeatureService featureService = context.getBean(FeatureService.class);
        Feature feature = new Feature();
        feature.setId(3L);
        feature.setName("mini-bar");
        featureService.save(feature);
        Feature foundFeature = featureService.findByName(feature.getName()).orElse(null);
        System.out.println("Feature : " + foundFeature);

    }
}
