package com.zdanovich.core.unit.validation;

import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import static com.zdanovich.core.utils.Utils.EMPTY_STRING;

public class ReviewTest extends ValidationTest {

    public static final String VALID_REVIEW_TEXT = "Valid review text";
    public static final Timestamp VALID_REVIEW_DATE = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 0, 0));

    @Test
    public void testValidation_Review_Date_NotNull() {
        Review review = new Review();
        review.setReviewText(VALID_REVIEW_TEXT);
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Review> constraintViolation : constraintViolations) {
            Assert.assertNotNull(constraintViolation);
            Assert.assertEquals(resourceBundle.getString("review.date.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Review_Date_FutureOrPresent() {
        Review review = new Review();
        review.setReviewText(VALID_REVIEW_TEXT);
        review.setReviewDate(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0)));
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Review> constraintViolation : constraintViolations) {
            Assert.assertNotNull(constraintViolation);
            Assert.assertEquals(resourceBundle.getString("review.date.futureOrPresent"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Review_Text_NotEmpty() {
        Review review = new Review();
        review.setReviewDate(VALID_REVIEW_DATE);
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Review> constraintViolation : constraintViolations) {
            Assert.assertNotNull(constraintViolation);
            Assert.assertEquals(resourceBundle.getString("review.text.notEmpty"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Review_Text_Size_Max() {
        Review review = new Review();
        String hundredCharactersString = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        String invalidString = "1";
        for (int i = 0; i < 10; i++) {
            invalidString = invalidString.concat(hundredCharactersString);
        }
        review.setReviewText(invalidString);
        review.setReviewDate(VALID_REVIEW_DATE);
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Review> constraintViolation : constraintViolations) {
            Assert.assertNotNull(constraintViolation);
            Assert.assertEquals(resourceBundle.getString("review.text.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Review_addUser() throws NoSuchMethodException {
        Review review = new Review();
        review.setReviewText("");
        review.setReviewDate(VALID_REVIEW_DATE);
        Method setUser = Review.class.getMethod("setUser", User.class);
        Object[] objects = { null };
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Review>> constraintViolations = executableValidator.validateParameters(review, setUser, objects);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.notNull"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Review_addTour() throws NoSuchMethodException {
        Review review = new Review();
        review.setReviewText("");
        review.setReviewDate(VALID_REVIEW_DATE);
        Method setTour = Review.class.getMethod("setTour", Tour.class);
        Object[] objects = { null };
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Review>> constraintViolations = executableValidator.validateParameters(review, setTour, objects);
        constraintViolations.addAll(executableValidator.validateParameters(review, setTour, objects));
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.notNull"),
                    constraintViolation.getMessage());
        }
    }

}
