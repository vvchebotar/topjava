package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Set;

public interface MealRepository {
    Meal save(Meal meal);

    // false if not found
    boolean delete(int id, Set<Integer> mealsIdSet);

    // null if not found
    Meal get(int id, Set<Integer> mealsIdSet);

    List<Meal> getAll(Set<Integer> mealsIdSet);
}
