package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.Collection;
import java.util.List;

public interface MealService {
    Meal save(Meal meal, User user);

    boolean delete(int id, User user);

    Meal get(int id, User user);

    Collection<Meal> getAll(User user);

    List<MealWithExceed> getAllFiltered(String starDate, String endDate, String starTime, String endTime, User user);
}