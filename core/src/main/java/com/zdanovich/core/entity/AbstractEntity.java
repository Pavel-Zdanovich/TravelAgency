package com.zdanovich.core.entity;

import com.zdanovich.core.entity.generator.Identifiable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity implements Identifiable, Serializable {

    public static final String ENTITY_GENERATOR = "ENTITY_GENERATOR";
    public static final String SEQUENCE_IDENTIFIER_GENERATOR_PATH = "com.zdanovich.core.entity.generator.SequenceIdentifierGenerator";
    public static final String ID = "id";

    @Column(name = ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = ENTITY_GENERATOR)
    @GenericGenerator(name = ENTITY_GENERATOR, strategy = SEQUENCE_IDENTIFIER_GENERATOR_PATH)
    protected Long id;

}
