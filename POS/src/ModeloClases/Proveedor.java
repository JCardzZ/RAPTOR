package ModeloClases;

import Conexion.Consulta;
import Modelo.ClsProveedores;
import Modelo.ClsReporteProveedores;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class Proveedor extends Consulta implements Printable {

    private DefaultTableModel modelo, modelo2;
    private List<ClsProveedores> proveedor, proveedor1, proveedor2, proveedorFilter;
    private String Sql;
    private Object[] object;
    private JPanel panel;

    public List<ClsProveedores> InsertarProveedores(String Proveedor, String Email, String Telefono) {

        proveedor1 = Proveedores().stream()
                .filter(P -> P.getTelefono().equals(Telefono) || P.getEmail().equals(Email))
                .collect(Collectors.toList());
        if (0 == proveedor1.size()) {
            Sql = "INSERT INTO Proveedores (Proveedor ,Telefono,Email)"
                    + "VALUES(?,?,?)";
            object = new Object[]{Proveedor, Telefono, Email};
            Insertar(Sql, object);
            Sql = "INSERT INTO ReporteProveedores (IDProveedor,SaldoActual,FechaActual,UltimoPago,FechaPago)"
                    + "VALUES(?,?,?,?,?)";
            proveedor = Proveedores();
            int pos = proveedor.size();
            pos--;
            int idProveedor = proveedor.get(pos).getIDProveedor();
            object = new Object[]{idProveedor, "$0.00", "Sin Fecha", "$0.00", "Sin Fecha"};
            Insertar(Sql, object);
        }
        return proveedor1;
    }

    public List<ClsProveedores> Proveedores() {
        try {
            proveedor = (List<ClsProveedores>) QR.query(getConn(), "SELECT * FROM proveedores",
                    new BeanListHandler(ClsProveedores.class));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex);
        }
        return proveedor;
    }

    public List<ClsProveedores> getProveedores() {
        return Proveedores();
    }

    public void BuscarProveedores(JTable table, String campo, int numRegistro, int RegPorPagina) {
        String[] registros = new String[4];
        String[] titulos = {"ID", "Proveedor", "Email", "Telefono"};
        modelo = new DefaultTableModel(null, titulos);
        proveedor = Proveedores();
        if (campo.equals("")) {
            proveedorFilter = proveedor.stream()
                    .skip(numRegistro).limit(RegPorPagina)
                    .collect(Collectors.toList());
        } else {
            proveedorFilter = proveedor.stream()
                    .filter(P -> P.getProveedor().startsWith(campo) || P.getEmail().startsWith(campo)
                    || P.getTelefono().startsWith(campo))
                    .skip(numRegistro).limit(RegPorPagina)
                    .collect(Collectors.toList());

        }
        proveedorFilter.forEach(item -> {
            registros[0] = String.valueOf(item.getIDProveedor());
            registros[1] = item.getProveedor();
            registros[2] = item.getEmail();
            registros[3] = item.getTelefono();
            modelo.addRow(registros);
        });
        table.setModel(modelo);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public DefaultTableModel reportesProveedor(JTable table, int idProveedor) {
        String[] registros = new String[6];
        String[] titulos = {"ID", "Proveedor", "Saldo Actual", "Fecha Actual", "Ultimo Pago", "Fecha Pago"};
        modelo2 = new DefaultTableModel(null, titulos);
        List<ClsReporteProveedores> reportes = reportesProveedor(idProveedor);
        reportes.forEach(item -> {
            registros[0] = String.valueOf(item.getIDRegistro());
            registros[1] = item.getProveedor();
            registros[2] = item.getSaldoActual();
            registros[3] = item.getFechaActual();
            registros[4] = item.getUltimoPago();
            registros[5] = item.getFechaPago();
            modelo2.addRow(registros);

        });
        table.setModel(modelo2);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        return modelo2;
    }

    public List<ClsProveedores> actualizarProveedor(int idProveedor, String Proveedor, String Email, String Telefono) {
        proveedor1 = Proveedores().stream()
                .filter(P -> P.getTelefono().equals(Telefono))
                .collect(Collectors.toList());
        proveedor2 = Proveedores().stream()
                .filter(P -> P.getEmail().equals(Email))
                .collect(Collectors.toList());
        List<ClsProveedores> listFinal = new ArrayList<ClsProveedores>();
        listFinal.addAll(proveedor1);
        listFinal.addAll(proveedor2);
        if (2 == listFinal.size()) {
            if (idProveedor == proveedor1.get(0).getIDProveedor()
                    && idProveedor == proveedor2.get(0).getIDProveedor()) {
                Actualizar(idProveedor, Proveedor, Email, Telefono);
                listFinal.clear();

            }

        } else {
            if (0 == listFinal.size()) {
                Actualizar(idProveedor, Proveedor, Email, Telefono);
                listFinal.clear();
            } else {
                if (0 != proveedor1.size()) {
                    if (idProveedor == proveedor1.get(0).getIDProveedor()) {
                        listFinal.clear();

                    }
                }
                if (0 != proveedor2.size()) {
                    if (idProveedor == proveedor1.get(0).getIDProveedor()) {
                        listFinal.clear();

                    }
                }
            }
        }
        return listFinal;
    }

    private void Actualizar(int idProveedor, String Proveedor, String Email, String Telefono) {
        Sql = "UPDATE proveedores SET Proveedor = ?, Telefono = ?,"
                + "Email = ? WHERE IdProveedor =" + idProveedor;
        Object[] proveedor = new Object[]{Proveedor, Telefono, Email};
        Actualizar(Sql, proveedor);
    }

    public void eliminarProveedor(int idProveedor, int idRegsitro) {
        Sql = "DELETE FROM reporteproveedores WHERE IDRegistro LIKE ?";
        Eliminar(Sql, idRegsitro);
        Sql = "DELETE FROM proveedores WHERE IDProveedor LIKE ?";
        Eliminar(Sql, idProveedor);
    }

    public void actualizarReportePro(String saldoActual, String fecha, String pago, int idPoveedor) {
        List<ClsReporteProveedores> reportes = reportesProveedor(idPoveedor);
        int idRegistro = reportes.get(0).getIDRegistro();
        Sql = "UPDATE reporteproveedores SET IdProveedor = ?, SaldoActual = ?, FechaActual = ?"
                + ", UltimoPago =?, FechaPago = ? WHERE IdRegistro =" + idRegistro;
        Object[] reporte = new Object[]{idPoveedor, "$" + saldoActual, fecha, "$" + pago, fecha};
        Actualizar(Sql, reporte);
    }

    public int print(Graphics graphics, PageFormat PageFormat, int pageIndex) {
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(PageFormat.getImageableX(), PageFormat.getImageableY());
            this.panel.printAll(graphics);
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }

    }

    public void ImprimirRecibo(JPanel panel) {
        this.panel = panel;
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }
}
