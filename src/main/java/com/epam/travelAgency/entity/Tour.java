package com.epam.travelAgency.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.postgresql.util.PGmoney;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "Tour")
@Table(name = "tours")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class, defaultForType = TourType.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tour extends TravelAgencyEntity {

    @Column(name = "tour_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "Please enter tour id")
    private long tourId;

    @Column(name = "photo")
    private String photoPath;

    @Column(name = "start_date")
    @NotNull(message = "Please enter tour start date")
    private Timestamp startDate;

    @Column(name = "end_date")
    @NotNull(message = "Please enter tour end date")
    private Timestamp endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    @Type(type = "org.hibernate.type.BigDecimalType")
    @NotNull(message = "Please enter tour cost")
    private PGmoney cost;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tour_type", columnDefinition = "types_of_tours")
    @Type(type = "pgsql_enum")
    @NotNull(message = "Please enter tour type")
    private TourType tourType;

    @ManyToMany(mappedBy = "tours")
    private List<User> users = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", foreignKey = @ForeignKey(name = "tour_hotel_id_fkey"))
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "country_id", foreignKey = @ForeignKey(name = "tour_country_id_fkey"))
    private Country country;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
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
