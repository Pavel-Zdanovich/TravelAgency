package com.zdanovich.core.repository;

import com.zdanovich.core.entity.generator.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CommonRepository<ID extends Serializable, E extends Identifiable<ID>> extends JpaRepository<E, ID> {
}
