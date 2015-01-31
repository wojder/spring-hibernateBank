package pl.training.service.persistence;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pl.training.domain.Account;

public class JdbcAccountRepository implements AccountRepository {

    private static final String INSERT_ACCOUNT = "insert into accounts values(:number,:balance,0)";
    private static final String INSERT_ACCOUNTS_CLIENTS = "insert into accounts_clients values(:number,:clientId)";

    private static final String SELECT_ACCOUNT = "select * from accounts where number = :number";
    private static final String SELECT_ACCOUNTS = "select * from accounts limit :startIdx, :pageSize";
    
    private static final String UPDATE_ACCOUNT = "update accounts set balance = :balance where number = :number";
    
    private AccountsListExtractor accountsListExtractor = new AccountsListExtractor();
    private NamedParameterJdbcTemplate jdbcTemplate;
        
    public JdbcAccountRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Override
    public void save(Account account) {
        jdbcTemplate.update(INSERT_ACCOUNT, new BeanPropertySqlParameterSource(account));
        account.getClients().forEach((client) -> {
            jdbcTemplate.update(INSERT_ACCOUNTS_CLIENTS, new MapSqlParameterSource()
                    .addValue("number", account.getNumber())
                    .addValue("clientId", client.getId()));
        });
    }
    
    @Override
    public Account read(String accountNumber) {
        List<Account> result = jdbcTemplate.query(SELECT_ACCOUNT,
                new MapSqlParameterSource("number", accountNumber),
                accountsListExtractor);
        if (result.size() != 1) {
            throw new AccountDoesNotExistException();
        }
        return result.get(0);
    }

    @Override
    public List<Account> read(int startIndex, int pageSize) {
        return jdbcTemplate.query(SELECT_ACCOUNTS, new MapSqlParameterSource()
                .addValue("startIdx", startIndex)
                .addValue("pageSize", pageSize),
                accountsListExtractor);            
    }

    @Override
    public void update(Account account) {
        if (jdbcTemplate.update(UPDATE_ACCOUNT,
                new BeanPropertySqlParameterSource(account)) == 0) {
            throw new AccountDoesNotExistException();
        }
    }
    
}
