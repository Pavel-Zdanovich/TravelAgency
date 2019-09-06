package com.epam.core.util;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneralCriterionConverter<E extends Serializable> {

    private GeneralCriterion<E> generalCriterion;

    public GeneralCriterionConverter(GeneralCriterion<E> generalCriterion) {
        this.generalCriterion = generalCriterion;
    }

    public Predicate[] toPredicates(Root<E> root, CommonAbstractCriteria criteria, CriteriaBuilder criteriaBuilder) {
        final Map<Map.Entry<String, RelationType>, Object> criterionMap = this.generalCriterion.getCriterionMap();
        List<Predicate> predicateList = new ArrayList<>(criterionMap.size());
        criterionMap.forEach((criterion, value) -> {
            switch (criterion.getValue()) {
                case BETWEEN_DOUBLES: {
                    double[] castValue = (double[]) value;
                    Expression<Double> expression = this.getExpression(root, criterion.getKey(), Double.class);
                    Predicate predicate = criteriaBuilder.between(expression, castValue[0], castValue[1]);
                    predicateList.add(predicate);
                    break;
                }
                case BETWEEN_BIGDECIMALS: {
                    BigDecimal[] castValue = (BigDecimal[]) value;
                    Expression<BigDecimal> expression = this.getExpression(root, criterion.getKey(), BigDecimal.class);
                    Predicate predicate = criteriaBuilder.between(expression, castValue[0], castValue[1]);
                    predicateList.add(predicate);
                    break;
                }
                case EQUAL: {
                    Expression<Object> expression = this.getExpression(root, criterion.getKey(), Object.class);
                    Predicate predicate = criteriaBuilder.equal(expression, value);
                    predicateList.add(predicate);
                    break;
                }
                case GREATER_THAN_DOUBLE: {
                    double castValue = (double) value;
                    Expression<Double> expression = this.getExpression(root, criterion.getKey(), Double.class);
                    Predicate predicate = criteriaBuilder.greaterThan(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case GREATER_THAN_BIGDECIMAL: {
                    BigDecimal castValue = (BigDecimal) value;
                    Expression<BigDecimal> expression = this.getExpression(root, criterion.getKey(), BigDecimal.class);
                    Predicate predicate = criteriaBuilder.greaterThan(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case GREATER_THAN_OR_EQUAL_TO_DOUBLE: {
                    double castValue = (double) value;
                    Expression<Double> expression = getExpression(root, criterion.getKey(), Double.class);
                    Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case GREATER_THAN_OR_EQUAL_TO_BIGDECIMAL: {
                    BigDecimal castValue = (BigDecimal) value;
                    Expression<BigDecimal> expression = getExpression(root, criterion.getKey(), BigDecimal.class);
                    Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case LESS_THAN_DOUBLE: {
                    double castValue = (double) value;
                    Expression<Double> expression = getExpression(root, criterion.getKey(), Double.class);
                    Predicate predicate = criteriaBuilder.lessThan(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case LESS_THAN_BIGDECIMAL: {
                    BigDecimal castValue = (BigDecimal) value;
                    Expression<BigDecimal> expression = getExpression(root, criterion.getKey(), BigDecimal.class);
                    Predicate predicate = criteriaBuilder.lessThan(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case LESS_THAN_OR_EQUAL_TO_DOUBLE: {
                    double castValue = (double) value;
                    Expression<Double> expression = getExpression(root, criterion.getKey(), Double.class);
                    Predicate predicate = criteriaBuilder.lessThanOrEqualTo(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                case LESS_THAN_OR_EQUAL_TO_BIGDECIMAL: {
                    BigDecimal castValue = (BigDecimal) value;
                    Expression<BigDecimal> expression = getExpression(root, criterion.getKey(), BigDecimal.class);
                    Predicate predicate = criteriaBuilder.lessThanOrEqualTo(expression, castValue);
                    predicateList.add(predicate);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown type of relationship of criterion and value : " + criterion.getValue());
                }
            }
        });
        Predicate[] predicates = new Predicate[predicateList.size()];
        return predicateList.toArray(predicates);
    }

    private <Y> Expression<Y> getExpression(Root<E> root, String fieldName, Class<Y> classCast) {
        Class<E> entityClass = root.getModel().getBindableJavaType();
        if (EntityAnalyzer.containsField(entityClass, fieldName)) {
            return root.get(fieldName);
        } else {
            List<Field> fieldList = EntityAnalyzer.getDependentEntitiesField(entityClass);
            String dependentEntityName = null;
            for (Field field : fieldList) {
                if (EntityAnalyzer.containsField(field.getType(), fieldName)) {
                    dependentEntityName = field.getName();
                }
            }
            if (dependentEntityName != null) {
                return root.get(dependentEntityName).get(fieldName);
            } else {
                throw new IllegalArgumentException("impossible to query entity : " + entityClass.getSimpleName() + " by : " + fieldName,
                        new NoSuchFieldException("Specified entity class : " + entityClass.getSimpleName() + " does not have a field : " + fieldName));
            }
        }
    }

}
