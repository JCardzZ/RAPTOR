package ModeloClases;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendario {

    Calendar c = new GregorianCalendar();
    private static String fecha, dia, mes, año, hora, minutos, segundos, am_pm;

    public Calendario() {
        hora = Integer.toString(c.get(Calendar.HOUR));
        minutos = Integer.toString(c.get(Calendar.MINUTE));
        segundos = Integer.toString(c.get(Calendar.SECOND));
        switch (c.get(Calendar.AM_PM)) {
            case 0:
                am_pm = "am";
                break;
            case 1:
                am_pm = "pm";
                break;
        }
        dia = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        mes = Integer.toString(c.get(Calendar.MONTH));
        año = Integer.toString(c.get(Calendar.YEAR));
        fecha = dia + "-" + mes + "-" + año;
        hora = hora + ":" + minutos + ":" + segundos + " " + am_pm;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}
