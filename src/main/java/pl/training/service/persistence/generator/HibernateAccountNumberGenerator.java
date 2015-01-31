package pl.training.service.persistence.generator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateAccountNumberGenerator implements AccountNumberGenerator {

    private static final String SELECT_MAX_ACCOUNT_NUMBER = "select max(a.number) from Account a";
    private static final long DEFAULT_ACCOUNT_NUMBER = 0L;
    
    private SessionFactory sessionFactory;

    public HibernateAccountNumberGenerator(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public String getNext() {
        return String.format("%026d", getLastAccountNumber() + 1);
    }

    private Long getLastAccountNumber() {
        Session session = sessionFactory.getCurrentSession();
        String lastAccountNumber = (String) session
                .createQuery(SELECT_MAX_ACCOUNT_NUMBER)
                .uniqueResult();
        return lastAccountNumber != null ? Long.valueOf(lastAccountNumber) : DEFAULT_ACCOUNT_NUMBER;
    }
    
}
