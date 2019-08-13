package com.epam.travelAgency.util;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CostRange {

    @NonNull
    private BigDecimal minCost;
    @NonNull
    private BigDecimal maxCost;

}
