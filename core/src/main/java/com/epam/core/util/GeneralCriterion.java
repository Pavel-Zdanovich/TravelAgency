package com.epam.core.util;

import java.io.Serializable;
import java.util.*;

public class GeneralCriterion<E extends Serializable> {

    private Map<Map.Entry<String, RelationType>, Object> criterionMap;//SingularAttribute<E, ?>

    public GeneralCriterion() {
        this.criterionMap = new HashMap<>();
    }

    public GeneralCriterion(String name, RelationType type, Object value) {
        this.criterionMap = new HashMap<>();
        this.addCriterion(name, type, value);
    }

    Map<Map.Entry<String, RelationType>, Object> getCriterionMap() {
        return criterionMap;
    }

    public int size() {
        return this.criterionMap.size();
    }

    public boolean isEmpty() {
        return this.criterionMap.isEmpty();
    }

    public boolean containsKey(String name, RelationType type) {
        return this.defineEntryNumber(name, type) != -1;
    }

    public boolean containsValue(Object value) {
        return this.criterionMap.containsValue(value);
    }

    public void addCriterion(String name, RelationType type, Object value) {
        this.criterionMap.put(new AbstractMap.SimpleEntry<>(name, type), value);
    }

    public Object get(String name, RelationType type) {
        int entryNumber = this.defineEntryNumber(name, type);
        if (entryNumber != -1) {
            return this.criterionMap.values().toArray()[entryNumber];
        } else {
            return null;
        }
    }

    public Map.Entry<String, RelationType> remove(String name, RelationType type) {
        int entryNumber = this.defineEntryNumber(name, type);
        Map.Entry<String, RelationType> removedEntry = null;
        if (entryNumber != -1) {
            removedEntry = this.criterionMap.keySet().stream().skip(entryNumber).findFirst().orElse(null);
            this.criterionMap.remove(removedEntry);
        }
        return removedEntry;
    }

    public void clear() {
        this.criterionMap.clear();
    }

    public Set<Map.Entry<String, RelationType>> keySet() {
        return this.criterionMap.keySet();
    }

    public Collection<Object> values() {
        return this.criterionMap.values();
    }

    public Set<Map.Entry<Map.Entry<String, RelationType>, Object>> entrySet() {
        return this.criterionMap.entrySet();
    }

    private int defineEntryNumber(String name, RelationType type) {
        int result = -1;
        for (Map.Entry<String, RelationType> entry: this.criterionMap.keySet()) {
            if (entry.getKey().equals(name) && entry.getValue().equals(type)) {
                result++;
                break;
            } else {
                result++;
            }
        }
        return result;
    }

}
