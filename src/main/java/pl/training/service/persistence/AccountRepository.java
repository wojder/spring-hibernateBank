package pl.training.service.persistence;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.training.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
    
    List<Account> getByBalance(BigDecimal balance);
    
    @Query("select max(a.number) from Account a")
    String getMaxAccountNumber();
    
    @Transactional
    @Modifying
    @Query("delete form Account a where a.number = number")
    void deleteByAccountNumber(@Param("number") String number);
    
}
