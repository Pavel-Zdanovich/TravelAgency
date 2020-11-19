package com.zdanovich.core.unit.validation;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import com.zdanovich.core.entity.enums.TourType;
import com.zdanovich.core.utils.CoreUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

public class TourTest extends ValidationTest {

    public static final String VALID_TOUR_PHOTO_PATH = "C:\\Users\\Zdan\\IdeaProjects\\travelagency\\core\\src\\main\\resources\\messages.properties";
    public static final Timestamp VALID_TOUR_START_DATE = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 0, 0));
    public static final Timestamp VALID_TOUR_END_DATE = Timestamp.valueOf(LocalDateTime.of(2021, 1, 2, 0, 0));
    public static final String VALID_TOUR_DESCRIPTION = "Valid description of the tour";
    public static final BigDecimal VALID_TOUR_COST = BigDecimal.valueOf(0.1234);
    public static final TourType VALID_TOUR_TYPE = TourType.BUSINESS;

    @Test
    public void testValidation_Tour_PhotoPath_NotEmpty() {
        Tour tour = new Tour();
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.photoPath.notEmpty"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_StartDate_NotNull() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.startDate.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_StartDate_FutureOrPresent() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0)));
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.startDate.futureOrPresent"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_EndDate_NotNull() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.endDate.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_EndDate_Future() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0)));
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.endDate.future"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_Description_NotEmpty() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.description.notEmpty"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_Description_Size() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        String hundredCharactersString = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        String invalidString = "1";
        for (int i = 0; i < 10; i++) {
            invalidString = invalidString.concat(hundredCharactersString);
        }
        tour.setDescription(invalidString);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.description.size").replaceAll(INTERPOLATE_REGEX, CoreUtils.EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, CoreUtils.EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Tour_Cost_NotNull() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.cost.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_Cost_Min() {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(BigDecimal.valueOf(-0.0001));
        tour.setTourType(VALID_TOUR_TYPE);
        Set<ConstraintViolation<Tour>> constraintViolations = validator.validate(tour);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Tour> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("tour.cost.min").replaceAll(INTERPOLATE_REGEX, CoreUtils.EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, CoreUtils.EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Tour_addHotel() throws NoSuchMethodException {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Method setHotel = Tour.class.getMethod("setHotel", Hotel.class);
        Object[] objects = {null};
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Tour>> constraintViolations = executableValidator.validateParameters(tour, setHotel, objects);
        constraintViolations.addAll(executableValidator.validateParameters(tour, setHotel, objects));
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.notNull"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_addCountry() throws NoSuchMethodException {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Method setCountry = Tour.class.getMethod("setCountry", Country.class);
        Object[] objects = {null};
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Tour>> constraintViolations = executableValidator.validateParameters(tour, setCountry, objects);
        constraintViolations.addAll(executableValidator.validateParameters(tour, setCountry, objects));
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("country.notNull"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Tour_add_remove_User() throws NoSuchMethodException {
        Tour tour = new Tour();
        tour.setPhotoPath(VALID_TOUR_PHOTO_PATH);
        tour.setStartDate(VALID_TOUR_START_DATE);
        tour.setEndDate(VALID_TOUR_END_DATE);
        tour.setDescription(VALID_TOUR_DESCRIPTION);
        tour.setCost(VALID_TOUR_COST);
        tour.setTourType(VALID_TOUR_TYPE);
        Method addUser = Tour.class.getMethod("addUser", User.class);
        Method removeUser = Tour.class.getMethod("removeUser", User.class);
        Object[] objects = {null};
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Tour>> constraintViolations = executableValidator.validateParameters(tour, addUser, objects);
        constraintViolations.addAll(executableValidator.validateParameters(tour, removeUser, objects));
        constraintViolations.addAll(executableValidator.validateParameters(tour, addUser, objects));
        Assert.assertEquals(2, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("user.notNull"),
                    constraintViolation.getMessage());
        }
    }

}
