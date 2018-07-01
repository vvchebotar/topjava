package ru.javawebinar.topjava.service;

import java.io.Serializable;

public interface GenericService<T, PK extends Serializable> {

    PK create(T newInstance);

    T retrieve(Class<T> clazz, PK id);

    void update(T transientObject);

    void delete(T persistentObject);
}
