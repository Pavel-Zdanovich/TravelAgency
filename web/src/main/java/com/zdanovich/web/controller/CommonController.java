package com.zdanovich.web.controller;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.repository.CommonRepository;
import com.zdanovich.core.service.CommonService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface CommonController<E extends AbstractEntity, ID extends Serializable, R extends CommonRepository<E, ID>,
        S extends CommonService<E, ID, R>> {

    ResponseEntity save(E entity);
    ResponseEntity findAll();
    ResponseEntity findById(ID entityId);
    ResponseEntity update(E entity);
    ResponseEntity delete(E entity);

}
