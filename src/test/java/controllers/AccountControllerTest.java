package controllers;

import com.avaje.ebean.Ebean;
import model.Account;
import model.Rate;
import model.types.CurrencyTypes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.DateTimeService;

import java.math.BigDecimal;
import java.util.List;

public class AccountControllerTest {

    private AccountController accountController = new AccountController();
    private DateTimeService dateTimeService = new DateTimeService();

    @Before
    public void setUp() throws Exception {
        Ebean.beginTransaction();
        Ebean.deleteAll(Ebean.find(Rate.class).findList());
        Ebean.deleteAll(Ebean.find(Account.class).findList());

        Ebean.commitTransaction();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void list() throws Exception {
        this.setUp();

        Ebean.beginTransaction();
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.EUR));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.USD));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.RUB));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.EUR));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.USD));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.RUB));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.EUR));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.USD));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.RUB));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.EUR));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.USD));
        Ebean.insert(new Account(new BigDecimal(100), CurrencyTypes.RUB));

        Ebean.commitTransaction();

        List<Account> list = accountController.list(1);

        Assert.assertEquals(10, list.size());

        list = accountController.list(2);
        Assert.assertTrue(list.size() <= 10);
    }

    @Test
    public void getById() throws Exception {
        Account acc = new Account(new BigDecimal(100), CurrencyTypes.RUB);
        Ebean.beginTransaction();

        Ebean.insert(acc);

        Ebean.commitTransaction();

        Account fAcc =
                accountController.getById(acc.getId());

        Assert.assertEquals(new BigDecimal(100), fAcc.getAmount());

        Assert.assertEquals(CurrencyTypes.RUB, fAcc.getCurrType());


    }

    @Test
    public void enroll() throws Exception {


        Account fromAcc = new Account(new BigDecimal(100), CurrencyTypes.EUR);
        Account toAcc = new Account(new BigDecimal(100), CurrencyTypes.USD);
        Ebean.beginTransaction();

        Ebean.insert(new Rate(CurrencyTypes.EUR, new BigDecimal(70), dateTimeService.today()));
        Ebean.insert(new Rate(CurrencyTypes.USD, new BigDecimal(60), dateTimeService.today()));
        Ebean.insert(new Rate(CurrencyTypes.RUB, new BigDecimal(1), dateTimeService.today()));

        Ebean.insert(fromAcc);
        Ebean.insert(toAcc);

        Ebean.commitTransaction();


        accountController.enroll(toAcc.getId(), fromAcc.getId(), new BigDecimal(10));

        accountController.enroll(fromAcc.getId(), toAcc.getId(), new BigDecimal(12));

        fromAcc = Ebean.find(Account.class, fromAcc.getId());
        toAcc = Ebean.find(Account.class, toAcc.getId());

        Assert.assertEquals(fromAcc.getAmount(), toAcc.getAmount());





    }

    @Test(expected = javax.ws.rs.WebApplicationException.class)
    public void enroll_bad() throws Exception {

        Ebean.beginTransaction();
        Ebean.deleteAll(Ebean.find(Rate.class).findList());
        Ebean.deleteAll(Ebean.find(Account.class).findList());

        Ebean.commitTransaction();

        Account fromAcc = new Account(new BigDecimal(100), CurrencyTypes.EUR);
        Account toAcc = new Account(new BigDecimal(100), CurrencyTypes.USD);
        Ebean.beginTransaction();

        Ebean.insert(new Rate(CurrencyTypes.EUR, new BigDecimal(70), dateTimeService.today()));
        Ebean.insert(new Rate(CurrencyTypes.USD, new BigDecimal(60), dateTimeService.today()));
        Ebean.insert(new Rate(CurrencyTypes.RUB, new BigDecimal(1), dateTimeService.today()));

        Ebean.insert(fromAcc);
        Ebean.insert(toAcc);

        Ebean.commitTransaction();


        accountController.enroll(toAcc.getId(), fromAcc.getId(), new BigDecimal(200));


    }

}