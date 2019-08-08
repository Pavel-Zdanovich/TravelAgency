package com.epam.travelAgency.config;

import com.epam.travelAgency.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EntityConfig.class)
public class TravelAgencyEntityConfigTest {

    private ApplicationContext entityContext;

    @Before
    public void setUp() throws Exception {
        entityContext = new AnnotationConfigApplicationContext(EntityConfig.class);
    }

    @Test
    public void getCountry() {
        Assert.assertNotNull(entityContext.getBean(Country.class));
    }

    @Test
    public void getHotel() {
        Assert.assertNotNull(entityContext.getBean(Hotel.class));
    }

    @Test
    public void getReview() {
        Assert.assertNotNull(entityContext.getBean(Review.class));
    }

    @Test
    public void getTour() {
        Assert.assertNotNull(entityContext.getBean(Tour.class));
    }

    @Test
    public void getUser() {Assert.assertNotNull(entityContext.getBean(User.class));}

}