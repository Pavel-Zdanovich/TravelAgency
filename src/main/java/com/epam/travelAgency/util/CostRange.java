package com.epam.travelAgency.util;

import lombok.*;
import org.postgresql.util.PGmoney;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CostRange {

    @NonNull
    private PGmoney minCost;
    @NonNull
    private PGmoney maxCost;

}
