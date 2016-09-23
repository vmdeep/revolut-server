package controllers;

import com.avaje.ebean.Ebean;
import model.Rate;
import model.types.CurrencyTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.DateTimeService;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Singleton
@Path("/Rates")
public class RatesController {


      private DateTimeService dateTimeService = new DateTimeService();

      private static final Logger log = LogManager.getLogger(AccountController.class);

      @GET
      public List<Rate> list(@QueryParam("date") String date) {

            Date timestamp = dateTimeService.parseDate(date);

            return Ebean.find(Rate.class).where().between("date", timestamp, timestamp).findList();

      }

      @POST
      public Integer add(@QueryParam("date") String date
              , @QueryParam("type") CurrencyTypes type
              , @QueryParam("amount") BigDecimal amount) {


            Ebean.beginTransaction();
            try {
                  Rate rate = new Rate(type, amount, dateTimeService.parseDate(date));

                  Ebean.insert(rate);


                  Ebean.commitTransaction();

                  return rate.getId();

            } finally {
                  Ebean.endTransaction();
            }


      }

      @GET
      @Path("/{from}")
      public BigDecimal exchange(@PathParam("from") String from
              , @QueryParam("to") String to
              , @QueryParam("amount") BigDecimal amount) {

            ArrayList<CurrencyTypes> types = new ArrayList<>();

            types.add(CurrencyTypes.valueOf(from));
            types.add(CurrencyTypes.valueOf(to));

            final HashMap<String, Rate> rates = new HashMap<>(2);


            Ebean.find(Rate.class).where().between("date", dateTimeService.today(), dateTimeService.today())
                    .and().in("currency_type", types)
                    .findList().forEach(rate -> {
                  rates.put(rate.getCurrencyType().name(), rate);
            });

            if (!rates.containsKey(from) || !rates.containsKey(to)) {
                  log.debug("Invalid rates for exchange", () -> "From: " + from + " To:" + to + " Amount: " + amount);
                  throw new WebApplicationException("Invalid rates for exchange", Response.Status.BAD_REQUEST);
            }

            return (rates.get(from).getRate().multiply(amount)).divide(rates.get(to).getRate(), 3, BigDecimal.ROUND_HALF_UP);
      }
}
