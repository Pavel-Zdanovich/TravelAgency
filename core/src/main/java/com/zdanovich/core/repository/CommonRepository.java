package com.zdanovich.core.repository;

import com.zdanovich.core.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity, ID extends Serializable> extends JpaRepository<E, ID> {
}