package pl.training;

import java.math.BigDecimal;
import java.text.NumberFormat;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import pl.training.facade.BankOperationException;

@Aspect
public class ConsoleTransactionLogger {

    private static final String SEPARATOR = "####################################################################";
    
    @Pointcut("execution(* pl.training.facade.Bank.*Cash*(..))")
    private void financialOperations() {
    }
    
    private String formatCurrency(BigDecimal value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }
    
    @Before("execution(* pl.training.facade.Bank.payInCashToAccount(..)) && args(accountNumber,amount)")
    public void beforePayInCashToAccount(String accountNumber, BigDecimal amount) {
        System.out.println(SEPARATOR);
        System.out.println("Rozpoczęcie operacji: Wpłata");
        System.out.println(accountNumber + " <== " + formatCurrency(amount));
    }
    
    @Before("execution(* pl.training.facade.Bank.payOutCashFromAccount(..)) && args(accountNumber,amount)")
    public void beforePayOutCashFromAccount(String accountNumber, BigDecimal amount) {
        System.out.println(SEPARATOR);
        System.out.println("Rozpoczęcie operacji: Wypłata");
        System.out.println(accountNumber + " ==> " + formatCurrency(amount));
    }

    @Before("execution(* pl.training.facade.Bank.transferCash(..)) && args(fromAccountNumber,toAccountNumber,amount)")
    public void beforeTransferCash(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        System.out.println(SEPARATOR);
        System.out.println("Rozpoczęcie operacji: Przelew");
        System.out.println(fromAccountNumber + " ==> " + formatCurrency(amount) + " ==> " + toAccountNumber);
    }
    
    @AfterReturning("financialOperations()")
    public void onSuccess() {
        System.out.println("Operacja zakończona poprawnie");
        System.out.println(SEPARATOR);
    }

    @AfterThrowing(value = "financialOperations()", throwing = "ex")
    public void onFailure(BankOperationException ex) {
        System.out.println("Operacja zakończona błędem ("
            + ex.getClass().getSimpleName() + ")");
        System.out.println(SEPARATOR);
    }
    
}
