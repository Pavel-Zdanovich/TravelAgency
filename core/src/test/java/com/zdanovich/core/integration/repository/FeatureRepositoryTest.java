package com.zdanovich.core.integration.repository;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.metamodel.Feature_;
import com.zdanovich.core.repository.FeatureRepository;
import com.zdanovich.core.utils.CoreUtils;
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

public class FeatureRepositoryTest extends AbstractRepositoryTest {

    @Test
    public void save() {
        Feature expected = new Feature();
        expected.setName("TestFeatureName1");

        Feature actual = featureRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Feature savedFeature = featureRepository.save(expected);
        Assert.assertNotNull(savedFeature);
        expected.setId(savedFeature.getId());
        Assert.assertEquals(expected, savedFeature);

        actual = featureRepository.findById(expected.getId()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(savedFeature, actual);
    }

    @Test
    public void saveAll_By_Iterable() {
        Feature expected1 = new Feature();
        expected1.setName("TestFeatureName2");
        Feature expected2 = new Feature();
        expected2.setName("TestFeatureName3");
        List<Feature> features = new ArrayList<>();
        features.add(expected1);
        features.add(expected2);

        Feature actual1 = featureRepository.findByName(expected1.getName()).orElse(null);
        Assert.assertNull(actual1);
        Feature actual2 = featureRepository.findByName(expected2.getName()).orElse(null);
        Assert.assertNull(actual2);

        List<Feature> savedFeatures = featureRepository.saveAll(features);
        Assert.assertNotNull(savedFeatures);
        Assert.assertEquals(features.size(), savedFeatures.size());
        actual1 = savedFeatures.get(0);
        Assert.assertNotNull(actual1);
        expected1.setId(actual1.getId());
        Assert.assertEquals(expected1, actual1);
        actual2 = savedFeatures.get(1);
        Assert.assertNotNull(actual2);
        expected2.setId(actual2.getId());
        Assert.assertEquals(expected2, actual2);

        actual1 = featureRepository.findById(expected1.getId()).orElse(null);
        Assert.assertNotNull(actual1);
        Assert.assertEquals(expected1.getName(), actual1.getName());
        actual2 = featureRepository.findById(expected2.getId()).orElse(null);
        Assert.assertNotNull(actual2);
        Assert.assertEquals(expected2.getName(), actual2.getName());
    }

    @Test
    public void saveAndFlush() {
        Feature expected = new Feature();
        expected.setName("TestFeatureName4");

        Feature actual = featureRepository.findByName(expected.getName()).orElse(null);
        Assert.assertNull(actual);

        Feature savedFeature = featureRepository.saveAndFlush(expected);
        Assert.assertNotNull(savedFeature);
        expected.setId(savedFeature.getId());
        Assert.assertEquals(expected, savedFeature);

        actual = featureRepository.findById(savedFeature.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flush() {
        Feature feature = new Feature();
        feature.setName("TestFeatureName5");

        Feature actual = featureRepository.findByName(feature.getName()).orElse(null);
        Assert.assertNull(actual);

        Feature savedFeature = featureRepository.save(feature);
        Assert.assertNotNull(savedFeature);
        feature.setId(savedFeature.getId());
        Assert.assertEquals(feature, savedFeature);

        actual = featureRepository.findById(feature.getId()).orElse(null);
        Assert.assertEquals(feature, actual);

        /**{@link org.springframework.data.repository.CrudRepository#findById(Object)} synchronized with cache, but
         * {@link FeatureRepository#findByName(String)} not synchronized and required flush
         */

        actual = featureRepository.findByName(feature.getName()).orElse(null);
        Assert.assertNull(actual);

        featureRepository.flush();

        actual = featureRepository.findByName(feature.getName()).orElse(null);
        Assert.assertEquals(feature, actual);
    }

    @Test
    public void findById() {
        Feature actual = featureRepository.findById(airConditioner.getId()).orElse(null);
        Assert.assertEquals(airConditioner, actual);
    }

    @Test
    public void findByName() {
        Feature actual = featureRepository.findByName(airConditioner.getName()).orElse(null);
        Assert.assertEquals(airConditioner, actual);
    }

    @Test
    public void findOne_ById() {
        Feature expected = new Feature();
        expected.setId(airConditioner.getId());

        Feature actual = featureRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(airConditioner, actual);
    }

    @Test
    public void findOne_ByName() {
        Feature expected = new Feature();
        expected.setName(CoreUtils.AIR_CONDITIONER);

        Feature actual = featureRepository.findOne(Example.of(expected)).orElse(null);
        Assert.assertEquals(airConditioner, actual);
    }

    @Test
    public void findAll() {
        List<Feature> actual = featureRepository.findAll();
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findAllById() {
        List<Long> featureIds = new ArrayList<>();
        featureIds.add(airConditioner.getId());
        featureIds.add(cableTv.getId());

        List<Feature> foundFeatures = featureRepository.findAllById(featureIds);
        Assert.assertNotNull(foundFeatures);
        Assert.assertEquals(foundFeatures.size(), featureIds.size());
        Feature actual1 = foundFeatures.stream().filter(feature -> feature.getId().equals(airConditioner.getId())).findAny().orElse(null);
        Feature actual2 = foundFeatures.stream().filter(feature -> feature.getId().equals(cableTv.getId())).findAny().orElse(null);
        Assert.assertEquals(airConditioner, actual1);
        Assert.assertEquals(cableTv, actual2);
    }

    @Test
    public void findAll_With_Sort_ById() {
        List<Feature> unsortedFeatures = featureRepository.findAll();
        Assert.assertNotNull(unsortedFeatures);
        Assert.assertFalse(unsortedFeatures.isEmpty());

        List<Feature> sortedFeatures = featureRepository.findAll(Sort.by(Feature_.FEATURE_ID));
        Assert.assertNotNull(sortedFeatures);
        Assert.assertEquals(unsortedFeatures.size(), sortedFeatures.size());

        unsortedFeatures.sort(Comparator.comparingLong(Feature::getId));
        Assert.assertEquals(unsortedFeatures, sortedFeatures);
    }

    @Test
    public void findAll_With_Sort_ByName() {
        List<Feature> unsortedFeatures = featureRepository.findAll();
        Assert.assertNotNull(unsortedFeatures);
        Assert.assertFalse(unsortedFeatures.isEmpty());

        List<Feature> sortedFeatures = featureRepository.findAll(Sort.by(Feature_.NAME));
        Assert.assertNotNull(sortedFeatures);
        Assert.assertEquals(unsortedFeatures.size(), sortedFeatures.size());

        unsortedFeatures.sort(Comparator.comparing(Feature::getName));
        Assert.assertEquals(unsortedFeatures, sortedFeatures);
    }

    @Test
    public void findAll_By_Example_Id() {
        Feature example = new Feature();
        example.setId(airConditioner.getId());

        List<Feature> foundFeatures = featureRepository.findAll(Example.of(example));
        Assert.assertNotNull(foundFeatures);
        Assert.assertEquals(1, foundFeatures.size());
        Assert.assertEquals(airConditioner, foundFeatures.get(0));
    }

    @Test
    public void findAll_By_Example_Name() {
        Feature example = new Feature();
        example.setName(airConditioner.getName());

        List<Feature> foundFeatures = featureRepository.findAll(Example.of(example));
        Assert.assertNotNull(foundFeatures);
        Assert.assertEquals(1, foundFeatures.size());
        Assert.assertEquals(airConditioner, foundFeatures.get(0));
    }

    @Test
    public void findAll_By_Example_With_Sort() {
        Feature example = new Feature();
        example.setName("ca");

        List<Feature> foundFeatures = featureRepository.findAll(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)), Sort.by(Feature_.NAME));
        Assert.assertNotNull(foundFeatures);
        Assert.assertEquals(2, foundFeatures.size());
        Feature actual1 = foundFeatures.stream().filter(feature -> feature.getName().equals(cableTv.getName())).findAny().orElse(null);
        Feature actual2 = foundFeatures.stream().filter(feature -> feature.getName().equals(carRental.getName())).findAny().orElse(null);
        Assert.assertEquals(cableTv, actual1);
        Assert.assertEquals(carRental, actual2);
    }

    @Test
    public void findAll_By_Example_With_Pagination() {
        Page<Feature> featurePage = featureRepository.findAll(PageRequest.of(0, 1));
        Assert.assertNotNull(featurePage);
    }

    @Test
    public void findAll_With_Pagination() {
        Page<Feature> featurePage = featureRepository.findAll(PageRequest.of(0, 1));
        Assert.assertNotNull(featurePage);
    }

    @Test
    public void existsById() {
        Assert.assertTrue(featureRepository.existsById(airConditioner.getId()));
    }

    @Test
    public void exists_By_Example() {
        Feature example = new Feature();
        example.setName("air");
        Assert.assertTrue(featureRepository.exists(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING))));
    }

    @Test
    public void count() {
        Assert.assertNotEquals(0, featureRepository.count());
    }

    @Test
    public void count_By_Example() {
        Feature example = new Feature();
        example.setName("air");
        Assert.assertEquals(1, featureRepository.count(Example.of(example, ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING))));
    }

    @Test
    public void update() {
        Feature feature = new Feature();
        feature.setName("TestFeatureName6");

        Feature actual = featureRepository.findByName(feature.getName()).orElse(null);
        Assert.assertNull(actual);

        Feature savedFeature = featureRepository.save(feature);
        Assert.assertNotNull(savedFeature);
        feature.setId(savedFeature.getId());

        Feature expected = featureRepository.findById(feature.getId()).orElse(null);
        Assert.assertNotNull(expected);
        Assert.assertEquals(feature, expected);

        expected.setName("TestFeatureName7");
        savedFeature = featureRepository.save(expected);
        Assert.assertEquals(expected, savedFeature);

        actual = featureRepository.findById(expected.getId()).orElse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Feature feature = new Feature();
        feature.setName("TestFeatureName8");

        Feature foundFeature = featureRepository.findByName(feature.getName()).orElse(null);
        Assert.assertNull(foundFeature);

        Feature savedFeature = featureRepository.save(feature);
        Assert.assertNotNull(savedFeature);
        feature.setId(savedFeature.getId());

        foundFeature = featureRepository.findById(feature.getId()).orElse(null);
        Assert.assertNotNull(foundFeature);
        Assert.assertEquals(feature, foundFeature);

        featureRepository.delete(foundFeature);

        Feature deletedFeature = featureRepository.findById(foundFeature.getId()).orElse(null);
        Assert.assertNull(deletedFeature);
    }

    @Test
    public void deleteById() {
        Feature feature = new Feature();
        feature.setName("TestFeatureName9");

        Feature foundFeature = featureRepository.findByName(feature.getName()).orElse(null);
        Assert.assertNull(foundFeature);

        Feature savedFeature = featureRepository.save(feature);
        Assert.assertNotNull(savedFeature);
        feature.setId(savedFeature.getId());

        foundFeature = featureRepository.findById(feature.getId()).orElse(null);
        Assert.assertNotNull(foundFeature);
        Assert.assertEquals(feature, foundFeature);

        featureRepository.deleteById(feature.getId());

        Feature deletedFeature = featureRepository.findById(feature.getId()).orElse(null);
        Assert.assertNull(deletedFeature);
    }

    @Test
    public void deleteAll() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteAll_Iterable() {
        Feature feature1 = new Feature();
        feature1.setName("TestFeatureName10");
        Feature feature2 = new Feature();
        feature2.setName("TestFeatureName11");
        List<Feature> features = new ArrayList<>();
        features.add(feature1);
        features.add(feature2);

        Feature foundFeature = featureRepository.findByName(feature1.getName()).orElse(null);
        Assert.assertNull(foundFeature);
        foundFeature = featureRepository.findByName(feature2.getName()).orElse(null);
        Assert.assertNull(foundFeature);

        Feature savedFeature = featureRepository.save(feature1);
        Assert.assertNotNull(savedFeature);
        feature1.setId(savedFeature.getId());
        savedFeature = featureRepository.save(feature2);
        Assert.assertNotNull(savedFeature);
        feature2.setId(savedFeature.getId());

        foundFeature = featureRepository.findById(feature1.getId()).orElse(null);
        Assert.assertEquals(feature1, foundFeature);
        foundFeature = featureRepository.findById(feature2.getId()).orElse(null);
        Assert.assertEquals(feature2, foundFeature);

        featureRepository.deleteAll(features);

        foundFeature = featureRepository.findById(feature1.getId()).orElse(null);
        Assert.assertNull(foundFeature);
        foundFeature = featureRepository.findById(feature2.getId()).orElse(null);
        Assert.assertNull(foundFeature);
    }

    @Test
    public void deleteAllInBatch() {
        //I hope I'll never use this method
    }

    @Test
    public void deleteInBatch_By_Iterable() {
        Feature feature1 = new Feature();
        feature1.setName("TestFeatureName12");
        Feature feature2 = new Feature();
        feature2.setName("TestFeatureName13");
        List<Feature> features = new ArrayList<>();
        features.add(feature1);
        features.add(feature2);

        Feature foundFeature = featureRepository.findByName(feature1.getName()).orElse(null);
        Assert.assertNull(foundFeature);
        foundFeature = featureRepository.findByName(feature2.getName()).orElse(null);
        Assert.assertNull(foundFeature);

        Feature savedFeature = featureRepository.save(feature1);
        Assert.assertNotNull(savedFeature);
        feature1.setId(savedFeature.getId());
        savedFeature = featureRepository.save(feature2);
        Assert.assertNotNull(savedFeature);
        feature2.setId(savedFeature.getId());

        foundFeature = featureRepository.findById(feature1.getId()).orElse(null);
        Assert.assertEquals(feature1, foundFeature);
        foundFeature = featureRepository.findById(feature2.getId()).orElse(null);
        Assert.assertEquals(feature2, foundFeature);

        clearCache();

        featureRepository.deleteInBatch(features);

        /** {@link org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(Iterable)}
         * 1. Don't call @PreDelete
         * 2. Not CASCADE
         * 3. Execute bulk DELETE statement to the database, bypassing the cache etc
         */

        foundFeature = featureRepository.findById(feature1.getId()).orElse(null);
        Assert.assertNull(foundFeature);
        foundFeature = featureRepository.findById(feature2.getId()).orElse(null);
        Assert.assertNull(foundFeature);
    }
}
