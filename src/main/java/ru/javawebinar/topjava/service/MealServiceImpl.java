package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoHardCodeImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UserMealWithExceedUtil;

import java.util.List;

public class MealServiceImpl implements MealService {
    private MealDao mealDao = MealDaoHardCodeImpl.getInstance();

    @Override
    public Long create(Meal newInstance) {
        return mealDao.create(newInstance);
    }

    @Override
    public Meal retrieve(Class<Meal> clazz, Long id) {
        return mealDao.retrieve(clazz, id);
    }

    @Override
    public void update(Meal transientObject) {
        mealDao.update(transientObject);
    }

    @Override
    public void delete(Meal persistentObject) {
        mealDao.delete(persistentObject);
    }

    @Override
    public List<Meal> getList() {
        return mealDao.getList();
    }

    @Override
    public int getAllMealsCount() {
        return mealDao.getAllMealsCount();
    }

    @Override
    public List<MealWithExceed> getMealWithExceeds(int caloriesPerDay) {
        List<MealWithExceed> mealWithExceedList = MealsUtil.getWithExceeded(mealDao.getList(), caloriesPerDay);
        UserMealWithExceedUtil.sortListByDateTime(mealWithExceedList);
        return mealWithExceedList;
    }

    @Override
    public void deleteById(long id) {
        mealDao.deleteById(id);
    }
}
