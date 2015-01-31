package pl.training.service.persistence;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.training.domain.Account;

public class JpaAccountRepository implements AccountRepository {

    @PersistenceContext(unitName = "bank")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void save(Account account) {
        try {
            entityManager.persist(account);
        } catch(EntityExistsException ex) {
            throw new AccountAlreadyExistsException();
        }
    }

    @Override
    public Account read(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return account;
    }

    @Override
    public List<Account> read(int startIndex, int pageSize) {
        return entityManager.createNamedQuery(Account.GET_ALL, Account.class)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public void update(Account account) {
        read(account.getNumber());
        entityManager.merge(account);
    }
    
}
