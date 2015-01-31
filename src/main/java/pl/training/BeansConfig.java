package pl.training;

import javax.persistence.EntityManagerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.training.facade.Bank;
import pl.training.facade.BankImpl;
import pl.training.service.persistence.AccountRepository;
import pl.training.service.persistence.ClientRepository;
import pl.training.service.persistence.generator.AccountNumberGenerator;
import pl.training.service.persistence.generator.JpaAccountNumberGenerator;

@ComponentScan(basePackages = "pl.training.controller")
@EnableWebMvc
@EnableJpaRepositories(basePackages = "pl.training.service.persistence")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
public class BeansConfig {
     
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
    @Bean
    public AccountNumberGenerator accountNumberGenerator(AccountRepository accountRepository) {
        return new JpaAccountNumberGenerator(accountRepository);
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
        return entityManagerFactoryBean;
    }
    
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("classpath:bank");
        return messageSource;
    }
}
