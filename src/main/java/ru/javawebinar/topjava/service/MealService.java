package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;


public interface MealService extends GenericService<Meal, Long> {

    List<Meal> getList();

    int getAllMealsCount();
}
