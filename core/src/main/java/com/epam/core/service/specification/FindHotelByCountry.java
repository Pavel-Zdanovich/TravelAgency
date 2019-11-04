package com.epam.core.service.specification;

import com.epam.core.entity.Country;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.Tour;
import com.epam.core.entity.metamodel.Country_;
import com.epam.core.entity.metamodel.Hotel_;
import com.epam.core.entity.metamodel.Tour_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class FindHotelByCountry implements Specification<Hotel> {

    private final String countryName;

    public FindHotelByCountry(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<Tour> tourPath = root.get(Hotel_.TOURS);
        Path<Country> countryPath = tourPath.get(Tour_.COUNTRY);
        Expression<Country> countryExpression = countryPath.get(Country_.NAME);
        return criteriaBuilder.equal(countryExpression, countryName);
    }
}
