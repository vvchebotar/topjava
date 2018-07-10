package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Set<Integer> mealsIdSet) {
        return mealsIdSet.contains(id) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Set<Integer> mealsIdSet) {
        return mealsIdSet.contains(id) ? repository.get(id) : null;
    }

    @Override
    public List<Meal> getAll(Set<Integer> mealsIdSet) {
        List<Meal> list = new LinkedList<>();
        mealsIdSet.forEach(a -> list.add(repository.get(a)));
        list.sort(MealsUtil::compareMealsByDate);
        return list.isEmpty() ? Collections.emptyList() : list;
    }
}

