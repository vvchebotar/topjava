package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsListInitUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.util.List;

public class MealDaoInMemoryImpl implements MealDao {
    final public List<Meal> mealList = MealsListInitUtil.prepareMealList();

    @Override
    public List<Meal> getList() {
        return mealList;
    }

    @Override
    public void deleteById(String id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Meal> getByDateTime(LocalDateTime date) {
        throw new NotImplementedException();
    }

    @Override
    public int getAllBooksCount() {
        return mealList.size();
    }

    @Override
    public Long create(Meal newInstance) {
        throw new NotImplementedException();
    }

    @Override
    public Meal retrieve(Class<Meal> clazz, Long id) {
        for (Meal meal : mealList) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public synchronized void update(Meal meal) {
        Meal retrievedMeal = retrieve(Meal.class, meal.getId());
        if (meal.getVersion() == retrievedMeal.getVersion()) {
            meal.setVersion(meal.getVersion() + 1);
            mealList.remove(retrievedMeal);
            mealList.add(meal);
        }
    }

    @Override
    public synchronized void delete(Meal meal) {
        Meal retrievedMeal = retrieve(Meal.class, meal.getId());
        mealList.remove(retrievedMeal);
    }
}
