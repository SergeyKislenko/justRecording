package com.recording.core.model.enums;

public enum OrderStatus {
    REGISTRATION("Зарегистрирована", "CONFIRMED"),
    CANCEL("Отменено", "CANCEL"),
    DONE("Завершнено", "DONE");

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
