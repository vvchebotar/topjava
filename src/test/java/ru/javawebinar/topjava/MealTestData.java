package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_1_ID = START_SEQ + 2;
    public static final int MEAL_2_ID = START_SEQ + 3;
    public static final int MEAL_3_ID = START_SEQ + 4;
    public static final int MEAL_4_ID = START_SEQ + 5;
    public static final int MEAL_5_ID = START_SEQ + 6;
    public static final int MEAL_6_ID = START_SEQ + 7;
    public static final int MEAL_7_ID = START_SEQ + 8;
    public static final int MEAL_8_ID = START_SEQ + 9;

    public static final LocalDate START_DATE = LocalDate.of(2018, 07, 30);
    public static final LocalDate END_DATE = LocalDate.of(2018, 07, 30);

    public static final LocalDateTime START_TIME = LocalDateTime.of(2018, 07, 30, 0, 0);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2018, 07, 30, 23, 59);

    public static final Meal MEAL_1 = new Meal(MEAL_1_ID, LocalDateTime.parse("2018-07-30T10:00:00"), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_2_ID, LocalDateTime.parse("2018-07-30T13:00:00"), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_3_ID, LocalDateTime.parse("2018-07-30T20:00:00"), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_4_ID, LocalDateTime.parse("2018-07-31T10:00:00"), "Завтрак", 1000);
    public static final Meal MEAL_5 = new Meal(MEAL_5_ID, LocalDateTime.parse("2018-07-31T13:00:00"), "Обед", 500);
    public static final Meal MEAL_6 = new Meal(MEAL_6_ID, LocalDateTime.parse("2018-07-31T20:00:00"), "Ужин", 510);
    public static final Meal MEAL_7 = new Meal(MEAL_7_ID, LocalDateTime.parse("2018-07-30T10:01:00"), "Завтрак_Админ", 2000);
    public static final Meal MEAL_8 = new Meal(MEAL_8_ID, LocalDateTime.parse("2018-07-30T13:01:00"), "Обед_Админ", 1000);

    public static final Meal NEW_MEAL = new Meal(LocalDateTime.parse("2018-08-01T10:00:00"), "Завтрак", 400);
    public static final Meal NEW_MEAL_WITH_ID = new Meal(100010, LocalDateTime.parse("2018-08-01T10:00:00"), "Завтрак", 400);
    public static final Meal EXPECTED_UPDATED_MEAL_1 = new Meal(MEAL_1_ID, LocalDateTime.parse("9999-01-01T23:59:59"), "updatedDescription", 99999);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
