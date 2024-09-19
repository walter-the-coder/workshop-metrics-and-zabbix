package com.example.type;

public enum TaxCategory {
    NORMAL,
    DELTA;

    @Override
    public String toString() {
        return name();
    }
}
