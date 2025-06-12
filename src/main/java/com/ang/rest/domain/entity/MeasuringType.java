package com.ang.rest.domain.entity;

public enum MeasuringType {

    UNIT(1),
    WEIGHT(1000),
    VOLUME(1000),
    UNKNOWN(1);


    private final int defaultConversionFactor;

    MeasuringType(int defaultConversionFactor) {
        this.defaultConversionFactor = defaultConversionFactor;
    }


    public int getDefaultConversionFactor() {
        return defaultConversionFactor;
    }
    public static MeasuringType of(String name) {
        if (name == null) {
            return UNKNOWN;
        }
        try {
            return MeasuringType.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
