package pl.training.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;

@Entity
public class SuperClient extends Client{
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private Long id;
    private BigDecimal discount;

    public SuperClient() {
    }

    public SuperClient(String firstName, String lastName, Address address) {
        super(firstName, lastName, address);
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    } 
}
