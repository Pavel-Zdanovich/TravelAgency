package com.zdanovich.web.integration.controller;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.utils.Utils;
import com.zdanovich.web.controller.impl.FeatureController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FeatureControllerTest extends AbstractControllerTest {

    @Test
    public void save() throws Exception {
        Feature feature = new Feature();
        feature.setName("TestFeatureName1");

        MvcResult mvcResult = request(HttpMethod.POST, FeatureController.PATH, feature, HttpStatus.CREATED);

        Feature savedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(savedFeature);
        feature.setId(savedFeature.getId());
        Assert.assertEquals(feature, savedFeature);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(FeatureController.PATH)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Feature> features = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assert.assertNotNull(features);
        Assert.assertNotEquals(0, features.size());
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(FeatureController.PATH).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Feature feature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(feature);
    }

    @Test
    public void findByName() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(FeatureController.PATH).queryParam("name", Utils.AIR_CONDITIONER)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Feature feature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(feature);
    }

    @Test
    public void update() throws Exception {
        Feature feature = new Feature();
        feature.setName("TestFeatureName2");

        MvcResult mvcResult = request(HttpMethod.POST, FeatureController.PATH, feature, HttpStatus.CREATED);

        Feature savedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(savedFeature);

        savedFeature.setName("UpdatedFeatureName");

        mvcResult = request(HttpMethod.PUT, FeatureController.PATH, savedFeature, HttpStatus.OK);

        Feature updatedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(updatedFeature);
        Assert.assertEquals(savedFeature, updatedFeature);
    }

    @Test
    public void delete() throws Exception {
        Feature feature = new Feature();
        feature.setName("TestFeatureName3");

        MvcResult mvcResult = request(HttpMethod.POST, FeatureController.PATH, feature, HttpStatus.CREATED);

        Feature savedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(savedFeature);

        mvcResult = request(HttpMethod.DELETE, FeatureController.PATH, savedFeature, HttpStatus.OK);

        Feature deletedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(deletedFeature);
    }

    @Test
    public void deleteById() throws Exception {
        Feature feature = new Feature();
        feature.setName("TestFeatureName4");

        MvcResult mvcResult = request(HttpMethod.POST, FeatureController.PATH, feature, HttpStatus.CREATED);

        Feature savedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(savedFeature);

        mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(FeatureController.PATH).queryParam("id", String.valueOf(savedFeature.getId()))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Feature deletedFeature = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Feature.class);
        Assert.assertNotNull(deletedFeature);
    }
}