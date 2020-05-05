package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.repository.FeatureRepository;
import com.zdanovich.core.service.impl.FeatureService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(path = FeatureController.PATH)
public class FeatureController extends AbstractController<Feature, Long, FeatureRepository, FeatureService> {

    public static final String PATH = "/features";

    @Autowired
    public FeatureController(FeatureService featureService) {
        super(featureService);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Feature> findByName(
            @RequestParam
            @NotEmpty(message = "{feature.name.notEmpty}")
            @Size(min = 3, max = 30, message = "{feature.name.size}") String name) {
        return ResponseEntity.of(this.service.findByName(name));
    }
}