package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Meal {
    private static AtomicInteger mealsObjectsCounter = new AtomicInteger(1);

    private Long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private int version;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        id = mealsObjectsCounter.longValue();
        mealsObjectsCounter.incrementAndGet();
    }

    public Meal(long id, LocalDateTime dateTime, String description, int calories, int version) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
        this.version = version;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", calories=" + calories +
                ", version=" + version +
                '}';
    }
}
