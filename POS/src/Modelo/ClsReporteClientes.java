package Modelo;

public class ClsReporteClientes extends ClsClientes {

    private int IDRegistro;
    private int IDCliente;
    private String SaldoActual;
    private String FechaActual;
    private String UltimoPago;
    private String FechaPago;
    private String ID;

    public ClsReporteClientes() {
    }

    public int getIDRegistro() {
        return IDRegistro;
    }

    public void setIDRegistro(int IDRegistro) {
        this.IDRegistro = IDRegistro;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }

    public String getSaldoActual() {
        return SaldoActual;
    }

    public void setSaldoActual(String SaldoActual) {
        this.SaldoActual = SaldoActual;
    }

    public String getFechaActual() {
        return FechaActual;
    }

    public void setFechaActual(String FechaActual) {
        this.FechaActual = FechaActual;
    }

    public String getUltimoPago() {
        return UltimoPago;
    }

    public void setUltimoPago(String UltimoPago) {
        this.UltimoPago = UltimoPago;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String FechaPago) {
        this.FechaPago = FechaPago;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

   
  
}
