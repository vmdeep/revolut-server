package controllers;

import com.avaje.ebean.Ebean;
import model.Account;

import javax.ws.rs.*;
import java.math.BigDecimal;
import java.util.List;

@Path("/Accounts")
public class AccountController {

    @GET
    public List<Account> list(@QueryParam("page") Integer page) {

        if (page == null) {
            page = 1;

        }

        return Ebean.find(Account.class)
                .setFirstRow(((page - 1) * 10) + 1)
                .setMaxRows(page * 10).orderBy("id")
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
            , @QueryParam("from") Integer from
            , @QueryParam("amount") BigDecimal amount) {

        return null;
    }


}
