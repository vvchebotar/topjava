package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;


public interface MealService extends GenericService<Meal, Long> {

    List<Meal> getList();

    int getAllMealsCount();

    List<MealWithExceed> getMealWithExceeds(int caloriesPerDay);

    void deleteById(long id);
}
