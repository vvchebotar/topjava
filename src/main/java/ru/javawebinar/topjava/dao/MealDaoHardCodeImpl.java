package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsListInitUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.util.List;

public class MealDaoHardCodeImpl implements MealDao {
    private static MealDaoHardCodeImpl instance;

    final public List<Meal> mealList = MealsListInitUtil.prepareMealList();

    private MealDaoHardCodeImpl() {
    }

    //to provide singleton
    public static MealDaoHardCodeImpl getInstance() {
        if (instance == null) {
            instance = new MealDaoHardCodeImpl();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public Long create(Meal newInstance) {
        throw new NotImplementedException();
    }

    @Override
    public Meal retrieve(Class<Meal> clazz, Long id) {
        Meal meal1 = mealList.stream().filter(meal -> id.compareTo(meal.getId()) == 0).findAny().get();
        return meal1;
    }

    @Override
    public synchronized void update(Meal meal) {
        Meal retrievedMeal = retrieve(Meal.class, meal.getId());
        //versioning mechanism
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

    @Override
    public List<Meal> getList() {
        return mealList;
    }

    @Override
    public List<Meal> getByDateTime(LocalDateTime date) {
        throw new NotImplementedException();
    }

    @Override
    public int getAllMealsCount() {
        return mealList.size();
    }

    @Override
    public synchronized void deleteById(Long id) {
        Meal meal = mealList.stream().filter(m -> id.compareTo(m.getId()) == 0).findAny().get();
        mealList.remove(meal);
    }
}
