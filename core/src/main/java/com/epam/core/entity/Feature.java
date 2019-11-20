package com.epam.core.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Feature")
@Table(name = "FEATURES", uniqueConstraints = @UniqueConstraint(name = "FEATURE_NAME_UNIQUE", columnNames = "NAME"))
@AttributeOverride(name = "id", column = @Column(name = "FEATURE_ID"))
@NoArgsConstructor
@EqualsAndHashCode(exclude = "hotels", callSuper = false)
@ToString(exclude = "hotels")
public class Feature extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEATURE_SEQUENCE")
    @SequenceGenerator(name = "FEATURE_SEQUENCE", sequenceName = "FEATURE_SEQUENCE", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long featureId) {
        this.id = featureId;
    }

    @Column(name = "NAME", nullable = false, length = 30)
    @Size(min = 3, max = 30, message = "Please enter feature name [3, 30] characters")
    @NotNull(message = "Please enter tour type")
    @Getter
    @Setter
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "HOTELS_FEATURES",
            joinColumns = @JoinColumn(name = "FEATURE_ID", referencedColumnName = "FEATURE_ID",
                    foreignKey = @ForeignKey(name = "HOTEL_FEATURE_FEATURE_ID_FK")),
            foreignKey = @ForeignKey(name = "HOTEL_FEATURE_FEATURE_ID_FK"),
            inverseJoinColumns = @JoinColumn(name = "HOTEL_ID", referencedColumnName = "HOTEL_ID",
                    foreignKey = @ForeignKey(name = "HOTEL_FEATURE_HOTEL_ID_FK")),
            inverseForeignKey = @ForeignKey(name = "HOTEL_FEATURE_HOTEL_ID_FK"))
    private Set<Hotel> hotels = new HashSet<>();

    Set<Hotel> getHotels() {
        return this.hotels;
    }

}
