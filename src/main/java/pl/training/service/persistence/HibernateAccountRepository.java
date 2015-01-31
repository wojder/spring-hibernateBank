package pl.training.service.persistence;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import pl.training.domain.Account;

public class HibernateAccountRepository implements AccountRepository {

    private SessionFactory sessionFactory;

    public HibernateAccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void save(Account account) {
        try {
            sessionFactory.getCurrentSession().save(account);
        } catch (ConstraintViolationException ex) {
            throw  new AccountAlreadyExistsException();
        }
    }

    @Override
    public Account read(String accountNumber) {
        Account account = (Account) sessionFactory
                .getCurrentSession()
                .get(Account.class, accountNumber);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return account;
    }

    @Override
    public List<Account> read(int startIndex, int pageSize) {
        return sessionFactory.getCurrentSession()
                .createQuery("select a from Account a")
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .list();
    }

    @Override
    public void update(Account account) {
        read(account.getNumber());
        sessionFactory.getCurrentSession().update(account);
    }
    
}
