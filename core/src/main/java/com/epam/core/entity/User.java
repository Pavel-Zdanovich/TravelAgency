package com.epam.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "User")
@Table(name = "users", schema = "public", uniqueConstraints = @UniqueConstraint(name = "user_login_unique", columnNames = "login"))
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User implements Serializable {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator_sequence", initialValue = 1_000_000, allocationSize = 9_999_999)
    @NotNull(message = "Please enter user id")
    private long userId;

    @Column(name = "login", length = 30)
    @NaturalId
    @Size(min = 5, max = 30, message = "Please enter user login [5, 30] characters")
    @NotNull(message = "Please enter user login")
    private String login;

    @Column(name = "password", length = 30)
    @Size(min = 5, max = 30, message = "Please enter user password [5, 30] characters")
    @NotNull(message = "Please enter user password")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)//{CascadeType.PERSIST, CascadeType.MERGE}
    @JoinTable(name = "users_tours", joinColumns = @JoinColumn(name = "user_id",
            referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "user_tour_user_id_fkey")),
            inverseJoinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "tour_id", foreignKey = @ForeignKey(name = "user_tour_tour_id_fkey")))
    private List<Tour> tours = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
