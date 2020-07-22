package com.zdanovich.core.unit.validation;

import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.utils.CoreUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

public class UserTest extends ValidationTest {

    public static final String VALID_USER_LOGIN = "ValidUserLogin123";
    public static final String VALID_USER_PASSWORD = "ValidUserPassword123";
    public static final UserRole VALID_USER_ROLE = UserRole.USER;

    @Test
    public void testValidation_User_Login_NotNull() {
        User user = new User();
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.login.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_User_Login_Size() {
        User user = new User();
        user.setLogin("User");
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.login.size").replaceAll(INTERPOLATE_REGEX, CoreUtils.EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, CoreUtils.EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_User_Login_Pattern() {
        User user = new User();
        user.setLogin("Invalid User Login");
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.login.pattern"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_User_Password_NotNull() {
        User user = new User();
        user.setLogin(VALID_USER_LOGIN);
        user.setRole(VALID_USER_ROLE);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.password.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_User_Password_Size() {
        User user = new User();
        user.setLogin(VALID_USER_LOGIN);
        user.setPassword("pass");
        user.setRole(VALID_USER_ROLE);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.password.size").replaceAll(INTERPOLATE_REGEX, CoreUtils.EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, CoreUtils.EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_User_Password_Pattern() {
        User user = new User();
        user.setLogin(VALID_USER_LOGIN);
        user.setPassword("Invalid User Password");
        user.setRole(VALID_USER_ROLE);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.password.pattern"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_User_add_remove_Tour() throws NoSuchMethodException {
        User user = new User();
        user.setLogin(VALID_USER_LOGIN);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        Method addTour = User.class.getMethod("addTour", Tour.class);
        Method removeTour = User.class.getMethod("removeTour", Tour.class);
        Object[] objects = { null };
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<User>> constraintViolations = executableValidator.validateParameters(user, addTour, objects);
        constraintViolations.addAll(executableValidator.validateParameters(user, removeTour, objects));
        Assert.assertEquals(2, constraintViolations.size());
        for (ConstraintViolation<User> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.notNull"), constraintViolation.getMessage());
        }
    }

}
