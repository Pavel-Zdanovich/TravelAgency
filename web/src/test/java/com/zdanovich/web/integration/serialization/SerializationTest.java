package com.zdanovich.web.integration.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.web.security.Authorities;
import com.zdanovich.web.serialization.SerializationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collection;

@Test
@ContextConfiguration(classes = SerializationConfiguration.class)
@WebAppConfiguration
public class SerializationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void serializeBigDecimal() throws JsonProcessingException {
        BigDecimal bigDecimal = BigDecimal.valueOf(123.000);
        String stringBigDecimal = objectMapper.writeValueAsString(bigDecimal);
        String string = stringBigDecimal.replaceAll("\\[\"java.math.BigDecimal\"\\,(\\d+\\.\\d+)\\]", "$1");

        Assert.assertEquals(bigDecimal, new BigDecimal(string));

        BigDecimal readBigDecimal = objectMapper.readValue(string, BigDecimal.class);
        Assert.assertEquals(bigDecimal, readBigDecimal);
    }

    @Test
    public void serializeDouble() throws JsonProcessingException {
        Double aDouble = Double.valueOf(123.000);
        String string = objectMapper.writeValueAsString(aDouble);
        Assert.assertEquals(aDouble, Double.valueOf(string));
    }

    @Test
    public void authorities() {
        Collection<? extends GrantedAuthority> auth = Authorities.getFor(UserRole.USER);

        Assert.assertFalse(auth.isEmpty());
        Assert.assertNotNull(auth.stream().findFirst().get());
    }
}