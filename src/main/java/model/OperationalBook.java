package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class OperationalBook {

    @Id
    @GeneratedValue
    private Integer id;

    @Embedded
    private OperationalItem from;

    @Embedded
    private OperationalItem to;

    private Date date;

    public OperationalBook(OperationalItem from, OperationalItem to, Date date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OperationalItem getFrom() {
        return from;
    }

    public void setFrom(OperationalItem from) {
        this.from = from;
    }

    public OperationalItem getTo() {
        return to;
    }

    public void setTo(OperationalItem to) {
        this.to = to;
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
        OperationalBook that = (OperationalBook) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
