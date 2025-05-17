package com.nion.tasktracker.entity.dictionary;

import lombok.Getter;

@Getter
public enum TaskType {
    WORK("Задачи, связанные с работой или учебой"),
    PERSONAL("Личные задачи, например, покупки, встречи с друзьями и т.д."),
    URGENT("Срочные задачи, требующие немедленного выполнения."),
    ROUTINE("Повседневные задачи, такие как уборка, приготовление пищи и т.п."),
    PROJECT("Задачи, связанные с долгосрочными проектами."),
    REMINDER("Напоминания о событиях, днях рождения, важных встречах."),
    LEISURE("Задачи для отдыха и развлечений, например, просмотр фильма, чтение книги."),
    MISC("Разные задачи, не попадающие в другие категории (общие, случайные задачи).");


    private final String description;

    TaskType(String description) {
        this.description = description;
    }
}
