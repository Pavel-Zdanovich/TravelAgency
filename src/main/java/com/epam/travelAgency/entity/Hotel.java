package com.epam.travelAgency.entity;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UniqueElements;
import org.postgis.PGgeometry;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "Hotel")
@Table(name = "hotels", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "hotel_name_unique", columnNames = "name"),
                                            @UniqueConstraint(name = "hotel_website_unique", columnNames = "website")})
@TypeDef(name = "types_of_features", typeClass = EnumArrayType.class, defaultForType = Feature[].class,
        parameters = @org.hibernate.annotations.Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "types_of_features"))
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Hotel extends TravelAgencyEntity {

    @Column(name = "hotel_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "Please enter hotel id")
    private long hotelId;

    @Column(name = "name", length = 50)
    @Size(min = 2, max = 50, message = "Please enter hotel name [2, 50] characters")
    @NotEmpty(message = "Please enter hotel name")
    private String name;

    @Column(name = "stars")
    @Range(min = 1, max = 5, message = "Please enter hotel stars in the range [1, 5]")
    @NotNull(message = "Please enter hotel stars")
    private int stars;

    @Column(name = "website", length = 75)
    @URL
    @Pattern(regexp = "^(http|https)://", message = "Please enter hotel website starting in 'http(s)://www.' and ending in '.com'")
    @Pattern(regexp = "(/|(www(\\.))).{1,75}(\\.)com$", message = "Please enter hotel website starting in 'http(s)://www.' and ending in '.com'")
    @NotNull(message = "Please enter hotel website")
    private java.net.URL website;

    @Column(name = "coordinate", columnDefinition = "GEOMETRY(Point, 4326)")
    private PGgeometry coordinate;

    @Column(name = "types_of_features", columnDefinition = "types_of_features[]")
    @UniqueElements(message = "Please enter unique features")
    private Feature[] features;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tour> tours = new ArrayList<>();

    public boolean addTour(Tour tour) {
        tour.setHotel(this);
        return tours.add(tour);
    }

    public boolean removeTour(Tour tour) {
        tour.setHotel(null);
        return tours.remove(tour);
    }

}
