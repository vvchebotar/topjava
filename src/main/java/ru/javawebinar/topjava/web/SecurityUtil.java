package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Component
public class SecurityUtil {
    private static final Logger log = getLogger(UserServlet.class);
    private static UserService userService;

    public static User user;

    @Autowired
    public SecurityUtil(UserService userService) {
        SecurityUtil.userService = userService;
    }

    public static User authUser() {
        return user;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void setAuthUserId(int userId) {
        getWithSavingPrevious(userId);
    }

    private static void getWithSavingPrevious(int i) {
        if (user != null) {
            log.debug(userService.toString());
            userService.create(user);
        }
        if (userService == null) log.debug("BINGO");
        user = userService.get(i);
    }
}