package com.epam.travelAgency.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "user_login_unique", columnNames = "login"))
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User extends TravelAgencyEntity {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "Please enter user id")
    private long userId;

    @Column(name = "login", length = 30)
    @Size(min = 5, max = 30, message = "Please enter user login [5, 30] characters")
    @NotNull(message = "Please enter user login")
    private String login;

    @Column(name = "password", length = 30)
    @Size(min = 5, max = 30, message = "Please enter user password [5, 30] characters")
    @NotNull(message = "Please enter user password")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_tours", uniqueConstraints = @UniqueConstraint(name = "user_tour_primary_key", columnNames = {"user_id", "tour_id"}),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "user_tour_user_id_fkey")),
            inverseJoinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "tour_id", foreignKey = @ForeignKey(name = "user_tour_tour_id_fkey")))
    private List<Tour> tours = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public boolean addTour(Tour tour) {
        tour.getUsers().add(this);
        return this.tours.add(tour);
    }

    public boolean removeTour(Tour tour) {
        tour.getUsers().remove(this);
        return this.tours.remove(tour);
    }

    public boolean addReview(Review review) {
        review.setUser(this);
        return reviews.add(review);
    }

    public boolean removeReview(Review review) {
        review.setUser(null);
        return reviews.remove(review);
    }

}
