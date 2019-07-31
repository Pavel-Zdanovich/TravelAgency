package com.epam.travelAgency.entity;

public enum TourType {

    TREATMENT("treatment"),
    TOURISM("tourism"),
    LEISURE("leisure"),
    BUSINESS("business"),
    PILGRIMAGE("pilgrimage"),
    TRAINING("training"),
    SPORT_COMPETITION("sport competition"),
    RURAL_TOURISM("rural tourism"),
    SCIENTIFIC_EXPEDITION("scientific expedition"),
    ECOTOURISM("ecotourism");

    private final String name;

    TourType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static TourType getTourType(String stringTourType) {
        for (TourType tourType : values()) {
            if (tourType.name.equals(stringTourType)) {
                return tourType;
            }
        }
        throw new IllegalArgumentException("Illegal tour name : " + stringTourType);
    }

}
