package com.epam.core.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Country")
@Table(name = "COUNTRIES", uniqueConstraints = @UniqueConstraint(name = "COUNTRY_NAME_UNIQUE", columnNames = "NAME"))
@AttributeOverride(name = "id", column = @Column(name = "COUNTRY_ID"))
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tours", callSuper = false)
@ToString(exclude = "tours")
public class Country extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_SEQUENCE")
    @SequenceGenerator(name = "COUNTRY_SEQUENCE", sequenceName = "COUNTRY_SEQUENCE", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long countryId) {
        this.id = countryId;
    }

    @Column(name = "NAME", nullable = false, length = 50)
    @Size(min = 3, max = 50, message = "Please enter country name [3, 50] characters")
    @NotEmpty(message = "Please enter country name")
    @Getter
    @Setter
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "country")
    private Set<Tour> tours = new HashSet<>();

    Set<Tour> getTours() {
        return this.tours;
    }

}
