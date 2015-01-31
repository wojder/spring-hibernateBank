package pl.training;

import javax.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.training.facade.Bank;
import pl.training.facade.BankImpl;
import pl.training.service.persistence.AccountRepository;
import pl.training.service.persistence.ClientRepository;
import pl.training.service.persistence.JpaAccountRepository;
import pl.training.service.persistence.JpaClientRepository;
import pl.training.service.persistence.generator.AccountNumberGenerator;
import pl.training.service.persistence.generator.JpaAccountNumberGenerator;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
public class BeansConfig {
     
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
    @Bean
    public AccountNumberGenerator accountNumberGenerator() {
        return new JpaAccountNumberGenerator();
    }
    
    @Bean
    public AccountRepository accountRepository() {
        return new JpaAccountRepository();
    }
    
    @Bean
    public ClientRepository clientRepository() {
        return new JpaClientRepository();
    }

    @Bean
    public Bank bank(AccountNumberGenerator accountNumberGenerator,
            AccountRepository accountRepository, ClientRepository clientRepository) {
        return new BankImpl(accountNumberGenerator, accountRepository, clientRepository);
    }
    
    @Bean
    public ConsoleTransactionLogger consoleTransactionLogger() {
        return new ConsoleTransactionLogger();
    }
    
    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("bank");
        entityManagerFactoryBean.setPackagesToScan("pl.training.domain");
        return entityManagerFactoryBean;
    }

}
