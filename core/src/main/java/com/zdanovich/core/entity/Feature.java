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
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@EqualsAndHashCode(exclude = "hotels", callSuper = false)
@ToString(exclude = "hotels")
@Validated
public class Feature extends AbstractEntity {

    public static final String FEATURES = "FEATURES";
    public static final String FEATURE_ID = "FEATURE_ID";
    public static final String NAME = "NAME";

    @Column(name = NAME, nullable = false, length = 30)
    @NotEmpty(message = "{feature.name.notEmpty}")
    @Size(min = 3, max = 30, message = "{feature.name.size}")
    @Getter
    @Setter
    private String name;

    @ManyToMany(targetEntity = Hotel.class, cascade = CascadeType.ALL)
    @JoinTable(name = "HOTELS_FEATURES",
            joinColumns = @JoinColumn(name = FEATURE_ID, referencedColumnName = FEATURE_ID,
                    foreignKey = @ForeignKey(name = "HOTEL_FEATURE_FEATURE_ID_FK")),
            foreignKey = @ForeignKey(name = "HOTEL_FEATURE_FEATURE_ID_FK"),
            inverseJoinColumns = @JoinColumn(name = Hotel.HOTEL_ID, referencedColumnName = Hotel.HOTEL_ID,
                    foreignKey = @ForeignKey(name = "HOTEL_FEATURE_HOTEL_ID_FK")),
            inverseForeignKey = @ForeignKey(name = "HOTEL_FEATURE_HOTEL_ID_FK"))
    @Getter
    private Set<Hotel> hotels = new HashSet<>();
}
