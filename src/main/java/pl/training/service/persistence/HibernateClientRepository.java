package pl.training.service.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.training.domain.Client;

public class HibernateClientRepository implements ClientRepository {
    
    private SessionFactory sessionFactory;

    public HibernateClientRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Client save(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.save(client);
        session.flush();
        session.refresh(client);
        return client;
    }
    
    
}
