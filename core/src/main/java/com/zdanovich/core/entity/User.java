package com.zdanovich.core.entity;

import com.zdanovich.core.entity.enums.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.validation.annotation.Validated;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = User.USERS, uniqueConstraints = @UniqueConstraint(name = "USER_LOGIN_UNIQUE", columnNames = User.LOGIN))
@AttributeOverride(name = AbstractEntity.ID, column = @Column(name = User.USER_ID))
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tours", "reviews"}, callSuper = false)
@ToString(exclude = {"tours", "reviews"})
@Validated
public class User extends AbstractEntity {

    public static final String USERS = "USERS";
    public static final String USER_ID = "USER_ID";
    public static final String LOGIN = "LOGIN";
    public static final String PASSWORD = "PASSWORD";
    public static final String ROLE = "ROLE";
    public static final String ONE_WORD_REGEX = "\\w+";

    @Column(name = LOGIN, nullable = false, length = 30)
    @NaturalId
    @NotNull(message = "{user.login.notNull}")
    @Size(min = 5, max = 30, message = "{user.login.size}")
    @Pattern(regexp = ONE_WORD_REGEX, message = "{user.login.pattern}")
    @Getter
    @Setter
    private String login;

    @Column(name = PASSWORD, nullable = false, length = 100)
    @NotNull(message = "{user.password.notNull}")
    @Size(min = 5, max = 100, message = "{user.password.size}")
    @Pattern(regexp = ONE_WORD_REGEX, message = "{user.password.pattern}")
    @Getter
    @Setter
    private String password;

    @Column(name = ROLE, nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    @Getter
    @Setter
    private UserRole role;

    @ManyToMany(targetEntity = Tour.class, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_TOURS",
            joinColumns = @JoinColumn(name = USER_ID, referencedColumnName = USER_ID,
                    foreignKey = @ForeignKey(name = "USER_TOUR_USER_ID_FK")),
            foreignKey = @ForeignKey(name = "USER_TOUR_USER_ID_FK"),
            inverseJoinColumns = @JoinColumn(name = Tour.TOUR_ID, referencedColumnName = Tour.TOUR_ID,
                    foreignKey = @ForeignKey(name = "USER_TOUR_TOUR_ID_FK")),
            inverseForeignKey = @ForeignKey(name = "USER_TOUR_TOUR_ID_FK"))
    @Getter
    private Set<Tour> tours = new HashSet<>();

    @OneToMany(targetEntity = Review.class, cascade = CascadeType.ALL)
    @JoinColumn(name = USER_ID)
    @Getter
    private List<Review> reviews = new ArrayList<>();

    public boolean addTour(@NotNull(message = "{tour.notNull}") Tour tour) {
        return this.tours.add(tour) ? tour.getUsers().add(this) : false;
    }

    public boolean removeTour(@NotNull(message = "{tour.notNull}") Tour tour) {
        return this.tours.remove(tour) ? tour.getUsers().remove(this) : false;
    }
}
