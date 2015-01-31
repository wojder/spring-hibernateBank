package pl.training.service.persistence.generator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.training.service.persistence.AccountRepository;

public class JpaAccountNumberGenerator implements AccountNumberGenerator {

    private static final long DEFAULT_ACCOUNT_NUMBER = 0L;
    
    private AccountRepository accountRepository;

    public JpaAccountNumberGenerator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    
    @Override
    public String getNext() {
        return String.format("%026d", getLastAccountNumber() + 1);
    }
       
    private Long getLastAccountNumber() {
        String lastAccountNumber = accountRepository.getMaxAccountNumber();
        return lastAccountNumber != null ? Long.valueOf(lastAccountNumber) : DEFAULT_ACCOUNT_NUMBER;
    }
    
}
