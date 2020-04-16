package com.zdanovich.web.controller;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.repository.CommonRepository;
import com.zdanovich.core.service.CommonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractController<E extends AbstractEntity, ID extends Serializable,
        R extends CommonRepository<E, ID>, S extends CommonService<E, ID, R>> implements CommonController<E, ID, R, S> {

    protected final S service;

    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("viewName");
        List<E> entities = this.service.findAll();
        modelAndView.addObject("entities", entities);
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @Override
    public ModelAndView take(ID entityId) {
        ModelAndView modelAndView = new ModelAndView("viewName");
        Optional<E> foundEntity = this.service.findById(entityId);
        if (foundEntity.isPresent()) {
            modelAndView.addObject("foundEntity", foundEntity.get());
            modelAndView.setStatus(HttpStatus.FOUND);
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;
    }

    @Override
    public ModelAndView save(E entity) {
        ModelAndView modelAndView = new ModelAndView("viewName");
        if (this.service.save(entity).equals(entity)) {
            modelAndView.addObject("entity", entity);
            modelAndView.setStatus(HttpStatus.CREATED);
        } else {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }
        return modelAndView;
    }

    @Override
    public ModelAndView update(E entity) {
        ModelAndView modelAndView = new ModelAndView("viewName");
        Optional<E> foundEntity = this.service.findById((ID) entity.getId());
        if (foundEntity.isPresent()) {
            E updatedEntity = this.service.save(entity);
            if (updatedEntity.equals(entity)) {
                modelAndView.addObject("updatedEntity", updatedEntity);
                modelAndView.setStatus(HttpStatus.OK);
            } else {
                modelAndView.addObject("foundEntity", foundEntity.get());
                modelAndView.setStatus(HttpStatus.NOT_MODIFIED);
            }
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;
    }

    @Override
    public ModelAndView delete(E entity) {
        ModelAndView modelAndView = new ModelAndView("viewName");
        Optional<E> foundEntity = this.service.findById((ID) entity.getId());
        if (foundEntity.isPresent()) {
            this.service.delete(entity);
            modelAndView.addObject("foundEntity", foundEntity.get());
            modelAndView.setStatus(HttpStatus.OK);
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;
    }
}
