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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = Country.COUNTRIES, uniqueConstraints = @UniqueConstraint(name = "COUNTRY_NAME_UNIQUE", columnNames = Country.NAME))
@AttributeOverride(name = AbstractEntity.ID, column = @Column(name = Country.COUNTRY_ID))
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Validated
public class Country extends AbstractEntity<Long> {

    public static final String COUNTRIES = "COUNTRIES";
    public static final String COUNTRY_ID = "COUNTRY_ID";
    public static final String NAME = "NAME";

    @Column(name = NAME, nullable = false, length = 50)
    @NotEmpty(message = "{country.name.notEmpty}")
    @Size(min = 2, max = 50, message = "{country.name.size}")
    @Getter
    @Setter
    private String name;
}
