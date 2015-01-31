package pl.training.service.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
    
}
