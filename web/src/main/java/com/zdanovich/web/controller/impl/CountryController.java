package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.service.impl.CountryService;
import com.zdanovich.web.controller.AbstractController;
import com.zdanovich.web.security.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(path = CountryController.PATH)
public class CountryController extends AbstractController<Country, Long, CountryRepository, CountryService> {

    public static final String PATH = "/countries";

    @Autowired
    public CountryController(CountryService countryService) {
        super(countryService);
    }

    @GetMapping(params = "name")
    @PreAuthorize(value = "hasAuthority('" + Authorities.READ_PRIVILEGE + "')")
    public ResponseEntity<Country> findByName(
            @RequestParam
            @NotNull(message = "{country.name.notEmpty}")
            @Size(min = 2, max = 50, message = "{country.name.size}") String name) {
        return ResponseEntity.of(this.service.findByName(name));
    }
}
