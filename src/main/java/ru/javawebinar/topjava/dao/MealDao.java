package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao extends GenericDao<Meal, Long> {

    List<Meal> getList();

    void deleteById(String id);

    List<Meal> getByDateTime(LocalDateTime date);

    int getAllMealsCount();
}
