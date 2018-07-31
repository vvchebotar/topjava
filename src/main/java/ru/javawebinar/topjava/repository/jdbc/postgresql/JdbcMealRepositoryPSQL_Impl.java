package ru.javawebinar.topjava.repository.jdbc.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;

@Repository
public class JdbcMealRepositoryPSQL_Impl extends JdbcMealRepository {
    @Autowired
    public JdbcMealRepositoryPSQL_Impl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
}
