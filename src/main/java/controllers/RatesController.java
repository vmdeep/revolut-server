package controllers;

import com.avaje.ebean.Ebean;
import model.Rate;
import model.types.CurrencyTypes;

import javax.inject.Singleton;
import javax.ws.rs.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Singleton
@Path("/Rates")
public class RatesController {


      private static Date parseDate(String date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date timestamp = null;

            try {
                  if (date == null || date.isEmpty()) {
                        timestamp = dateFormat.parse(dateFormat.format(new Date()));

                  } else {


                        timestamp = dateFormat.parse(date);

                  }
            } catch (ParseException e) {
                  throw new RuntimeException("Not valid date!");
            }

            return timestamp;
      }

      @GET
      public List<Rate> list(@QueryParam("date") String date) {

            Date timestamp = parseDate(date);

            return Ebean.find(Rate.class).where().between("date", timestamp, timestamp).findList();

      }

      @PUT
      public Integer add(@QueryParam("date") String date
              , @QueryParam("type") CurrencyTypes type
              , @QueryParam("amount") BigDecimal amount) {


            Ebean.beginTransaction();

            Rate rate = new Rate(type, amount, parseDate(date));

            Ebean.insert(rate);


            Ebean.commitTransaction();


            return rate.getId();
      }

      @POST
      public BigDecimal exchange(@QueryParam("from") String from
              , @QueryParam("to") String to
              , @QueryParam("amount") BigDecimal amount) {

            ArrayList<CurrencyTypes> types = new ArrayList<>();

            types.add(CurrencyTypes.valueOf(from));
            types.add(CurrencyTypes.valueOf(to));

            final HashMap<String, Rate> rates = new HashMap<>(2);


            Ebean.find(Rate.class).where().between("date", parseDate(null), parseDate(null)).and()
                    .in("currency_type", types)
                    .findList().forEach(rate -> {
                  rates.put(rate.getCurrencyType().name(), rate);
            });

            if (!rates.containsKey(from) || !rates.containsKey(to)) {
                  throw new RuntimeException("No valid rates for exchange");
            }

            return (rates.get(from).getRate().divide(rates.get(to).getRate(), 3, BigDecimal.ROUND_HALF_UP)).multiply(amount);
      }
}
