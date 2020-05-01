package com.zdanovich.web.controller.impl;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.service.impl.CountryService;
import com.zdanovich.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<Country> findByName(
            @RequestParam
            @NotNull(message = "{country.name.notEmpty}")
            @Size(min = 2, max = 50, message = "{country.name.size}") String name) {
        return ResponseEntity.of(this.service.findByName(name));
    }

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(this.service.count());
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Long> deleteById(
            @RequestParam
            @NotNull(message = "{entity.id.notNull}") Long id) {
        if (this.service.existsById(id)) {
            this.service.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    /*@DeleteMapping
    public ResponseEntity<Iterable<Country>> deleteAll(
            @RequestBody
            @Valid Iterable<Country> iterable) {
        this.service.deleteAll(iterable);
        return ResponseEntity.ok(iterable);
    }*/
}
