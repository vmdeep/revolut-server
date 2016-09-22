package controllers;

import com.avaje.ebean.Ebean;
import model.OperationalBook;
import services.DateTimeService;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@Singleton
@Path("/Book")
public class OperationalBookController {

    private DateTimeService dateTimeService = new DateTimeService();

    @GET
    public List<OperationalBook> list(@QueryParam("page") Integer page, @QueryParam("date") String date) {
        return Ebean.find(OperationalBook.class)
                .setFirstRow(((page - 1) * 10) + 1)
                .setMaxRows(page * 10)
                .where()
                .between("date", dateTimeService.parseDate(date), dateTimeService.parseDate(date))
                .orderBy("id")
                .findList();
    }

    @GET
    @Path("/{id}")
    public OperationalBook get(@PathParam("id") Integer id) {
        return Ebean.find(OperationalBook.class, id);
    }
}
