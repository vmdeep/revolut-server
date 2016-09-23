package model;

import com.avaje.ebean.Ebean;
import model.types.CurrencyTypes;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


public class AccountTest {




    @Test
    public void insert() {


        Account acc = new Account(new BigDecimal(100), CurrencyTypes.EUR);

        Ebean.beginTransaction();
        Ebean.insert(acc);
        Ebean.commitTransaction();

        Integer id = acc.getId();

        Account loadedAcc = Ebean.find(Account.class, id);

        Assert.assertEquals(CurrencyTypes.EUR, loadedAcc.getCurrType());

        Assert.assertEquals(new BigDecimal(100), loadedAcc.getAmount());

        Assert.assertEquals(id, loadedAcc.getId());

        Assert.assertFalse(loadedAcc.isDeleted());


    }


    @Test
    public void update() {
        Account acc = new Account(new BigDecimal(100), CurrencyTypes.EUR);

        Ebean.beginTransaction();

        Ebean.insert(acc);

        Ebean.commitTransaction();

        Integer id = acc.getId();

        Account loadedAcc = Ebean.find(Account.class, id);

        loadedAcc.setAmount(loadedAcc.getAmount().add(new BigDecimal(100)));

        Ebean.beginTransaction();

        Ebean.update(loadedAcc);

        Ebean.commitTransaction();

        Account updatedAcc = Ebean.find(Account.class, id);

        Assert.assertEquals(new BigDecimal(200), updatedAcc.getAmount());

    }

    @Test
    public void delete() {
        Account acc = new Account(new BigDecimal(100), CurrencyTypes.EUR);

        Ebean.beginTransaction();


        Ebean.insert(acc);


        Ebean.commitTransaction();


        Ebean.beginTransaction();

        Ebean.delete(Account.class, acc.getId());

        Ebean.commitTransaction();


        Account loadedAcc = Ebean.find(Account.class, acc.getId());

        Assert.assertNull(loadedAcc);

    }

}