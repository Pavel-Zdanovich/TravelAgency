package com.zdanovich.core.entity.generator;

import java.io.Serializable;

@FunctionalInterface
public interface Identifiable<ID extends Serializable > {

    ID getId();

}
