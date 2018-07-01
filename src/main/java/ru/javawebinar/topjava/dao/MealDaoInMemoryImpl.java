package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsListInitUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.util.List;

public class MealDaoInMemoryImpl implements MealDao {
    private List<Meal> mealList = MealsListInitUtil.prepareMealList();

    @Override
    public List<Meal> getList() {
        return mealList;
    }

    @Override
    public void deleteById(String id) {
        //TODO implement
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
        //TODO implement
        mealList.add(newInstance);
        return null; //newInstance.getId;

    }

    @Override
    public Meal retrieve(Class<Meal> clazz, Long id) {
        //TODO implement
        return null;
    }

    @Override
    public void update(Meal transientObject) {
        //TODO implement

    }

    @Override
    public void delete(Meal persistentObject) {
        //TODO implement
    }
}
