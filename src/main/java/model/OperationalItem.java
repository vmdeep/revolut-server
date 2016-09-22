package model;

import model.types.CurrencyTypes;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Embeddable
public class OperationalItem {

    Integer accId;

    BigDecimal amount;

    CurrencyTypes currType;

    public OperationalItem(Integer accId, BigDecimal amount, CurrencyTypes currType) {
        this.accId = accId;
        this.amount = amount;
        this.currType = currType;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyTypes getCurrType() {
        return currType;
    }

    public void setCurrType(CurrencyTypes currType) {
        this.currType = currType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationalItem that = (OperationalItem) o;
        return Objects.equals(accId, that.accId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accId);
    }
}
