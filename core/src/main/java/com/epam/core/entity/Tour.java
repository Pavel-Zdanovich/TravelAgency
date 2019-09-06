package com.epam.core.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Currency;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "Tour")
@Table(name = "tours", schema = "public")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class, defaultForType = TourType.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tour implements Serializable {

    @Column(name = "tour_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator_sequence", initialValue = 1_000_000, allocationSize = 9_999_999)
    @NotNull(message = "Please enter tour id")
    private long tourId;

    @Column(name = "photo")
    private String photoPath;

    @Column(name = "start_date")
    @FutureOrPresent(message = "Please enter a start date corresponding to the current or future date")
    @NotNull(message = "Please enter a tour start date")
    private Timestamp startDate;

    @Column(name = "end_date")
    @Future(message = "Please enter a end date corresponding to the future date")
    @NotNull(message = "Please enter tour end date")
    private Timestamp endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "cost", precision = 19, scale = 4)
    @Type(type = "org.hibernate.type.BigDecimalType")
    @Currency(value = "USD", message = "Please enter the cost of the tour in currency format")
    @NotNull(message = "Please enter tour cost")
    private BigDecimal cost;//double

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tour_type", columnDefinition = "types_of_tours")
    @Type(type = "pgsql_enum")
    @NotNull(message = "Please enter tour type")
    private TourType tourType;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tours")
    private List<User> users = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", foreignKey = @ForeignKey(name = "tour_hotel_id_fkey"))
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id", foreignKey = @ForeignKey(name = "tour_country_id_fkey"))
    private Country country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tour", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public boolean addReview(Review review) {
        review.setTour(this);
        return reviews.add(review);
    }

    public boolean removeReview(Review review) {
        review.setTour(null);
        return reviews.remove(review);
    }

}