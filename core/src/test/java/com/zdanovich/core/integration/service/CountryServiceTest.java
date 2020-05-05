package com.zdanovich.core.integration.service;

import com.zdanovich.core.entity.Country;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CountryServiceTest extends AbstractServiceTest {

    @Test
    public void save() {
        Country expected = new Country();
        expected.setName("TestCountryName1");

        Country actual = countryService.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Country savedCountry = countryService.save(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = countryService.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findById() {
        Country actual = countryService.findById(france.getId()).orElse(null);
        Assert.assertEquals(france, actual);
    }

    @Test
    public void findByName() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void existsById() {
    }

    @Test
    public void count() {
    }

    @Test
    public void update() {
        Country country = new Country();
        country.setName("TestCountryName6");

        Country foundCountry = countryService.findByName(country.getName()).orElse(null);
        Assert.assertNull(foundCountry);

        Country savedCountry = countryService.save(country);
        Assert.assertNotNull(savedCountry);
        country.setId(savedCountry.getId());

        Country expected = countryService.findById(country.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(country, expected);

        expected.setName("TestCountryName7");
        savedCountry = countryService.save(expected);
        Assert.assertEquals(expected, savedCountry);

        foundCountry = countryService.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, foundCountry);
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteAllByIterable() {
    }

    @Test
    public void deleteAll() {
    }
}