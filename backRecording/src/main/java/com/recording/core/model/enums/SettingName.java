package com.recording.core.model.enums;

public enum SettingName {
    NAME_ORG("Наименование организации"),
    STEP("Продолжительность одного приема"),
    NEXT_DAY("Сколько дней вперед заполнять расписание"),
    START_WORK("Время начала рабочего дня"),
    END_WORK("Время окончания рабочего дня");

    private final String description;

    private SettingName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
