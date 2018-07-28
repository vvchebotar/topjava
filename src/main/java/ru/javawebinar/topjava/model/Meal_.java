package ru.javawebinar.topjava.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

// этот класс относится к Criteria. 
//@StaticMetamodel(Meal.class)
public abstract class Meal_ extends AbstractBaseEntity {
    public static volatile SingularAttribute<Meal_, LocalDateTime> dateTime;
    public static volatile SingularAttribute<Meal_, String> description;
    public static volatile SingularAttribute<Meal_, Integer> calories;
    public static volatile SingularAttribute<Meal_, Integer> id;
    public static volatile SingularAttribute<Meal_, User> user;

    public Meal_() {
    }

    public Meal_(Integer id) {
        super(id);
    }
}
