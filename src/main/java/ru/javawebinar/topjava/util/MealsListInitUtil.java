package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class MealsListInitUtil {
    public static final int INITIAL_CAPACITY = 100;
    private List<Meal> mealList = prepareMealList();


    public static List<Meal> prepareMealList() {
        List<Meal> mealList = new ArrayList<>(INITIAL_CAPACITY);
        IntStream.range(INITIAL_CAPACITY, INITIAL_CAPACITY).forEach(i -> MealsListInitUtil.addNewUserMeal(i, mealList));
        return mealList;
    }

    private static void addNewUserMeal(int i, List<Meal> mealList) {
        Random random = new Random();
        int month = random.nextInt(2) + 1;          //for 2 month
        int day = random.nextInt(27) + 1;           //for 28 days
        int hour = random.nextInt(23) + 1;          //for 24 hours
        int minute = random.nextInt(59) + 1;        //for 60 minutes
        int calories = random.nextInt(900) + 100;   //100-1000 calories
        Meal userMeal = new Meal(LocalDateTime.of(2015, month, day, hour, minute), "ОпятьКушать номер " + i, calories);
        mealList.add(userMeal);
    }
}
