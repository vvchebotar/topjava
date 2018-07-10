package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    private MealRepository mealRepository;
    private UserRepository userRepository;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, User user) {
        Meal mealReturned = mealRepository.save(meal);
        user.addMealId(mealReturned.getId());
        userRepository.save(user);
        return mealReturned;
    }

    @Override
    public boolean delete(int id, User user) {
        boolean b = mealRepository.delete(id, user.getMealsIdSet()) ? user.deleteMealId(id) : false;
        userRepository.save(user);
        return b;
    }

    @Override
    public Meal get(int id, User user) {
        return mealRepository.get(id, user.getMealsIdSet());
    }

    @Override
    public Collection<Meal> getAll(User user) {
        return mealRepository.getAll(user.getMealsIdSet());
    }

    @Override
    public List<MealWithExceed> getAllFiltered(String starDate, String endDate, String starTime, String endTime, User user) {
        LocalDate startLocalDate = starDate == null || starDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(starDate);
        LocalDate endLocalDate = endDate == null || endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
        LocalTime startLocalTime = starTime == null || starTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(starTime);
        LocalTime endLocalTime = endTime == null || endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
        List<Meal> meals = mealRepository.getAll(user.getMealsIdSet());
        return MealsUtil.getFilteredWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY, meal ->
                isaBoolean(startLocalDate, endLocalDate, startLocalTime, endLocalTime, meal));
    }

    private boolean isaBoolean(LocalDate startLocalDate, LocalDate endLocalDate, LocalTime startLocalTime, LocalTime endLocalTime, Meal meal) {
        return DateTimeUtil.isBetween(meal.getDate(), startLocalDate, endLocalDate) &&
                DateTimeUtil.isBetween(meal.getTime(), startLocalTime, endLocalTime);
    }
}