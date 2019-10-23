package Conexion;

import Modelo.ClsCajas;
import Modelo.ClsClientes;
import Modelo.ClsDepartamentos;
import Modelo.ClsProveedores;
import Modelo.ClsReporteClientes;
import Modelo.ClsReporteProveedores;
import Modelo.ClsUsuarios;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

public class Consulta extends Conexion {

    public QueryRunner QR = new QueryRunner();
    private List<ClsClientes> cliente;
    private List<ClsReporteClientes> reportescliente;
    private List<ClsReporteProveedores> reporteproveedores;
    private List<ClsDepartamentos> departamento;
    private List<ClsProveedores> proveedor;
    private List<ClsUsuarios> usuarios;
    private List<ClsCajas> cajas;

    public List<ClsClientes> clientes() {
        try {
            cliente = (List<ClsClientes>) QR.query(getConn(),
                    "Select * From Clientes",
                    new BeanListHandler(ClsClientes.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return cliente;
    }

    public void Insertar(String sql, Object[] data) {
        try {
            final QueryRunner qr = new QueryRunner(true);
            qr.insert(getConn(), sql, new ColumnListHandler(), data);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);

        }
    }

    public List<ClsReporteClientes> reportesClientes(int idCliente) {
        String condicion = " Clientes.IDCliente = reporteclientes.IDCliente ";
        String campos = "Clientes.IDCliente,Clientes.ID,Clientes.Nombres,"
                + "Clientes.Apellidos,reporteclientes.IDRegistro,reporteclientes.SaldoActual,"
                + "reporteclientes.FechaActual,reporteclientes.UltimoPago,reporteclientes.FechaPago ";
        try {
            reportescliente = (List<ClsReporteClientes>) QR.query(getConn(),
                    "SELECT " + campos + " From reporteclientes Inner Join Clientes ON "
                    + condicion + " WHERE reporteclientes.IDCliente ="
                    + idCliente, new BeanListHandler(ClsReporteClientes.class));
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        return reportescliente;
    }

    public void Actualizar(String sql, Object[] data) {
        try {
            final QueryRunner qr = new QueryRunner(true);
            qr.update(getConn(), sql, data);

        } catch (SQLException ex) {
            System.out.println("Error : " + ex);
        }
    }

    public void Eliminar(String sql, int id) {
        try {
            final QueryRunner qr = new QueryRunner(true);
            qr.update(getConn(), sql, "%" + id + "%");
        } catch (SQLException ex) {
            System.out.println("Error : " + ex);

        }

    }

    public List<ClsProveedores> Proveedores() {
        try {
            proveedor = (List<ClsProveedores>) QR.query(getConn(), "SELECT * FROM Proveedores",
                    new BeanListHandler(ClsProveedores.class));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return proveedor;
    }

    public List<ClsReporteProveedores> reportesProveedor(int idProveedor) {
        String condicion = "proveedores.IdProveedor = reporteproveedores.IdProveedor ";
        String campos = "proveedores.IdProveedor, proveedores.Proveedor,"
                + "reporteproveedores.IdRegistro, reporteproveedores.SaldoActual,"
                + "reporteproveedores.FechaActual,reporteproveedores.UltimoPago,"
                + "reporteproveedores.FechaPago ";
        try {
            reporteproveedores = (List<ClsReporteProveedores>) QR.query(getConn(),
                    "SELECT " + campos + "FROM reporteproveedores Inner Join proveedores ON "
                    + condicion + "WHERE reporteproveedores.IdProveedor ="
                    + idProveedor, new BeanListHandler(ClsReporteProveedores.class));;
        } catch (SQLException ex) {
            System.out.println("Error en ReporteProveedor : " + ex);
        }
        return reporteproveedores;

    }

    public List<ClsUsuarios> Usuarios() {
        try {
            usuarios = (List<ClsUsuarios>) QR.query(getConn(), "Select * From Usuarios",
                    new BeanListHandler(ClsUsuarios.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return usuarios;
    }

    public List<ClsCajas> cajas() {
        try {
            cajas = (List<ClsCajas>) QR.query(getConn(), "Select * From Cajas",
                    new BeanListHandler(ClsCajas.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return cajas;
    }

    public List<ClsDepartamentos> departamentos() {
        try {
            departamento = (List<ClsDepartamentos>) QR.query(getConn(), "Select * From Departamentos",
                    new BeanListHandler(ClsDepartamentos.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return departamento;
    }
}
