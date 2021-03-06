package model;

import model.types.CurrencyTypes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Accounts")
@XmlRootElement
public class Account {

    @Id
    @GeneratedValue
    private Integer id;

    private BigDecimal amount;

    private CurrencyTypes currType;

    private Boolean deleted = false;

    public Integer getId() {
        return id;
    }

    public Account(BigDecimal amount, CurrencyTypes currType) {
        this.amount = amount;
        this.currType = currType;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
