package pl.training.service.persistence.generator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaAccountNumberGenerator implements AccountNumberGenerator {

    private static final String SELECT_MAX_ACCOUNT_NUMBER = "select max(a.number) from Account a";
    private static final long DEFAULT_ACCOUNT_NUMBER = 0L;

    @PersistenceContext(unitName = "bank")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public String getNext() {
        return String.format("%026d", getLastAccountNumber() + 1);
    }
       
    private Long getLastAccountNumber() {
        String lastAccountNumber = entityManager
                .createQuery(SELECT_MAX_ACCOUNT_NUMBER, String.class)
                .getSingleResult();
        return lastAccountNumber != null ? Long.valueOf(lastAccountNumber) : DEFAULT_ACCOUNT_NUMBER;
    }
    
}
