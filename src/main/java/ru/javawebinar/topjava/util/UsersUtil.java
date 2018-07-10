package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class UsersUtil {
    public static final List<User> USERS;

    static {
        User admin = new User("admin", "admin@gmail.com", "1234", Role.ROLE_ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER);
        IntStream.range(1, 4).forEach(admin::addMealId);
        User user = new User("user", "user@yandex.ru", "1234", Role.ROLE_USER, Role.ROLE_USER);
        IntStream.range(4, 7).forEach(user::addMealId);
        USERS = Arrays.asList(admin, user);
    }
}