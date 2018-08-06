package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries(value = {
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM  Meal WHERE id=:id AND user.id=:userId"),
        @NamedQuery(name = Meal.GET_ALL_SORTED, query = "SELECT m FROM  Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
//        @NamedQuery(name = "тоже что и GET_ALL_SORTED", query = "SELECT m FROM  Meal m JOIN m.user u WHERE u.id=:user_id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_SORTED, query = "SELECT m FROM Meal m " +
                "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC"),
//        @NamedQuery(name = "тоже что и GET_BETWEEN_SORTED", query = "SELECT m FROM   Meal m JOIN m.user u WHERE u.id=:user_id AND m.dateTime BETWEEN :start_time AND :end_time ORDER BY m.dateTime DESC"),
//        distinct - чтобы не создавались на каждый fetch отдельной копии объекта, а один объект - коллекция ассоц объектов
//        @NamedQuery(name = "Contact.findById", query = "select distinct m from Meal m left join fetch m.user u  where m.id = :id"),
//        fetch - чтобы добавить асооц объект к объекту. и мы можем вытягивать поля.
//        @NamedQuery(name = "select1", query = "select m.user from Meal m left join m.user u where u.caloriesPerDay = 2000"),
//        получим List масивов объектов по select. List<object[]> list
//        @NamedQuery(name = "select1", query = "select m.description, u.email, u.name, u.password from Meal m left join m.user u"),
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {
    public static final String DELETE = "Meal.delete";
    public static final String GET_ALL_SORTED = "Meal.getAllSorted";
    public static final String GET_BETWEEN_SORTED = "Meal.getBetweenSorted";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(max = 255)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 0, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, User user) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
