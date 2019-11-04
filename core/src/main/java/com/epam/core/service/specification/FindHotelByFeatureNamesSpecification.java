package com.epam.core.service.specification;

import com.epam.core.entity.Feature;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.metamodel.Feature_;
import com.epam.core.entity.metamodel.Hotel_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Set;

public class FindHotelByFeatureNamesSpecification implements Specification<Hotel> {

    private final Set<String> featureNames;

    public FindHotelByFeatureNamesSpecification(Set<String> featureNames) {
        this.featureNames = featureNames;
    }

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Join<Hotel, Feature> hotelFeatureSetJoin = root.join(Hotel_.FEATURES, JoinType.INNER);
        Predicate featureNamesIn = hotelFeatureSetJoin.get(Feature_.NAME).in(featureNames);
        Expression<Long> count = criteriaBuilder.count(root.get(Hotel_.HOTEL_ID));
        Predicate countEqual = criteriaBuilder.equal(count, Long.valueOf(featureNames.size()));
        criteriaQuery.groupBy(root.get(Hotel_.HOTEL_ID), root.get(Hotel_.NAME), root.get(Hotel_.WEBSITE),
                root.get(Hotel_.STARS), root.get(Hotel_.LATITUDE), root.get(Hotel_.LONGITUDE));
        criteriaQuery.having(countEqual);
        return featureNamesIn;
    }
}
