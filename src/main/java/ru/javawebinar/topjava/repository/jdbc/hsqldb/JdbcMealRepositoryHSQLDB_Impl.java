package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class JdbcMealRepositoryHSQLDB_Impl extends JdbcMealRepository {
    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("date_time", getFormattedDateTime(meal));
        completeMapForSave(meal, userId, map);
        return save(meal, map);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, getFormattedDateTime(startDate), getFormattedDateTime(endDate));
    }

    protected String getFormattedDateTime(Meal meal) {
        return getFormattedDateTime(meal.getDateTime());
    }

    protected String getFormattedDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
