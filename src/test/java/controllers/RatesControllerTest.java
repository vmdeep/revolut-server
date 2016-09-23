package controllers;

import com.avaje.ebean.Ebean;
import model.Rate;
import model.types.CurrencyTypes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class RatesControllerTest {

    public RatesController ratesController = new RatesController();

    @Before
    public void setUp() throws Exception {
        Ebean.beginTransaction();
        Ebean.deleteAll(Ebean.find(Rate.class).findList());

        Ebean.commitTransaction();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void list() throws Exception {


        ratesController.add("22-02-2004", CurrencyTypes.USD, new BigDecimal(100));
        ratesController.add("22-02-2005", CurrencyTypes.USD, new BigDecimal(100));
        ratesController.add("22-02-2006", CurrencyTypes.USD, new BigDecimal(100));

        ratesController.list("22-02-2004").stream().forEach(
                r -> {
                    try {
                        Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").parse("22-02-2004"), r.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        );

        Assert.assertTrue(ratesController.list("22-02-2004").size() == 1);

    }

    @Test
    public void add() throws Exception {
        Integer id =
                ratesController.add("22-02-2007", CurrencyTypes.EUR, new BigDecimal(100));

        Rate r = Ebean.find(Rate.class, id);

        Assert.assertEquals(CurrencyTypes.EUR, r.getCurrencyType());
        Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").parse("22-02-2007"), r.getDate());


    }

    @Test
    public void exchange() throws Exception {
        ratesController.add(null, CurrencyTypes.RUB, new BigDecimal(1));
        ratesController.add(null, CurrencyTypes.EUR, new BigDecimal(70));
        ratesController.add(null, CurrencyTypes.USD, new BigDecimal(60));

        Assert.assertEquals(new BigDecimal("8.571"),
                ratesController.exchange("USD", "EUR", new BigDecimal(10))
        );


    }

}