package pl.training.service.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
}
