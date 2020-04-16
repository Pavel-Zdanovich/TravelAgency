package com.zdanovich.core.entity;

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
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.zdanovich.core.entity.AbstractEntity.ID;
import static com.zdanovich.core.entity.Review.REVIEWS;
import static com.zdanovich.core.entity.Review.REVIEW_ID;

@Entity
@Table(name = REVIEWS)
@AttributeOverride(name = ID, column = @Column(name = REVIEW_ID, precision = 10))
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Validated
public class Review extends AbstractEntity {

    public static final String REVIEWS = "REVIEWS";
    public static final String REVIEW_ID = "REVIEW_ID";
    public static final String REVIEW_DATE = "REVIEW_DATE";
    public static final String REVIEW_TEXT = "REVIEW_TEXT";

    @Column(name = REVIEW_DATE, nullable = false)
    @FutureOrPresent(message = "{review.date.futureOrPresent}")
    @NotNull(message = "{review.date.notNull}")
    @Getter
    @Setter
    private Timestamp reviewDate;

    @Column(name = REVIEW_TEXT, nullable = false, length = 1000)
    @NotEmpty(message = "{review.text.notEmpty}")
    @Size(max = 1000, message = "{review.text.size}")
    @Getter
    @Setter
    private String reviewText;

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = User.USER_ID, nullable = false, foreignKey = @ForeignKey(name = "REVIEW_USER_ID_FK"))
    @Getter
    private User user;

    @ManyToOne(targetEntity = Tour.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = Tour.TOUR_ID, nullable = false, foreignKey = @ForeignKey(name = "REVIEW_TOUR_ID_FK"))
    @Getter
    private Tour tour;

    public boolean setUser(@NotNull(message = "{user.notNull}") User user) {
        this.user = user;
        return this.user.getReviews().add(this);
    }

    public boolean removeUser() {
        if (this.user != null) {
            if (this.user.getReviews().remove(this)) {
                this.user = null;
                return true;
            }
        }
        return false;
    }

    public boolean setTour(@NotNull(message = "{tour.notNull}") Tour tour) {
        this.tour = tour;
        return this.tour.getReviews().add(this);
    }

    public boolean removeTour() {
        if (this.tour != null) {
            if (this.tour.getReviews().remove(this)) {
                this.user = null;
                return true;
            }
        }
        return false;
    }
}
