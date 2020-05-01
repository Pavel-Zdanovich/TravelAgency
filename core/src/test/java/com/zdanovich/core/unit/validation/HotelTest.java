package com.zdanovich.core.unit.validation;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Set;

import static com.zdanovich.core.utils.Utils.EMPTY_STRING;

public class HotelTest extends ValidationTest {

    public static final String VALID_HOTEL_NAME = "ValidHotelName";
    public static final Short VALID_HOTEL_STARS = Short.valueOf("5");
    public static final String VALID_HOTEL_WEBSITE = "https://www.valid-hotel-website.com/path-to_file";
    public static final BigDecimal VALID_HOTEL_LATITUDE = BigDecimal.valueOf(89.1234567);
    public static final BigDecimal VALID_HOTEL_LONGITUDE = BigDecimal.valueOf(179.1234567);

    @Test
    public void testValidation_Hotel_Name_NotEmpty() {
        Hotel hotel = new Hotel();
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertNotNull(constraintViolation);
            Assert.assertEquals(resourceBundle.getString("hotel.name.notEmpty"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Name_Size_Min() {
        Hotel hotel = new Hotel();
        hotel.setName("A");
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.name.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Hotel_Name_Size_Max() {
        Hotel hotel = new Hotel();
        hotel.setName("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.name.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Hotel_Stars_NotNull() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.stars.notNull"), constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Stars_Min() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(Short.valueOf("0"));
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.stars.min").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Hotel_Stars_Max() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(Short.valueOf("6"));
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.stars.max").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Hotel_Website_Size_Min() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite("http://www.a.com");
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.website.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Hotel_Website_Size_Max() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite("https://www.abcd.com/123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567891234567890");
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.website.size").replaceAll(INTERPOLATE_REGEX, EMPTY_STRING),
                    constraintViolation.getMessage().replaceAll(NUMBER_REGEX, EMPTY_STRING));
        }
    }

    @Test
    public void testValidation_Hotel_Website_Pattern() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite("invalid-hotel-website");
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.website.pattern").concat(Hotel.WEBSITE_EXAMPLE),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Latitude_NotNull() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.latitude.notNull"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Latitude_Min() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(BigDecimal.valueOf(-90.0000001));
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.latitude.min"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Latitude_Max() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(BigDecimal.valueOf(90.0000001));
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.latitude.max"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Longitude_NotNull() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.longitude.notNull"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Longitude_Min() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(BigDecimal.valueOf(-180.0000001));
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.longitude.min"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_Longitude_Max() {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(BigDecimal.valueOf(180.0000001));
        Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
        Assert.assertEquals(1, constraintViolations.size());
        for (ConstraintViolation<Hotel> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("hotel.longitude.max"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_add_remove_contains_Feature_By_Name() throws NoSuchMethodException {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Method removeFeature = Hotel.class.getMethod("removeFeature", String.class);
        Method containsFeature = Hotel.class.getMethod("containsFeature", String.class);
        Object[] objects = { "" };
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Hotel>> constraintViolations = executableValidator.validateParameters(hotel, removeFeature, objects);
        constraintViolations.addAll(executableValidator.validateParameters(hotel, containsFeature, objects));
        Assert.assertEquals(2, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("feature.name.notEmpty"),
                    constraintViolation.getMessage());
        }
    }

    @Test
    public void testValidation_Hotel_add_remove_contains_Feature() throws NoSuchMethodException {
        Hotel hotel = new Hotel();
        hotel.setName(VALID_HOTEL_NAME);
        hotel.setStars(VALID_HOTEL_STARS);
        hotel.setWebsite(VALID_HOTEL_WEBSITE);
        hotel.setLatitude(VALID_HOTEL_LATITUDE);
        hotel.setLongitude(VALID_HOTEL_LONGITUDE);
        Method addFeature = Hotel.class.getMethod("addFeature", Feature.class);
        Method removeFeature = Hotel.class.getMethod("removeFeature", Feature.class);
        Method containsFeature = Hotel.class.getMethod("containsFeature", Feature.class);
        Object[] objects = { null };
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<Hotel>> constraintViolations = executableValidator.validateParameters(hotel, addFeature, objects);
        constraintViolations.addAll(executableValidator.validateParameters(hotel, removeFeature, objects));
        constraintViolations.addAll(executableValidator.validateParameters(hotel, containsFeature, objects));
        Assert.assertEquals(3, constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Assert.assertEquals(resourceBundle.getString("feature.notNull"),
                    constraintViolation.getMessage());
        }
    }

}
