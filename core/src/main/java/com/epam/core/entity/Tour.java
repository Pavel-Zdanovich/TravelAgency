package com.epam.core.entity;

import com.epam.core.entity.enums.TourType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Currency;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Tour")
@Table(name = "TOUR")
@AttributeOverride(name = "id", column = @Column(name = "TOUR_ID"))
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"users", "reviews"}, callSuper = false)
@ToString(exclude = {"users", "reviews"})
public class Tour extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOUR_SEQUENCE")
    @SequenceGenerator(name = "TOUR_SEQUENCE", sequenceName = "TOUR_SEQUENCE", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long tourId) {
        this.id = tourId;
    }

    @Column(name = "PHOTO_PATH")
    @NotBlank(message = "Please enter not blank photo path")
    @Getter
    @Setter
    private String photoPath;

    @Column(name = "START_DATE", nullable = false)
    @FutureOrPresent(message = "Please enter a start date corresponding to the current or future date")
    @NotNull(message = "Please enter a tour start date")
    @Getter
    @Setter
    private Timestamp startDate;

    @Column(name = "END_DATE", nullable = false)
    @Future(message = "Please enter a end date corresponding to the future date")
    @NotNull(message = "Please enter tour end date")
    @Getter
    @Setter
    private Timestamp endDate;

    @Column(name = "DESCRIPTION", length = 1000)
    @Getter
    @Setter
    private String description;

    @Column(name = "COST", nullable = false, precision = 14, scale = 4)
    @Type(type = "org.hibernate.type.BigDecimalType")
    @Currency(value = "USD", message = "Please enter the cost of the tour in currency format")
    @NotNull(message = "Please enter tour cost")
    @Getter
    @Setter
    private BigDecimal cost;

    @Column(name = "TOUR_TYPE", nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    @Size(min = 3, max = 30, message = "Please enter tour type [3, 30] characters")
    @NotNull(message = "Please enter tour type")
    @Getter
    @Setter
    private TourType tourType;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tours")
    @Getter
    private Set<User> users = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "HOTEL_ID", foreignKey = @ForeignKey(name = "TOUR_HOTEL_ID_FK"))
    @Getter
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COUNTRY_ID", foreignKey = @ForeignKey(name = "TOUR_COUNTRY_ID_FK"))
    @Getter
    private Country country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tour")
    @Getter
    private List<Review> reviews = new ArrayList<>();

    public boolean addHotel(Hotel hotel) {
        this.hotel = hotel;
        return this.hotel.getTours().add(this);
    }

    public boolean removeHotel() {
        boolean result = false;
        if (this.hotel != null) {
            result = this.hotel.getTours().remove(this);
            this.hotel = null;
        }
        return result;
    }

    public boolean addCountry(Country country) {
        this.country = country;
        return this.country.getTours().add(this);
    }

    public boolean removeCountry() {
        boolean result = false;
        if (this.country != null) {
            result = this.country.getTours().remove(this);
            this.country = null;
        }
        return result;
    }

    public boolean addUser(User user) {
        return user.addTour(this);
    }

    public boolean removeUser(User user) {
        return user.removeTour(this);
    }
}
