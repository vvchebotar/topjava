package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
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
public class JdbcMealRepositoryImplTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealRepository repository;

    @Test
    public void saveNewMeal() {
        Meal newMeal = new Meal(NEW_MEAL);
        newMeal = repository.save(newMeal, USER_ID);
        assertMatch(newMeal, NEW_MEAL_WITH_ID);
        assertMatch(repository.getAll(USER_ID), NEW_MEAL_WITH_ID, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void saveExistingMeal() {
        Meal updated = new Meal(EXPECTED_UPDATED_MEAL_1);
        assertMatch(repository.save(updated, USER_ID), EXPECTED_UPDATED_MEAL_1);
        assertMatch(repository.getAll(USER_ID), EXPECTED_UPDATED_MEAL_1, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2);
    }

    @Test
    public void saveExistingMealWrongUser() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("updatedDescription");
        updated.setCalories(99999);
        updated.setDateTime(LocalDateTime.parse("9999-01-01T23:59:59"));
        assertThat(repository.save(updated, ADMIN_ID)).isNull();
        assertMatch(repository.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void delete() {
        assertThat(repository.delete(MEAL_1_ID, USER_ID)).isEqualTo(true);
    }

    @Test
    public void deleteWrongMeal() {
        assertThat(repository.delete(MEAL_1_ID - 1, USER_ID)).isEqualTo(false);
    }

    @Test
    public void deleteWrongUser() {
        assertThat(repository.delete(MEAL_1_ID, ADMIN_ID)).isEqualTo(false);
    }

    @Test
    public void get() {
        assertMatch(repository.get(MEAL_1_ID, USER_ID), MEAL_1);
    }

    @Test
    public void getWrongId() {
        assertThat(repository.get(MEAL_1_ID - 1, USER_ID)).isNull();
    }

    @Test
    public void getWrongUser() {
        assertThat(repository.get(MEAL_1_ID, ADMIN_ID)).isNull();
    }

    @Test
    public void getAll() {
        assertMatch(repository.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetween() {
        assertMatch(repository.getBetween(START_TIME, END_TIME, USER_ID), MEAL_3, MEAL_2, MEAL_1);
        assertMatch(repository.getBetween(START_TIME, END_TIME.plusDays(1), USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
        assertMatch(repository.getBetween(START_TIME.minusDays(1), END_TIME.minusDays(1), USER_ID));
    }
}