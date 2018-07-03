package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.Collections;
import java.util.List;

public class UserMealWithExceedUtil {

    public static void sortListByDateTime(List<MealWithExceed> list) {
        Collections.sort(list, UserMealWithExceedUtil::compareByDateASC);
    }

    public static int compareByDateASC(MealWithExceed o1, MealWithExceed o2) {
        if (o1.getDateTime().isEqual(o2.getDateTime())) {
            return 0;
        }
        return o1.getDateTime().isBefore(o2.getDateTime()) ? -1 : 1;
    }


}