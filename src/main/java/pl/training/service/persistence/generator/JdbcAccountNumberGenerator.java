package pl.training.service.persistence.generator;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcAccountNumberGenerator implements AccountNumberGenerator {

    private static final String SELECT_MAX_ACCOUNT_NUMBER = "select max(number) from accounts";
    private static final long DEFAULT_ACCOUNT_NUMBER = 0L;
    
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public JdbcAccountNumberGenerator(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Override
    public String getNext() {
        return String.format("%026d", getLastAccountNumber() + 1);
    }
    
    private Long getLastAccountNumber() {
        Long lastAccountNumber = jdbcTemplate.queryForObject(SELECT_MAX_ACCOUNT_NUMBER
                , new MapSqlParameterSource(), Long.class);
        return lastAccountNumber != null ? lastAccountNumber : DEFAULT_ACCOUNT_NUMBER;
    }
    
}
