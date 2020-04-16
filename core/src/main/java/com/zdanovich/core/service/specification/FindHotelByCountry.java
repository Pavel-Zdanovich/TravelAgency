package com.zdanovich.core.service.specification;

import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.metamodel.Country_;
import com.zdanovich.core.entity.metamodel.Hotel_;
import com.zdanovich.core.entity.metamodel.Tour_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class FindHotelByCountry implements Specification<Hotel> {

    private final String countryName;

    public FindHotelByCountry(@NotEmpty(message = "{country.name.notEmpty}")
                              @Size(min = 2, max = 50, message = "{country.name.size}") String countryName) {
        this.countryName = countryName;
    }

    public FindHotelByCountry(@Valid Country country) {
        this.countryName = country.getName();
    }

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<Tour> tourPath = root.get(Hotel_.TOURS);
        Path<Country> countryPath = tourPath.get(Tour_.COUNTRY);
        Expression<Country> countryExpression = countryPath.get(Country_.NAME);
        return criteriaBuilder.equal(countryExpression, this.countryName);
    }
}
