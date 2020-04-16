package com.zdanovich.core.unit.entity;

import com.zdanovich.core.entity.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static com.zdanovich.core.utils.CoreConstants.EMPTY_STRING;

public class FeatureTest extends ValidationTest {

    @Test
    public void testValidation_Feature_Name_NotEmpty() {
        Feature feature = new Feature();
        Set<ConstraintViolation<Feature>> constraintViolations = validator.validate(feature);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Feature> constraintViolation : constraintViolations) {
            Assert.assertNotNull(constraintViolation);
            Assert.assertEquals(resourceBundle.getString("feature.name.notEmpty"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Feature_Name_Size_Min() {
        Feature feature = new Feature();
        feature.setName("AB");
        Set<ConstraintViolation<Feature>> constraintViolations = validator.validate(feature);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Feature> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("feature.name.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Feature_Name_Size_Max() {
        Feature feature = new Feature();
        feature.setName("ABCDEFGHIJKLMNOPQRSTUVWXYZabcde");
        Set<ConstraintViolation<Feature>> constraintViolations = validator.validate(feature);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Feature> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("feature.name.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

}
