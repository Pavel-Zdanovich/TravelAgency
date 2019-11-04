package com.epam.core.integration.repository;

import com.epam.core.entity.Country;
import com.epam.core.integration.config.EntityManagerConfig;
import com.epam.core.integration.config.MigrationConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import com.epam.core.repository.CountryRepository;
import de.flapdoodle.embed.process.collections.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, MigrationConfig.class, EntityManagerConfig.class, CountryRepository.class})
@ActiveProfiles(profiles = {"test", "postgresql"})
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void save() {
        Country expected = new Country();
        expected.setName("TestCountryName");
        countryRepository.save(expected);
        Country actual = countryRepository.findByName(expected.getName()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_By_Iterable() {
    }

    @Test
    public void saveAndFlush() {
    }

    @Test
    public void flush() {
    }

    @Test
    public void findById() {
        Country expected = new Country();
        expected.setId(1L);
        expected.setName("France");
        Country actual = countryRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByName() {
        Country expected = new Country();
        expected.setId(1L);
        expected.setName("France");
        Country actual = countryRepository.findByName(expected.getName()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<Country> actual = countryRepository.findAll();
        Assert.assertTrue(actual.size() > 0);
    }

    @Test
    public void findAllById() {
    }

    @Test
    public void findAll_With_Sort () {
    }

    @Test
    public void findAll_By_Example() {
    }

    @Test
    public void findAll_By_Example_With_Sort() {
    }

    @Test
    public void findAll_By_Pageable() {
    }

    @Test
    public void findAll_By_Example_And_Pageable() {
    }

    @Test
    public void existsById() {
        Assert.assertTrue(countryRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
    }

    @Test
    public void count() {
        Assert.assertNotEquals(countryRepository.count(), 0);
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update() {
        Country expected = countryRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setName("UpdatedCountryName");
        countryRepository.save(expected);
        Country actual = countryRepository.findById(2L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Country foundCountry = countryRepository.findById(3L).orElse(null);
        Assert.assertNotNull(foundCountry);
        countryRepository.delete(foundCountry);
        Country deletedCountry = countryRepository.findById(3L).orElse(null);
        Assert.assertNull(deletedCountry);
    }

    @Test
    public void deleteById() {
        Country foundCountry = countryRepository.findById(4L).orElse(null);
        Assert.assertNotNull(foundCountry);
        countryRepository.deleteById(4L);
        Country deletedCountry = countryRepository.findById(4L).orElse(null);
        Assert.assertNull(deletedCountry);
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void deleteAll_By_Iterable() {
        long numberOfCountriesBeforeDeletion = countryRepository.count();
        Country country1 = new Country();
        country1.setId(5L);
        country1.setName("Greece");
        Country country2 = new Country();
        country2.setId(6L);
        country2.setName("Russia");
        countryRepository.deleteAll(Collections.newArrayList(country1, country2));
        long numberOfCountriesAfterDeletion = countryRepository.count();
        Assert.assertEquals(numberOfCountriesBeforeDeletion - numberOfCountriesAfterDeletion, 2);
    }

    @Test
    public void deleteAllInBatch() {
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }
}
