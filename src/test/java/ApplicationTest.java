import com.avaje.ebean.Ebean;
import model.Account;
import model.Rate;
import model.types.CurrencyTypes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.DateTimeService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

public class ApplicationTest {

    private DateTimeService dateTimeService = new DateTimeService();

    @Before
    public void setUp() throws Exception {
        Ebean.beginTransaction();

        Ebean.insert(new Rate(CurrencyTypes.EUR, new BigDecimal(70), dateTimeService.today()));
        Ebean.insert(new Rate(CurrencyTypes.USD, new BigDecimal(60), dateTimeService.today()));
        Ebean.insert(new Rate(CurrencyTypes.RUB, new BigDecimal(1), dateTimeService.today()));


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
        Application.main(null);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9998").path("Accounts/1");


        Account acc = target.request().buildGet().invoke().readEntity(Account.class);

        Assert.assertEquals(new BigDecimal(100), acc.getAmount());

        Response r = target.queryParam("from", new Integer(2)).queryParam("amount", new BigDecimal(10)).request().method("POST");

        Assert.assertEquals(200, r.getStatus());

    }
}
