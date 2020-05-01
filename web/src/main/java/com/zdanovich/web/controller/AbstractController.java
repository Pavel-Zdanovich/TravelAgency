package com.zdanovich.web.controller;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.repository.CommonRepository;
import com.zdanovich.core.service.CommonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractController<E extends AbstractEntity, ID extends Serializable,
        R extends CommonRepository<E, ID>, S extends CommonService<E, ID, R>> implements CommonController<E, ID, R, S> {

    protected final S service;

    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<E> save(@RequestBody E entity) {
        E savedEntity = this.service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<E>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @Override
    @GetMapping(params = "id")
    public ResponseEntity<E> findById(@RequestParam ID id) {
        return ResponseEntity.of(this.service.findById(id));
    }

    @Override
    @PutMapping
    public ResponseEntity<E> update(@RequestBody E entity) {
        if (this.service.existsById((ID) entity.getId())) {
            E updatedEntity = this.service.save(entity);
            return ResponseEntity.ok(updatedEntity);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping
    public ResponseEntity<E> delete(@RequestBody E entity) {
        if (this.service.existsById((ID) entity.getId())) {
            this.service.delete(entity);
            return ResponseEntity.ok(entity);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.NOT_FOUND);
        }
    }
}