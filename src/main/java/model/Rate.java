package model;

import model.types.CurrencyTypes;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Rates",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"currency_type", "date"})
)
@XmlRootElement
public class Rate {

    @Id
    @GeneratedValue
    Integer id;

    CurrencyTypes currencyType;

    BigDecimal rate;

    Date date;

    public Rate(CurrencyTypes currencyType, BigDecimal rate, Date date) {
        this.currencyType = currencyType;
        this.rate = rate;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyTypes getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyTypes currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(id, rate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
