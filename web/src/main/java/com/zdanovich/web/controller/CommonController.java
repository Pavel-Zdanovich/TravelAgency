package com.zdanovich.web.controller;

import com.zdanovich.core.entity.generator.Identifiable;
import com.zdanovich.core.repository.CommonRepository;
import com.zdanovich.core.service.CommonService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface CommonController<ID extends Serializable, E extends Identifiable<ID>, R extends CommonRepository<ID, E>,
        S extends CommonService<ID, E, R>> {

    ResponseEntity<?> save(E entity);

    ResponseEntity<?> findById(ID entityId);

    ResponseEntity<?> update(E entity);

    ResponseEntity<?> deleteById(ID entityId);
}