package com.epam.web.controller.impl;

import com.epam.core.entity.Country;
import com.epam.core.repository.CountryRepository;
import com.epam.core.service.impl.CountryService;
import com.epam.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class CountryController extends AbstractController<Country, Long, CountryRepository, CountryService> {

    @Autowired
    public CountryController(CountryService service) {
        super(service);
    }

    public Optional<Country> findByName(String name) {
        return service.findByName(name);
    }

    public Optional<Country> findById(Long entityId) {
        return service.findById(entityId);
    }

    public long count() {
        return service.count();
    }

    public void deleteById(Long entityId) {
        service.deleteById(entityId);
    }
}
