package pl.training.service.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.training.domain.Client;

public class JpaClientRepository implements ClientRepository {

    @PersistenceContext(unitName = "bank")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Client save(Client client) {
        entityManager.persist(client);
        entityManager.flush();
        entityManager.refresh(client);
        return client;
    }
    
}
