package ru.javawebinar.topjava;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;

// default in db-config - work
// через -D - задаются системные properties
//-Dspring.profiles.active=datajpa,postgres - work
//@Profile({"postgres", "datajpa"}) - don't work
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        // можно задать свойства, которые контекст проверит и использует если они есть.
        //System.setProperty("spring.profiles.active", REPOSITORY_IMPLEMENTATION + ", " + Profiles.getActiveDbProfile()); - work


        // Когда задаешь конфиги сразу при создании класса, он их парсит. Выставление профилей после этого не дает результат.
        // Нужно сразу выставлять профиль, а потом парсить.
        /*try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(REPOSITORY_IMPLEMENTATION, Profiles.getActiveDbProfile());// - work
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();// to parse*/
        //to customize whether refresh or not.
        /*try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false)) {
            appCtx.getEnvironment().setActiveProfiles(REPOSITORY_IMPLEMENTATION, Profiles.getActiveDbProfile());// - work
            appCtx.refresh();*/
        // to load xml files after profiles setting.
        try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(REPOSITORY_IMPLEMENTATION, Profiles.getActiveDbProfile());// - work
            appCtx.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
