package com.epam.core.integration.repository;

import com.epam.core.entity.Feature;
import com.epam.core.integration.config.EntityManagerConfig;
import com.epam.core.integration.config.MigrationConfig;
import com.epam.core.integration.config.TestDataSourceConfig;
import com.epam.core.repository.FeatureRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, MigrationConfig.class, EntityManagerConfig.class, FeatureRepository.class})
@ActiveProfiles(profiles = {"test", "postgresql"})
public class FeatureRepositoryTest {

    @Autowired
    private FeatureRepository featureRepository;

    @Test
    public void save() {
        Feature expected = new Feature();
        expected.setName("TestFeatureName");
        featureRepository.save(expected);
        Feature actual = featureRepository.findByName(expected.getName()).orElse(null);
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
        Feature expected = new Feature();
        expected.setId(1L);
        expected.setName("air conditioner");
        Feature actual = featureRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByName() {
        Feature expected = new Feature();
        expected.setId(1L);
        expected.setName("air conditioner");
        Feature actual = featureRepository.findByName(expected.getName()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        List<Feature> actual = featureRepository.findAll();
        Assert.assertTrue(actual.size() > 0);
    }

    @Test
    public void findAll_Sort () {
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
        Assert.assertTrue(featureRepository.existsById(1L));
    }

    @Test
    public void exists_By_Example() {
    }

    @Test
    public void count() {
        Assert.assertNotEquals(featureRepository.count(), 0);
    }

    @Test
    public void count_By_Example() {
    }

    @Test
    public void update() {
        Feature expected = featureRepository.findById(2L).orElse(null);
        Assert.assertNotNull(expected);
        expected.setName("UpdatedFeatureName");
        featureRepository.save(expected);
        Feature actual = featureRepository.findById(2L).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Feature foundFeature = featureRepository.findById(3L).orElse(null);
        Assert.assertNotNull(foundFeature);
        featureRepository.delete(foundFeature);
        Feature deletedFeature = featureRepository.findById(3L).orElse(null);
        Assert.assertNull(deletedFeature);
    }

    @Test
    public void deleteById() {
        Feature foundFeature = featureRepository.findById(4L).orElse(null);
        Assert.assertNotNull(foundFeature);
        featureRepository.deleteById(4L);
        Feature deletedFeature = featureRepository.findById(4L).orElse(null);
        Assert.assertNull(deletedFeature);
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void deleteAll_Iterable() {
        long numberOfFeaturesBeforeDeletion = featureRepository.count();
        Feature feature1 = new Feature();
        feature1.setId(5L);
        feature1.setName("parking");
        Feature feature2 = new Feature();
        feature2.setId(6L);
        feature2.setName("restaurant");
        featureRepository.deleteAll(new ArrayList<Feature>() {{
            add(feature1);
            add(feature2);
        }});
        long numberOfFeaturesAfterDeletion = featureRepository.count();
        Assert.assertEquals(numberOfFeaturesBeforeDeletion - numberOfFeaturesAfterDeletion, 2);
    }

    @Test
    public void deleteAllInBatch() {
    }

    @Test
    public void deleteInBatch_By_Iterable() {
    }
}
