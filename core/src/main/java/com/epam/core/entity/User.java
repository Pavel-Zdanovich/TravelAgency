package com.epam.core.entity;

import com.epam.core.entity.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(name = "USER_LOGIN_UNIQUE", columnNames = "LOGIN"))
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tours", "reviews"}, callSuper = false)
@ToString(exclude = {"tours", "reviews"})
public class User extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "USER_SEQUENCE", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    @Column(name = "LOGIN", nullable = false, length = 30)
    @NaturalId
    @Size(min = 5, max = 30, message = "Please enter user login [5, 30] characters")
    @NotNull(message = "Please enter user login")
    @Getter
    @Setter
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 30)
    @Size(min = 5, max = 30, message = "Please enter user password [5, 30] characters")
    @NotNull(message = "Please enter user password")
    @Getter
    @Setter
    private String password;

    @Column(name = "ROLE", nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    @Size(min = 3, max = 10, message = "Please enter feature name [3, 10] characters")
    @NotNull(message = "Please enter tour type")
    @Getter
    @Setter
    private UserRole role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_TOURS",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID",
                    foreignKey = @ForeignKey(name = "USER_TOUR_USER_ID_FK")),
            foreignKey = @ForeignKey(name = "USER_TOUR_USER_ID_FK"),
            inverseJoinColumns = @JoinColumn(name = "TOUR_ID", referencedColumnName = "TOUR_ID",
                    foreignKey = @ForeignKey(name = "USER_TOUR_TOUR_ID_FK")),
            inverseForeignKey = @ForeignKey(name = "USER_TOUR_TOUR_ID_FK"))
    @Getter
    private Set<Tour> tours = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Getter
    private List<Review> reviews = new ArrayList<>();

    public boolean addTour(Tour tour) {
        tour.getUsers().add(this);
        return this.tours.add(tour);
    }

    public boolean removeTour(Tour tour) {
        tour.getUsers().remove(this);
        return this.tours.remove(tour);
    }

}
