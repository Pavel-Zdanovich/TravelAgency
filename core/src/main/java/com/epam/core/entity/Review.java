package com.epam.core.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "REVIEWS")
@AttributeOverride(name = "id", column = @Column(name = "REVIEW_ID", precision = 10))
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Review extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQUENCE")
    @SequenceGenerator(name = "REVIEW_SEQUENCE", sequenceName = "REVIEW_SEQUENCE", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long reviewId) {
        this.id = reviewId;
    }

    @Column(name = "REVIEW_DATE", nullable = false)
    @FutureOrPresent(message = "Please enter review date no later than today")
    @NotNull(message = "Please enter review date")
    @Getter
    @Setter
    private Timestamp reviewDate;

    @Column(name = "REVIEW_TEXT", nullable = false, length = 1000)
    @NotNull(message = "Please enter review text")
    @Getter
    @Setter
    private String reviewText;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "REVIEW_USER_ID_FK"))
    @Getter
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TOUR_ID", nullable = false, foreignKey = @ForeignKey(name = "REVIEW_TOUR_ID_FK"))
    @Getter
    private Tour tour;

    public boolean addUser(User user) {
        this.user = user;
        return this.user.getReviews().add(this);
    }

    public boolean removeUser() {
        boolean result = false;
        if (this.user != null) {
            result = this.user.getReviews().remove(this);
            this.user = null;
        }
        return result;
    }

    public boolean addTour(Tour tour) {
        this.tour = tour;
        return this.tour.getReviews().add(this);
    }

    public boolean removeTour() {
        boolean result = false;
        if (this.tour != null) {
            result = this.tour.getReviews().remove(this);
            this.user = null;
        }
        return result;
    }

}
