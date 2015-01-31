package pl.training.domain;

import java.math.BigDecimal;

public class SuperClient extends Client{
    
    private BigDecimal discount;

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    } 
}
