package com.epam.core.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Hotel")
@Table(name = "HOTEL", uniqueConstraints = {@UniqueConstraint(name = "HOTEL_NAME_UNIQUE", columnNames = "NAME"),
        @UniqueConstraint(name = "HOTEL_WEBSITE_UNIQUE", columnNames = "WEBSITE")})
@AttributeOverride(name = "id", column = @Column(name = "HOTEL_ID"))
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tours", callSuper = false)
@ToString(exclude = "tours")
public class Hotel extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOTEL_SEQUENCE")
    @SequenceGenerator(name = "HOTEL_SEQUENCE", sequenceName = "HOTEL_SEQUENCE", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long hotelId) {
        this.id = hotelId;
    }

    @Column(name = "NAME", nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "Please enter hotel name [2, 50] characters")
    @NotEmpty(message = "Please enter hotel name")
    @Getter
    @Setter
    private String name;

    @Column(name = "STARS", nullable = false, precision = 1)
    @Range(min = 1, max = 5, message = "Please enter hotel stars in the range [1, 5]")
    @NotNull(message = "Please enter hotel stars")
    @Getter
    @Setter
    private Short stars;

    @Column(name = "WEBSITE", nullable = false, length = 75)
    @Size(max = 75, message = "Please enter hotel website 75 characters long")
    @Pattern(regexp = "https?\\:\\/\\/www\\.\\w+\\.[a-z]{2,3}($|\\/[\\w\\/\\-]+\\/$)", message = "Please enter hotel website starting a protocol and ending with a domain")
    @NotNull(message = "Please enter hotel website")
    @Getter
    @Setter
    private String website;

    @Column(name = "LATITUDE", nullable = false, precision = 9, scale = 7)
    @Size(min = -90, max = 90, message = "Latitude coordinate of hotel must be in range [-90,+90]")
    @NotNull(message = "Please enter latitude coordinate of hotel")
    @Getter
    @Setter
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", nullable = false, precision = 10, scale = 7)
    @Size(min = -180, max = 180, message = "Longitude coordinate of hotel must be in range [-180,+180]")
    @NotNull(message = "Please enter longitude coordinate of hotel")
    @Getter
    @Setter
    private BigDecimal longitude;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "hotels")
    @Getter
    private Set<Feature> features = new HashSet<>();

    @OneToMany(targetEntity = Tour.class, cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Tour> tours = new ArrayList<>();

    public boolean addFeature(Feature feature) {
        feature.getHotels().add(this);
        return this.features.add(feature);
    }

    public boolean removeFeature(Feature feature) {
        feature.getHotels().remove(this);
        return this.features.remove(feature);
    }

    List<Tour> getTours() {
        return this.tours;
    }

}
