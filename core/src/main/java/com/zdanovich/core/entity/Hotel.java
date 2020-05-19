package com.zdanovich.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.zdanovich.core.entity.AbstractEntity.ID;
import static com.zdanovich.core.entity.Feature.FEATURE_ID;
import static com.zdanovich.core.entity.Hotel.HOTELS;
import static com.zdanovich.core.entity.Hotel.HOTEL_ID;
import static com.zdanovich.core.entity.Hotel.NAME;
import static com.zdanovich.core.entity.Hotel.WEBSITE;

@Entity
@Table(name = HOTELS, uniqueConstraints = {@UniqueConstraint(name = "HOTEL_NAME_UNIQUE", columnNames = NAME),
        @UniqueConstraint(name = "HOTEL_WEBSITE_UNIQUE", columnNames = WEBSITE)})
@AttributeOverride(name = ID, column = @Column(name = HOTEL_ID))
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tours", callSuper = false)
@ToString(exclude = "tours")
@Validated
public class Hotel extends AbstractEntity {

    public static final String HOTELS = "HOTELS";
    public static final String HOTEL_ID = "HOTEL_ID";
    public static final String NAME = "NAME";
    public static final String STARS = "STARS";
    public static final String WEBSITE = "WEBSITE";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String WEBSITE_EXAMPLE = " (https://www.example.com/path-to_file, http://www.example-1.en)";

    @Column(name = NAME, nullable = false, length = 50)
    @NotEmpty(message = "{hotel.name.notEmpty}")
    @Size(min = 2, max = 50, message = "{hotel.name.size}")
    @Getter
    @Setter
    private String name;

    @Column(name = STARS, nullable = false, precision = 1)
    @NotNull(message = "{hotel.stars.notNull}")
    @Min(value = 1, message = "{hotel.stars.min}")
    @Max(value = 5, message = "{hotel.stars.max}")
    @Getter
    @Setter
    private Short stars;

    @Column(name = WEBSITE, nullable = false, length = 100)
    @Size(min = 18, max = 100, message = "{hotel.website.size}")
    @URL(message = "{hotel.website.pattern}" + WEBSITE_EXAMPLE)
    @Getter
    @Setter
    private String website;

    @Column(name = LATITUDE, nullable = false, precision = 9, scale = 7)
    @NotNull(message = "{hotel.latitude.notNull}")
    @DecimalMin(value = "-90.0000000", message = "{hotel.latitude.min}")
    @DecimalMax(value = "90.0000000", message = "{hotel.latitude.max}")
    @Getter
    @Setter
    private BigDecimal latitude;

    @Column(name = LONGITUDE, nullable = false, precision = 10, scale = 7)
    @NotNull(message = "{hotel.longitude.notNull}")
    @DecimalMin(value = "-180.0000000", message = "{hotel.longitude.min}")
    @DecimalMax(value = "180.0000000", message = "{hotel.longitude.max}")
    @Getter
    @Setter
    private BigDecimal longitude;

    @ManyToMany(targetEntity = Feature.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "HOTELS_FEATURES",
            joinColumns = @JoinColumn(name = HOTEL_ID, referencedColumnName = HOTEL_ID,
                    foreignKey = @ForeignKey(name = "HOTEL_FEATURE_FEATURE_ID_FK")),
            foreignKey = @ForeignKey(name = "HOTEL_FEATURE_FEATURE_ID_FK"),
            inverseJoinColumns = @JoinColumn(name = FEATURE_ID, referencedColumnName = FEATURE_ID,
                    foreignKey = @ForeignKey(name = "HOTEL_FEATURE_HOTEL_ID_FK")),
            inverseForeignKey = @ForeignKey(name = "HOTEL_FEATURE_HOTEL_ID_FK"))
    @Getter
    private Set<Feature> features = new HashSet<>();

    @OneToMany(targetEntity = Tour.class, cascade = CascadeType.ALL)
    @JoinColumn(name = HOTEL_ID)
    @Getter
    private Set<Tour> tours = new HashSet<>();

    public boolean addFeature(@NotNull(message = "{feature.notNull}") @Valid Feature feature) {
        return this.features.add(feature) ? feature.getHotels().add(this) : false;
    }

    public Optional<Feature> getFeature(String featureName) {
        return this.features.stream().filter(feature -> feature.getName().equals(featureName)).findFirst();
    }

    public boolean removeFeature(@NotEmpty(message = "{feature.name.notEmpty}") String featureName) {
        return this.features.removeIf(feature -> feature.getName().equals(featureName));
    }

    public boolean removeFeature(@NotNull(message = "{feature.notNull}") @Valid Feature feature) {
        return this.features.remove(feature) ? feature.getHotels().remove(this) : false;
    }

    public boolean containsFeature(@NotEmpty(message = "{feature.name.notEmpty}") String featureName) {
        return this.features.stream().anyMatch(feature -> feature.getName().equals(featureName));
    }

    public boolean containsFeature(@NotNull(message = "{feature.notNull}") @Valid Feature feature) {
        return this.features.contains(feature);
    }
}
