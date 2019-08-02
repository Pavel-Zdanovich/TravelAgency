package com.epam.travelAgency.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;

@Component
@Entity(name = "Review")
@Table(name = "reviews", schema = "public")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Review extends TravelAgencyEntity {

    @Column(name = "review_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "Please enter review id")
    private long reviewId;

    @Column(name = "date")
    @PastOrPresent
    @NotNull(message = "Please enter review date")
    private Timestamp date;

    @Column(name = "text")
    @NotNull(message = "Please enter review text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "review_user_id_fkey"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", referencedColumnName = "tour_id", foreignKey = @ForeignKey(name = "review_tour_id_fkey"))
    private Tour tour;

}
