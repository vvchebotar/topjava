package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)// обеспечивает более высоку производительность для методов только чтения @springprof
public class JpaMealRepositoryImpl implements MealRepository {
    // контекстов может быть несколько и их нужно именовать (unitName = "")
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        // делаем обертку над entity User у которого id==userId, но в базу запрос не идет
        User userRef = em.getReference(User.class, userId);
        // добавляем к Meal без юзера ссылку на обертку над юзером
        meal.setUser(userRef);
        if (meal.isNew()) {
            // сохраняем в БД
            em.persist(meal);
            // откуда у meal появляется id? в доках persist() я не смог найти...
            return meal;
        } else {
            Meal queriedMeal = get(meal.getId(), userId);
            if (queriedMeal == null) {
                return null;
            }
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    //    чтобы удалить объект из базы нужно сразу merge, а потом remove
    @Transactional
    public void deleteMeal(Meal meal) {
        Meal merged = em.merge(meal);
        em.remove(merged);
    }

    public Meal get(int id, int userId) {
        // можно получить как у EntityManagerFactory, так и у EntityManager. Они эквивалентны
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Meal> query = cb.createQuery(Meal.class);
        Root<Meal> mealRoot = query.from(Meal.class);
        //  создаем параметры
        ParameterExpression<Integer> idParam = cb.parameter(Integer.class);
        ParameterExpression<Integer> userIdParam = cb.parameter(Integer.class);
        // фетч не обязательно потому что по id
        //        mealRoot.fetch("user", JoinType.LEFT);
        // достаем по mealId and userId.
        query.select(mealRoot).where(cb.and(cb.equal(mealRoot.get("id"), idParam), cb.equal(mealRoot.get("user").get("id"), userIdParam)));
        // достаем калории больше указанных. МОжно через параметры сделать.
        //        query.select(mealRoot).where(cb.gt(mealRoot.get("calories"), 1000));
        //        query.select(mealRoot).where(cb.gt(mealRoot.get("calories"), 1000));
        // чтобы вставить параметры в query
        TypedQuery<Meal> typedQuery = em.createQuery(query);
        typedQuery.setParameter(idParam, id);
        typedQuery.setParameter(userIdParam, userId);
        List<Meal> meals = typedQuery.getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
//        тут мы прямо в HQL делаем объект, собираем в него информацию с разных других объектов и передаем в лист
//        List<ContactSummry> result = em.createQuery(
//                "select new com.apress.prospring4.ch8.ContactSummary(c.firstName, c.lastName, t.telNumЬer) from Contact с left join c.contactTelDetails t where t.telType= 'Home'",
//                ContactSummary.class).getResultList();
        return em.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_time", startDate)
                .setParameter("end_time", endDate).getResultList();
    }
}