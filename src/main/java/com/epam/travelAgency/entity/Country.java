package com.epam.travelAgency.entity;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Country extends Entity {

    private long countryId;
    private String name;

    public Country() {
        this.countryId = Generators.timeBasedGenerator().generate().timestamp();
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        } else {
            Country country = (Country) o;
            return getCountryId() == country.getCountryId() &&
                    getName().equals(country.getName());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryId(), getName());
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("countryId=").append(countryId)
                .append(", name='").append(name).append('\'').append('}').toString();
    }
}
