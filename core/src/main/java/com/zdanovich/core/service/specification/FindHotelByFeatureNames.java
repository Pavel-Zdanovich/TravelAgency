package com.zdanovich.core.service.specification;

import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.metamodel.Feature_;
import com.zdanovich.core.entity.metamodel.Hotel_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FindHotelByFeatureNames implements Specification<Hotel> {

    private final Set<String> featureNames;

    public FindHotelByFeatureNames(@NotNull Collection<String> featureNames) {
        this.featureNames = new HashSet<>(featureNames);
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
