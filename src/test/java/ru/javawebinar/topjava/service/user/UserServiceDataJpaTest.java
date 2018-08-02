package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;

import java.util.stream.IntStream;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.STARVING_USER;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJpaTest extends UserServiceBaseTest {

    @Test
    public void getByIdWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        assertMatch(user, USER_WITH_MEALS);
        IntStream.range(0, 6).forEach(i -> MealTestData.assertMatch(user.getMeals().get(i), USER_WITH_MEALS.getMeals().get(i)));
        User user2 = service.getWithMeals(STARVING_USER_ID);
        assertMatch(user2, STARVING_USER);
    }
}
