package com.zdanovich.core.integration.repository;

import com.zdanovich.core.repository.CountryRepository;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.metamodel.Country_;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountryRepositoryTest extends AbstractRepositoryTest {

    @Test
    public void save() {
        Country expected = new Country();
        expected.setName("TestCountryName1");

        Country actual = countryRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Country savedCountry = countryRepository.save(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = countryRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_By_Iterable() {
        Country expected1 = new Country();
        expected1.setName("TestCountryName2");
        Country expected2 = new Country();
        expected2.setName("TestCountryName3");
        List<Country> countries = new ArrayList<>();
        countries.add(expected1);
        countries.add(expected2);

        Country actual1 = countryRepository.findByName(expected1.getName()).orElse(null);
        Assert.assertNull(actual1);
        Country actual2 = countryRepository.findByName(expected2.getName()).orElse(null);
        Assert.assertNull(actual2);

        List<Country> savedCountries = countryRepository.saveAll(countries);
        Assert.assertNotNull(savedCountries);
        Assert.assertEquals(countries.size(), savedCountries.size());
        actual1 = savedCountries.get(0);
        Assert.assertNotNull(actual1);
        expected1.setId(actual1.getId());
        Assert.assertEquals(expected1, actual1);
        actual2 = savedCountries.get(1);
        Assert.assertNotNull(actual2);
        expected2.setId(actual2.getId());
        Assert.assertEquals(expected2, actual2);

        actual1 = countryRepository.findById(expected1.getId()).orElse(null);
        Assert.assertEquals(expected1, actual1);
        actual2 = countryRepository.findById(expected2.getId()).orElse(null);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void saveAndFlush() {
        Country expected = new Country();
        expected.setName("TestCountryName4");

        Country actual = countryRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Country savedCountry = countryRepository.saveAndFlush(expected);
        Assert.assertNotNull(savedCountry);
        expected.setId(savedCountry.getId());
        Assert.assertEquals(expected, savedCountry);

        actual = countryRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flush() {
        Country country = new Country();
        country.setName("TestCountryName5");

        Country actual = countryRepository.findByName(country.getName()).orElse(null);
        Assert.assertNull(actual);

        Country savedCountry = countryRepository.save(country);
        Assert.assertNotNull(savedCountry);
        country.setId(savedCountry.getId());
        Assert.assertEquals(country, savedCountry);

        actual = countryRepository.findById(country.getId()).orElse(null);
        Assert.assertEquals(country, actual);

        /**{@link org.springframework.data.repository.CrudRepository#findById(Object)} synchronized with cache, but
         * {@link CountryRepository#findByName(String)} not synchronized and required flush
         */

        actual = countryRepository.findByName(country.getName()).orElse(null);
        Assert.assertNull(actual);

        countryRepository.flush();

        actual = countryRepository.findByName(country.getName()).orElse(null);
        Assert.assertEquals(country, actual);
    }

    @Test
    public void findById() {
        Country actual = countryRepository.findById(france.getId()).orElse(null);
        Assert.assertEquals(france, actual);
    }

    @Test
    public void findByName() {
        Country actual = countryRepository.findByName(france.getName()).orElse(null);
        Assert.assertEquals(france, actual);
    }

    @Test
    public void findOne_ById() {
        Country expected = new Country();
        expected.setId(france.getId());

        Country actual = countryRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(france, actual);
    }

    @Test
    public void findOne_ByName() {
        Country expected = new Country();
        expected.setName(france.getName());

        Country actual = countryRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void findAll() {
        List<Country> actual = countryRepository.findAll();
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findAllById() {
        List<Long> countryIds = new ArrayList<>();
        countryIds.add(france.getId());
        countryIds.add(spain.getId());

        List<Country> foundCountries = countryRepository.findAllById(countryIds);
        Assert.assertNotNull(foundCountries);
        Assert.assertEquals(countryIds.size(), foundCountries.size());
        Country actual1 = foundCountries.stream().filter(country -> country.getId().equals(france.getId())).findAny().orElse(null);
        Country actual2 = foundCountries.stream().filter(country -> country.getId().equals(spain.getId())).findAny().orElse(null);
        Assert.assertEquals(france, actual1);
        Assert.assertEquals(spain, actual2);
    }

    @Test
    public void findAll_With_Sort_ById() {
        List<Country> unsortedCountries = countryRepository.findAll();
        Assert.assertNotNull(unsortedCountries);
        Assert.assertFalse(unsortedCountries.isEmpty());

        List<Country> sortedCountries = countryRepository.findAll(Sort.by(Country_.COUNTRY_ID));
        Assert.assertNotNull(sortedCountries);
        Assert.assertEquals(unsortedCountries.size(), sortedCountries.size());

        unsortedCountries.sort(Comparator.comparingLong(Country::getId));
        Assert.assertEquals(unsortedCountries, sortedCountries);
    }

    @Test
    public void findAll_With_Sort_ByName() {
        List<Country> unsortedCountries = countryRepository.findAll();
        Assert.assertNotNull(unsortedCountries);

        List<Country> sortedCountries = countryRepository.findAll(Sort.by(Country_.NAME));
        Assert.assertNotNull(sortedCountries);
        Assert.assertEquals(unsortedCountries.size(), sortedCountries.size());

        unsortedCountries.sort(Comparator.comparing(Country::getName));
        Assert.assertEquals(unsortedCountries, sortedCountries);
    }

    @Test
    public void findAll_By_Example_Id() {
        Country example = new Country();
        example.setId(france.getId());

        List<Country> foundCountries = countryRepository.findAll(Example.of(example));
        Assert.assertNotNull(foundCountries);
        Assert.assertEquals(1, foundCountries.size());
        Assert.assertEquals(france, foundCountries.get(0));
    }

    @Test
    public void findAll_By_Example_Name() {
        Country example = new Country();
        example.setName(france.getName());

        List<Country> foundCountries = countryRepository.findAll(Example.of(example));
        Assert.assertNotNull(foundCountries);
        Assert.assertEquals(1, foundCountries.size());
        Assert.assertEquals(france, foundCountries.get(0));
    }

    @Test
    public void findAll_By_Example_With_Sort() {
        Country example = new Country();
        example.setName("The");

        List<Country> foundCountries = countryRepository.findAll(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)), Sort.by(Country_.NAME));
        Assert.assertNotNull(foundCountries);
        Assert.assertEquals(2, foundCountries.size());
        Country actual1 = foundCountries.stream().filter(country -> country.getName().equals(theGreatBritain.getName())).findAny().orElse(null);
        Country actual2 = foundCountries.stream().filter(country -> country.getName().equals(theNetherlands.getName())).findAny().orElse(null);
        Assert.assertEquals(theGreatBritain, actual1);
        Assert.assertEquals(theNetherlands, actual2);
    }

    @Test
    public void findAll_By_Example_And_Pagination() {
        Country example = new Country();
        example.setName("The");

        Page<Country> countryPage = countryRepository.findAll(Example.of(example), PageRequest.of(0, 2));
        Assert.assertNotNull(countryPage);
    }

    @Test
    public void findAll_With_Pagination() {
        Page<Country> countryPage = countryRepository.findAll(PageRequest.of(0, 1));
        Assert.assertNotNull(countryPage);
    }

    @Test
    public void existsById() {
        Assert.assertTrue(countryRepository.existsById(france.getId()));
    }

    @Test
    public void exists_By_Example() {
        Country example = new Country();
        example.setName("Franc");
        Assert.assertTrue(countryRepository.exists(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING))));
    }

    @Test
    public void count() {
        Assert.assertNotEquals(0, countryRepository.count());
    }

    @Test
    public void count_By_Example() {
        Country example = new Country();
        example.setName("Franc");
        Assert.assertEquals(1, countryRepository.count(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING))));
    }

    @Test
    public void update() {
        Country country = new Country();
        country.setName("TestCountryName6");

        Country foundCountry = countryRepository.findByName(country.getName()).orElse(null);
        Assert.assertNull(foundCountry);

        Country savedCountry = countryRepository.save(country);
        Assert.assertNotNull(savedCountry);
        country.setId(savedCountry.getId());

        Country expected = countryRepository.findById(country.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(country, expected);

        expected.setName("TestCountryName7");
        savedCountry = countryRepository.save(expected);
        Assert.assertEquals(expected, savedCountry);

        foundCountry = countryRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, foundCountry);
    }

    @Test
    public void delete() {
        Country country = new Country();
        country.setName("TestCountryName8");

        Country foundCountry = countryRepository.findByName(country.getName()).orElse(null);
        Assert.assertNull(foundCountry);

        Country savedCountry = countryRepository.save(country);
        Assert.assertNotNull(savedCountry);
        country.setId(savedCountry.getId());

        foundCountry = countryRepository.findById(country.getId()).orElse(null);
        Assert.assertEquals(country, foundCountry);

        countryRepository.delete(country);

        Country deletedCountry = countryRepository.findById(country.getId()).orElse(null);
        Assert.assertNull(deletedCountry);
    }

    @Test
    public void deleteById() {
        Country country = new Country();
        country.setName("TestCountryName9");

        Country foundCountry = countryRepository.findByName(country.getName()).orElse(null);
        Assert.assertNull(foundCountry);

        Country savedCountry = countryRepository.save(country);
        Assert.assertNotNull(savedCountry);
        country.setId(savedCountry.getId());

        foundCountry = countryRepository.findById(country.getId()).orElse(null);
        Assert.assertNotNull(foundCountry);
        Assert.assertEquals(country, foundCountry);

        countryRepository.deleteById(country.getId());

        Country deletedCountry = countryRepository.findById(country.getId()).orElse(null);
        Assert.assertNull(deletedCountry);
    }

    @Test
    public void deleteAll() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteAll_By_Iterable() {
        Country country1 = new Country();
        country1.setName("TestCountryName10");
        Country country2 = new Country();
        country2.setName("TestCountryName11");
        List<Country> countries = new ArrayList<>();
        countries.add(country1);
        countries.add(country2);

        Country foundCountry = countryRepository.findByName(country1.getName()).orElse(null);
        Assert.assertNull(foundCountry);
        foundCountry = countryRepository.findByName(country2.getName()).orElse(null);
        Assert.assertNull(foundCountry);

        Country savedCountry = countryRepository.save(country1);
        Assert.assertNotNull(savedCountry);
        country1.setId(savedCountry.getId());
        savedCountry = countryRepository.save(country2);
        Assert.assertNotNull(savedCountry);
        country2.setId(savedCountry.getId());

        foundCountry = countryRepository.findById(country1.getId()).orElse(null);
        Assert.assertEquals(country1, foundCountry);
        foundCountry = countryRepository.findById(country2.getId()).orElse(null);
        Assert.assertEquals(country2, foundCountry);

        countryRepository.deleteAll(countries);

        foundCountry = countryRepository.findById(country1.getId()).orElse(null);
        Assert.assertNull(foundCountry);
        foundCountry = countryRepository.findById(country2.getId()).orElse(null);
        Assert.assertNull(foundCountry);
    }

    @Test
    public void deleteAllInBatch() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteInBatch_By_Iterable() {
        Country country1 = new Country();
        country1.setName("TestCountryName12");
        Country country2 = new Country();
        country2.setName("TestCountryName13");
        List<Country> countries = new ArrayList<>();
        countries.add(country1);
        countries.add(country2);

        Country foundCountry = countryRepository.findByName(country1.getName()).orElse(null);
        Assert.assertNull(foundCountry);
        foundCountry = countryRepository.findByName(country2.getName()).orElse(null);
        Assert.assertNull(foundCountry);

        Country savedCountry = countryRepository.save(country1);
        Assert.assertNotNull(savedCountry);
        country1.setId(savedCountry.getId());
        savedCountry = countryRepository.save(country2);
        Assert.assertNotNull(savedCountry);
        country2.setId(savedCountry.getId());

        foundCountry = countryRepository.findById(country1.getId()).orElse(null);
        Assert.assertEquals(country1, foundCountry);
        countries.set(countries.indexOf(country1), foundCountry);
        foundCountry = countryRepository.findById(country2.getId()).orElse(null);
        Assert.assertEquals(country2, foundCountry);
        countries.set(countries.indexOf(country2), foundCountry);

        clearCache();

        countryRepository.deleteInBatch(countries);

        /** {@link org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(Iterable)}
         * 1. Don't call @PreDelete
         * 2. Not CASCADE
         * 3. Execute bulk DELETE statement to the database, bypassing the cache etc
         */

        foundCountry = countryRepository.findById(country1.getId()).orElse(null);
        Assert.assertNull(foundCountry);
        foundCountry = countryRepository.findById(country2.getId()).orElse(null);
        Assert.assertNull(foundCountry);
    }
}
