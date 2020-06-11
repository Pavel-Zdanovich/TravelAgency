package com.zdanovich.web.controller;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.repository.CommonRepository;
import com.zdanovich.core.service.CommonService;
import com.zdanovich.web.security.Authorities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractController<E extends AbstractEntity, ID extends Serializable,
        R extends CommonRepository<E, ID>, S extends CommonService<E, ID, R>> implements CommonController<E, ID, R, S> {

    protected final S service;

    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    @PostMapping
    @PreAuthorize(value = "hasAuthority('" + Authorities.CREATE_PRIVILEGE + "')")
    public ResponseEntity<E> save(@RequestBody E entity) {
        E savedEntity = this.service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @Override
    @GetMapping
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<Iterable<E>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @Override
    @GetMapping(params = "id")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<E> findById(@RequestParam ID id) {
        return ResponseEntity.of(this.service.findById(id));
    }

    @Override
    @PutMapping
    @PreAuthorize(value = "hasAuthority('" + Authorities.UPDATE_PRIVILEGE + "')")
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
    @PreAuthorize(value = "hasAuthority('" + Authorities.DELETE_PRIVILEGE + "')")
    public ResponseEntity<E> delete(@RequestBody E entity) {
        if (this.service.existsById((ID) entity.getId())) {
            this.service.delete(entity);
            return ResponseEntity.ok(entity);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping(params = "id")
    @PreAuthorize(value = "hasAuthority('" + Authorities.DELETE_PRIVILEGE + "')")
    public ResponseEntity<E> deleteById(@RequestParam ID id) {
        Optional<E> optionalEntity = this.service.findById(id);
        if (optionalEntity.isPresent()) {
            this.service.deleteById(id);
            return ResponseEntity.ok(optionalEntity.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}