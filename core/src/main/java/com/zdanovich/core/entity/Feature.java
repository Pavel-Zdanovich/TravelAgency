package com.zdanovich.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Feature.FEATURES, uniqueConstraints = @UniqueConstraint(name = "FEATURE_NAME_UNIQUE", columnNames = Feature.NAME))
@AttributeOverride(name = AbstractEntity.ID, column = @Column(name = Feature.FEATURE_ID))
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Validated
public class Feature extends AbstractEntity<Long> {

    public static final String FEATURES = "FEATURES";
    public static final String FEATURE_ID = "FEATURE_ID";
    public static final String NAME = "NAME";

    @Column(name = NAME, nullable = false, length = 30)
    @NotEmpty(message = "{feature.name.notEmpty}")
    @Size(min = 3, max = 30, message = "{feature.name.size}")
    @Getter
    @Setter
    private String name;
}
