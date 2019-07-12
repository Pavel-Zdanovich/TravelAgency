package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Country {

    private long countryId;
    private String name;

    public Country() {
        this.countryId = UUID.randomUUID().timestamp();
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
