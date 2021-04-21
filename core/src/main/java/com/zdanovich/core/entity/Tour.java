package com.zdanovich.core.entity;

import com.zdanovich.core.entity.enums.TourType;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Tour.TOURS)
@AttributeOverride(name = AbstractEntity.ID, column = @Column(name = Tour.TOUR_ID))
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users"}, callSuper = false)
@ToString(exclude = {"users"})
@Validated
public class Tour extends AbstractEntity<Long> {

    public static final String TOURS = "TOURS";
    public static final String TOUR_ID = "TOUR_ID";
    public static final String PHOTO_PATH = "PHOTO_PATH";
    public static final String START_DATE = "START_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String COST = "COST";
    public static final String TOUR_TYPE = "TOUR_TYPE";

    @Column(name = PHOTO_PATH)
    @NotEmpty(message = "{tour.photoPath.notEmpty}")
    @Getter
    @Setter
    private String photoPath;

    @Column(name = START_DATE, nullable = false)
    @NotNull(message = "{tour.startDate.notNull}")
    @FutureOrPresent(message = "{tour.startDate.futureOrPresent}")
    @Getter
    @Setter
    private Timestamp startDate;

    @Column(name = END_DATE, nullable = false)
    @NotNull(message = "{tour.endDate.notNull}")
    @Future(message = "{tour.endDate.future}")
    @Getter
    @Setter
    private Timestamp endDate;

    @Column(name = DESCRIPTION, length = 1000)
    @NotEmpty(message = "{tour.description.notEmpty}")
    @Size(max = 1000, message = "{tour.description.size}")
    @Getter
    @Setter
    private String description;

    @Column(name = COST, nullable = false, precision = 14, scale = 4)
    @NotNull(message = "{tour.cost.notNull}")
    @DecimalMin(value = "0.0000", message = "{tour.cost.min}")
    @Getter
    @Setter
    private BigDecimal cost;

    @Column(name = TOUR_TYPE, nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    @Getter
    @Setter
    private TourType tourType;

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL, mappedBy = "tours")
    @Getter
    private Set<User> users = new HashSet<>();

    @ManyToOne(targetEntity = Hotel.class, cascade = CascadeType.ALL)
    @JoinColumn(name = Hotel.HOTEL_ID, foreignKey = @ForeignKey(name = "TOUR_HOTEL_ID_FK"))
    @Getter
    private Hotel hotel;

    @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL)
    @JoinColumn(name = Country.COUNTRY_ID, foreignKey = @ForeignKey(name = "TOUR_COUNTRY_ID_FK"))
    @Getter
    private Country country;

    public boolean addUser(@NotNull(message = "{user.notNull}") User user) {
        return this.users.add(user) && user.getTours().add(this);
    }

    public boolean removeUser(@NotNull(message = "{user.notNull}") User user) {
        return this.users.remove(user) && user.getTours().remove(this);
    }

    public void setHotel(@NotNull(message = "{hotel.notNull}") Hotel hotel) {
        this.hotel = hotel;
    }

    public void setCountry(@NotNull(message = "{country.notNull}") Country country) {
        this.country = country;
    }
}
