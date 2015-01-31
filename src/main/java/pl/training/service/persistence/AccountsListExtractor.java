package pl.training.service.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import pl.training.domain.Account;

public class AccountsListExtractor implements ResultSetExtractor<List<Account>> {

    @Override
    public List<Account> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Account> result = new ArrayList<>();
        while (rs.next()) {
            Account account = new Account(rs.getString("number"));
            account.setBalance(rs.getBigDecimal("balance"));
            result.add(account);
        }
        return result;
    }
    
}
