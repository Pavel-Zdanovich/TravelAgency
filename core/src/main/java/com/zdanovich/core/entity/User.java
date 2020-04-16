package com.zdanovich.core.entity;

import com.zdanovich.core.entity.enums.UserRole;
import com.zdanovich.core.entity.metamodel.Review_;
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

import static com.zdanovich.core.entity.AbstractEntity.ID;
import static com.zdanovich.core.entity.Tour.TOUR_ID;
import static com.zdanovich.core.entity.User.LOGIN;
import static com.zdanovich.core.entity.User.USERS;
import static com.zdanovich.core.entity.User.USER_ID;

@Entity
@Table(name = USERS, uniqueConstraints = @UniqueConstraint(name = "USER_LOGIN_UNIQUE", columnNames = LOGIN))
@AttributeOverride(name = ID, column = @Column(name = USER_ID))
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

    @Column(name = PASSWORD, nullable = false, length = 30)
    @NotNull(message = "{user.password.notNull}")
    @Size(min = 5, max = 30, message = "{user.password.size}")
    @Pattern(regexp = ONE_WORD_REGEX, message = "{user.password.pattern}")
    @Getter
    @Setter
    private String password;

    @Column(name = ROLE, nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    @Getter
    @Setter
    private UserRole role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_TOURS",
            joinColumns = @JoinColumn(name = USER_ID, referencedColumnName = USER_ID,
                    foreignKey = @ForeignKey(name = "USER_TOUR_USER_ID_FK")),
            foreignKey = @ForeignKey(name = "USER_TOUR_USER_ID_FK"),
            inverseJoinColumns = @JoinColumn(name = TOUR_ID, referencedColumnName = TOUR_ID,
                    foreignKey = @ForeignKey(name = "USER_TOUR_TOUR_ID_FK")),
            inverseForeignKey = @ForeignKey(name = "USER_TOUR_TOUR_ID_FK"))
    @Getter
    private Set<Tour> tours = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = Review_.USER)
    @Getter
    private List<Review> reviews = new ArrayList<>();

    public boolean addTour(@NotNull(message = "{tour.notNull}") Tour tour) {
        return this.tours.add(tour) ? tour.getUsers().add(this) : false;
    }

    public boolean removeTour(@NotNull(message = "{tour.notNull}") Tour tour) {
        return this.tours.remove(tour) ? tour.getUsers().remove(this) : false;
    }
}
