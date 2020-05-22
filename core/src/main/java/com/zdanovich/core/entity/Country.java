package com.zdanovich.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Country.COUNTRIES, uniqueConstraints = @UniqueConstraint(name = "COUNTRY_NAME_UNIQUE", columnNames = Country.NAME))
@AttributeOverride(name = AbstractEntity.ID, column = @Column(name = Country.COUNTRY_ID))
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tours", callSuper = false)
@ToString(exclude = "tours")
@Validated
public class Country extends AbstractEntity {

    public static final String COUNTRIES = "COUNTRIES";
    public static final String COUNTRY_ID = "COUNTRY_ID";
    public static final String NAME = "NAME";

    @Column(name = NAME, nullable = false, length = 50)
    @NotEmpty(message = "{country.name.notEmpty}")
    @Size(min = 2, max = 50, message = "{country.name.size}")
    @Getter
    @Setter
    private String name;

    @OneToMany(targetEntity = Tour.class, cascade = CascadeType.ALL)
    @JoinColumn(name = COUNTRY_ID)
    @Getter
    private Set<Tour> tours = new HashSet<>();
}
