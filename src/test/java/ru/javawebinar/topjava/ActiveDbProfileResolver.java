package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.JDBC_IMPLEMENTATION;
import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.getActiveDbProfile(), REPOSITORY_IMPLEMENTATION, JDBC_IMPLEMENTATION};
    }
}