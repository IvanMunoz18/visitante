
import java.util.Calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendario {

    static Calendar calendario;
    static String hoy;
    static Date date;

    public Calendario() {
        date = new Date();
    }

    public String getFecha() {
        String hoy, año, mes, dia;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        hoy = dateFormat.format(date);

        return hoy;
    }
}
