package nl.utwente.ing.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2Database {
    @Autowired JdbcTemplate jdbcTemplate;
}
