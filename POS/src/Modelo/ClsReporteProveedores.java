package Modelo;

public class ClsReporteProveedores extends ClsProveedores {

    private int IDRegistro;
    private int IDProveedor;
    private String SaldoActual;
    private String FechaActual;
    private String UltimoPago;
    private String FechaPago;

    public int getIDRegistro() {
        return IDRegistro;
    }

    public void setIDRegistro(int IDRegistro) {
        this.IDRegistro = IDRegistro;
    }

    public int getIDProveedor() {
        return IDProveedor;
    }

    public void setIDProveedor(int IDProveedor) {
        this.IDProveedor = IDProveedor;
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

}
