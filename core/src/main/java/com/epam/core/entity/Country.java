package com.epam.core.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "Country")
@Table(name = "countries", schema = "public", uniqueConstraints = @UniqueConstraint(name = "country_name_unique", columnNames = "name"))
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Country implements Serializable {

    @Column(name = "country_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator_sequence", initialValue = 1_000_000, allocationSize = 9_999_999)
    @NotNull(message = "Please enter country id")
    private long countryId;

    @Column(name = "name", length = 50)
    @Size(min = 3, max = 50, message = "Please enter country name [3, 50] characters")
    @NotEmpty(message = "Please enter country name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country", orphanRemoval = true)
    private List<Tour> tours = new ArrayList<>();

    public boolean addTour(Tour tour) {
        tour.setCountry(this);
        return tours.add(tour);
    }

    public boolean removeTour(Tour tour) {
        tour.setCountry(null);
        return tours.remove(tour);
    }

}
