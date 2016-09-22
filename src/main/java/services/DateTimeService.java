package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeService {

    public Date parseDate(String date) {
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

    public Date today() {
        return this.parseDate(null);
    }
}
