package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-app-jdbc.xml"
})
@RunWith(SpringRunner.class)
//this script will be performed before every test
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(MEAL_1_ID, USER_ID), MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongId() {
        service.get(MEAL_1_ID - 1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() {
        service.get(MEAL_1_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_6_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongId() {
        service.delete(MEAL_1_ID - 1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() {
        service.delete(MEAL_1_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(START_DATE, END_DATE, USER_ID), MEAL_3, MEAL_2, MEAL_1);
        assertMatch(service.getBetweenDates(START_DATE, END_DATE.plusDays(1), USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
        assertMatch(service.getBetweenDates(START_DATE.minusDays(1), END_DATE.minusDays(1), USER_ID));
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(START_TIME, END_TIME, USER_ID), MEAL_3, MEAL_2, MEAL_1);
        assertMatch(service.getBetweenDateTimes(START_TIME, END_TIME.plusDays(1), USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
        assertMatch(service.getBetweenDateTimes(START_TIME.minusDays(1), END_TIME.minusDays(1), USER_ID));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(EXPECTED_UPDATED_MEAL_1);
        //Does returned result correspond expected entity?
        assertMatch(service.update(updated, USER_ID), EXPECTED_UPDATED_MEAL_1);
        //Does users meals correspond expected list?
        assertMatch(service.getAll(USER_ID), EXPECTED_UPDATED_MEAL_1, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongMealId() {
        Meal updated = new Meal(MEAL_1);
        updated.setId(1);
        service.update(updated, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUserId() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("updatedDescription");
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(NEW_MEAL);
        assertMatch(service.create(newMeal, USER_ID), NEW_MEAL_WITH_ID);
        assertMatch(service.getAll(USER_ID), NEW_MEAL_WITH_ID, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }
}