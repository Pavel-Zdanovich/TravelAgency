package com.epam.web.controller;

import com.epam.core.entity.AbstractEntity;
import com.epam.core.repository.CommonRepository;
import com.epam.core.service.CommonService;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;

public interface CommonController<E extends AbstractEntity, ID extends Serializable, R extends CommonRepository<E, ID>,
        S extends CommonService<E, ID, R>> {

    ModelAndView list();
    ModelAndView take(ID entityId);
    ModelAndView save(E entity);
    ModelAndView update(E entity);
    ModelAndView delete(E entity);

}
