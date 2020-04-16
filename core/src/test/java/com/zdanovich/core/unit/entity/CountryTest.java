package com.zdanovich.core.unit.entity;

import com.zdanovich.core.entity.Country;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static com.zdanovich.core.utils.CoreConstants.EMPTY_STRING;

public class CountryTest extends ValidationTest {

    @Test
    public void testValidation_Country_Name_NotEmpty() {
        Country country = new Country();
        Set<ConstraintViolation<Country>> constraintViolations = validator.validate(country);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Country> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("country.name.notEmpty"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Country_Name_Size_Min() {
        Country country = new Country();
        country.setName("A");
        Set<ConstraintViolation<Country>> constraintViolations = validator.validate(country);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Country> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("country.name.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Country_Name_Size_Max() {
        Country country = new Country();
        country.setName("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        Set<ConstraintViolation<Country>> constraintViolations = validator.validate(country);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Country> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("country.name.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }
}
