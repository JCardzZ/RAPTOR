package ModeloClases;

import Conexion.Consulta;
import Modelo.ClsClientes;
import Modelo.ClsReporteClientes;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cliente extends Consulta implements Printable {

    private Consulta consulta = new Consulta();
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;
    private List<ClsClientes> cliente, clienteFilter;
    private int IDCliente;
    private String Id;
    private String Sql;
    private Object[] object;
    private JPanel panel;

    public boolean InsertarCliente(String ID, String Nombre, String Apellido, String Direccion, String Telefono) {
        boolean valor = false;
        cliente = clientes();
        clienteFilter = cliente.stream()
                .filter(c -> c.getID().equals(ID))
                .collect(Collectors.toList());
        if (0 == clienteFilter.size()) {
            Sql = "Insert Into Clientes(ID,Nombres,Apellidos,Direccion,Telefono)" + "Values(?,?,?,?,?)";
            object = new Object[]{ID, Nombre, Apellido, Direccion, Telefono};
            Insertar(Sql, object);
            cliente = clientes();
            cliente.forEach(item -> {
                IDCliente = item.getIdCliente();
                Id = item.getID();
            });
            Sql = "Insert Into reporteClientes (IDCliente,SaldoActual,FechaActual,"
                    + "UltimoPago,FechaPago,ID)"
                    + "Values(?,?,?,?,?,?)";
            object = new Object[]{IDCliente, "$0.00", "Sin Fecha", "$0.00", "Sin Fecha", Id};
            Insertar(Sql, object);
            valor = true;
        }
        return valor;

    }

    public List<ClsClientes> getClientes() {
        return clientes();
    }

    public void BuscarCliente(JTable table, String campo, int num_registro, int reg_por_pagina) {
        String[] registros = new String[6];
        String[] titulos = {"Id", "ID", "Nombre", "Apellido", "Direccion", "Telefono"};
        modelo = new DefaultTableModel(null, titulos);
        cliente = clientes();
        if (campo.equals("")) {
            clienteFilter = cliente.stream()
                    .skip(num_registro).limit(reg_por_pagina)
                    .collect(Collectors.toList());
        } else {
            clienteFilter = cliente.stream()
                    .filter(C -> C.getID().startsWith(campo) || C.getNombres().startsWith(campo)
                    || C.getApellidos().startsWith(campo))
                    .skip(num_registro).limit(reg_por_pagina)
                    .collect(Collectors.toList());
        }
        clienteFilter.forEach(item -> {
            registros[0] = String.valueOf(item.getIdCliente());
            registros[1] = item.getID();
            registros[2] = item.getNombres();
            registros[3] = item.getApellidos();
            registros[4] = item.getDireccion();
            registros[5] = item.getTelefono();
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

    public DefaultTableModel reportesCliente(JTable table, int IDCliente) {
        String[] registros = new String[7];
        String[] titulos = {"ID", "Nombre", "Apellido", "Saldo Actual", "Fecha Actual", "Ultimo Pago", "Fecha Pago"};
        modelo2 = new DefaultTableModel(null, titulos);
        List<ClsReporteClientes> reportes = reportesClientes(IDCliente);
        reportes.forEach(item -> {
            registros[0] = String.valueOf(item.getIDRegistro());
            registros[1] = item.getNombres();
            registros[2] = item.getApellidos();
            registros[3] = item.getSaldoActual();
            registros[4] = item.getFechaActual();
            registros[5] = item.getUltimoPago();
            registros[6] = item.getFechaPago();
            modelo2.addRow(registros);

        });
        table.setModel(modelo2);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        return modelo2;
    }

    public boolean ActualizarClientes(String ID, String Nombre, String Apellido,
            String Direccion, String Telefono, int IDCliente) {
        boolean valor = false;
        cliente = clientes();
        clienteFilter = cliente.stream()
                .filter(c -> c.getID().equals(ID))
                .collect(Collectors.toList());
        int count = clienteFilter.size();
        clienteFilter = cliente.stream()
                .filter(c -> c.getIdCliente() == IDCliente)
                .collect(Collectors.toList());
        if (0 == count || ID.equals(clienteFilter.get(0).getID())) {

            Sql = "UPDATE Clientes SET ID = ?, Nombres = ?, Apellidos = ?,"
                    + " Direccion = ?, Telefono = ? WHERE IDCliente =" + IDCliente;
            Object[] cliente = new Object[]{ID, Nombre, Apellido, Direccion, Telefono};
            Actualizar(Sql, cliente);
            List<ClsReporteClientes> reportes = reportesClientes(IDCliente);
            int idRegistro = reportes.get(0).getIDRegistro();
            int IdCliente = reportes.get(0).getIDCliente();
            String SaldoActual = reportes.get(0).getSaldoActual();
            String FechaActual = reportes.get(0).getFechaActual();
            String UltimoPago = reportes.get(0).getUltimoPago();
            String FechaPago = reportes.get(0).getFechaPago();
            String Id = ID;
            Sql = "UPDATE reporteClientes Set IDCliente = ?, SaldoActual = ?, FechaActual = ?, "
                    + ", UltimoPago = ?, FechaPago = ?, ID = ? WHERE IDRegistro =" + idRegistro;
            Object[] reporte = new Object[]{IdCliente, SaldoActual, FechaActual, UltimoPago, FechaPago, ID};
            Actualizar(Sql, reporte);
            valor = true;

        }
        return valor;
    }

    public void EliminarCliente(int IdCliente, int idRegistro) {
        Sql = "DELETE FROM ReporteClientes WHERE IDRegistro LIKE ?";
        Eliminar(Sql, idRegistro);
        Sql = "DELETE FROM Clientes WHERE IDCliente LIKE ?";
        Eliminar(Sql, IdCliente);
    }

    public void ActualizarReportes(String SaldoActual, String Fecha, String Pago, int idCliente) {
        List<ClsReporteClientes> reportes = reportesClientes(idCliente);
        int idRegistro = reportes.get(0).getIDRegistro();
        int IDCliente = reportes.get(0).getIDCliente();
        String ID = reportes.get(0).getID();
        Sql = "UPDATE ReporteClientes SET IDCliente = ?, SaldoActual = ?, FechaActual = ?"
                + ", UltimoPago = ?, FechaPago = ?, ID = ? WHERE IDRegistro =" + idRegistro;
        Object[] reporte = new Object[]{idCliente, "$" + SaldoActual, Fecha, "$" + Pago, Fecha, ID};
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
