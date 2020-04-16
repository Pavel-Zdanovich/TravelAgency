package com.zdanovich.core.unit.entity;

import com.zdanovich.core.config.ValidationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.validation.Validator;
import java.util.ResourceBundle;

@Test
@ContextConfiguration(classes = ValidationConfig.class)
public abstract class ValidationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected Validator validator;
    protected ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    public static final String INTERPOLATE_REGEX = "\\{\\w*\\}";
    public static final String NUMBER_REGEX = "(\\d|\\.)*";

}
