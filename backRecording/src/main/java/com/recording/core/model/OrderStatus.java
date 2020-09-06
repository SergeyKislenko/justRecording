package com.recording.core.model;

public enum OrderStatus {
    REGISTRATION("Зарегистрирована", "CONFIRMED"),
    CANCEL("Отменено", "CANCEL");

    private final String description;
    private final String code;

    private OrderStatus(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public String toString() {
        return this.code;
    }
}
