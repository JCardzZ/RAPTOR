
package Modelo;

import java.text.DecimalFormat;
import java.text.ParseException;


public class ClsFormatoDecimal {
    DecimalFormat formateador = new DecimalFormat("###,###,###.00");
    Number numero;
    public String decimal(double formato){
        return formateador.format(formato);
        
    }
    public double reconstruir(String formato){
        try {
            numero= formateador.parse(formato);
            
        } catch (ParseException ex) {
            System.out.println("Error: "+ ex);
        }
        return numero.doubleValue();
    }
}
