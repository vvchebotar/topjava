package ru.javawebinar.topjava.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

// для Criteria
//@StaticMetamodel(User.class)
public abstract class User_ extends AbstractNamedEntity {
    public static volatile SingularAttribute<Meal_, String> email;
    public static volatile SingularAttribute<Meal_, String> password;
    public static volatile SingularAttribute<Meal_, Boolean> enabled;
    public static volatile SingularAttribute<Meal_, Date> registered;
    public static volatile SetAttribute<Meal_, Role> roles;
    public static volatile SingularAttribute<Meal_, Integer> id;
    public static volatile SingularAttribute<Meal_, Integer> caloriesPerDay;
    public static volatile SingularAttribute<Meal_, String> name;

    public User_() {
    }

    public User_(Integer id, String name) {
        super(id, name);
    }
}