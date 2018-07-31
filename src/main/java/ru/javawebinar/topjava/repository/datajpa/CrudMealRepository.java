package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

/*    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int deleteByIdAndUserId(@Param("id") int id,@Param("userId") int userId);*/

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

/*    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    Optional <Meal> getByIdAndUserId(@Param("id")int id, @Param("userId") int userId);*/

    Optional<Meal> getByIdAndUserId(int id, int userId);

/*    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> findAllByUserId(@Param("userId") int userId);*/

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

/*    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("userId")int userId);*/

    List<Meal> getByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT m FROM Meal m left join fetch m.user WHERE m.id=:id AND m.user.id=:userId ORDER BY m.dateTime DESC ")
    Meal getByIdAndUserIdWithFetchedUser(@Param("id") int id, @Param("userId") int userId);
}
