package com.example.type;

public enum ReceptionStatus {
    RECEIVED,
    PROCESSED,
    FAILED;

    @Override
    public String toString() {
        return name();
    }
}