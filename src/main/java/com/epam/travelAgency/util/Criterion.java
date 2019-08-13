package com.epam.travelAgency.util;

import com.epam.travelAgency.entity.TourType;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.postgresql.util.PGmoney;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Criterion {

    private static final String AND = " AND ";
    private long tourId;
    private String photoPath;
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private PGmoney cost;
    private int stars;
    private long hotelId;
    private long countryId;
    private TourType tourType;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (tourId != 0) {
            stringBuilder.append("tour_id = ").append(tourId).append(AND);
        }
        if (photoPath != null) {
            stringBuilder.append("photo = \'").append(photoPath).append("\'").append(AND);
        }
        if (startDate != null) {
            stringBuilder.append("start_date = \'").append(startDate).append("\'").append(AND);
        }
        if (endDate != null) {
            stringBuilder.append("end_date = \'").append(endDate).append("\'").append(AND);
        }
        if (description != null) {
            stringBuilder.append("description = \'").append(description).append("\'").append(AND);
        }
        if (cost != null) {
            stringBuilder.append("cost = ").append(cost).append(AND);
        }
        if (stars != 0) {
            stringBuilder.append("stars = ").append(stars).append(AND);
        }
        if (hotelId != 0) {
            stringBuilder.append("hotel_id = ").append(hotelId).append(AND);
        }
        if (countryId != 0) {
            stringBuilder.append("country_id = ").append(countryId).append(AND);
        }
        if (tourType != null) {
            stringBuilder.append("tour_type = \'").append(tourType).append("\'");
        } else if (stringBuilder.length() != 0) {
            int length = stringBuilder.length();
            stringBuilder.replace(length - AND.length(), length, StringUtils.EMPTY);
        }
        return stringBuilder.toString();
    }

}
