package controllers;

import com.avaje.ebean.Ebean;
import model.Account;
import model.OperationalBook;
import model.OperationalItem;
import services.DateTimeService;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.math.BigDecimal;
import java.util.List;

@Singleton
@Path("/Accounts")
public class AccountController {
    /*
        Need to be dependency injection but try to keep the App simple as possible.
        Good solution here to add HK2 lib
     */
    private RatesController ratesController = new RatesController();

    private DateTimeService dateTimeService = new DateTimeService();

    @GET
    public List<Account> list(@QueryParam("page") Integer page) {

        if (page == null) {
            page = 1;

        }

        return Ebean.find(Account.class)
                .setFirstRow(((page - 1) * 10) + 1)
                .setMaxRows(page * 10)
                .orderBy("id")
                .findList();

    }


    @GET
    @Path("/{id}")
    public Account getById(@PathParam("id") Integer id) {

        Account acc = Ebean.find(Account.class, id);

        if (acc == null) {
            throw new RuntimeException("Account with id " + id + " not found!");
        }
        return acc;
    }

    @PUT
    @Path("/{id}")
    public Integer enroll(@PathParam("id") Integer id
            , @QueryParam("from") @NotNull Integer from
            , @QueryParam("amount") @NotNull BigDecimal amount) {

        Ebean.beginTransaction();


        Account toAcc = this.getById(id);

        Account fromAcc = this.getById(from);

        BigDecimal toAmount = ratesController.exchange("EUR", "RUB", amount);

        if (fromAcc.getAmount().compareTo(amount) == -1) {
            Ebean.rollbackTransaction();
            throw new RuntimeException("Not enough money for the operation");

        }

        fromAcc.setAmount(fromAcc.getAmount().subtract(amount));

        toAcc.setAmount(toAmount);

        Ebean.update(fromAcc);

        Ebean.update(toAcc);

        OperationalBook book = new OperationalBook(
                new OperationalItem(fromAcc.getId(), amount, fromAcc.getCurrType()),
                new OperationalItem(toAcc.getId(), toAmount, toAcc.getCurrType()),
                dateTimeService.today()
        );

        Ebean.insert(book);


        Ebean.commitTransaction();


        return book.getId();
    }


}
