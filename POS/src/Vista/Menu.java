package Vista;

import Conexion.Conexion;
import Modelo.ClsCajas;
import Modelo.ClsClientes;
import Modelo.ClsFormatoDecimal;
import Modelo.ClsProveedores;
import Modelo.ClsUsuarios;
import ModeloClases.Caja;
import ModeloClases.Calendario;
import ModeloClases.Cliente;
import ModeloClases.Departamento;
import ModeloClases.Eventos;
import ModeloClases.Proveedor;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Menu extends javax.swing.JFrame {

    private Caja caja = new Caja();
    private Eventos evento = new Eventos();
    private Cliente cliente = new Cliente();
    private Proveedor proveedor = new Proveedor();
    private Departamento departamento = new Departamento();
    private ClsFormatoDecimal formato = new ClsFormatoDecimal();
    private List<ClsClientes> NumeroClientes;
    private DefaultTableModel tablaModelPD,  tablaModeloCLT, tablaModelReporteCliente,tablaModelReportPd;
    private DefaultTableModel tablaModelDpt;
    private List<ClsClientes> numCliente;
    private List<ClsProveedores> numProveedores, dataProveedor;
    private String accion = "Insert", FuncionCadaDepartamento, IDCategoria, Departamento, pago, DeudaActual, rol;
    private int PaginaSize = 100, Tab, MaxReg, PaginaCount, CurrentPage, NumeroPag, idProveedor;
    private int StarRec, EndRec, NumRegistro = 0, NumPagi = 0, idCliente, idRegistro, idDpto = 0;
    Pattern pattern;
    Matcher matcher;
    private List<ClsUsuarios> listUsuario;
    private List<ClsCajas> listCajas;

    public Menu(List<ClsUsuarios> listUsuario, List<ClsCajas> listCajas) {
        initComponents();
//        cerrar();
//        txtPagosClientes.setEnabled(true);
        if (null != listUsuario) {
            rol = listUsuario.get(0).getRole();;
            if (!rol.equalsIgnoreCase("Admin")) {
                RadioPagosProveedor.setEnabled(false);
            }
            if (rol.equals("Admin")) {
                lblUsuario.setText(listUsuario.get(0).getNombre());
                lblCaja.setText("0");
                this.listUsuario = listUsuario;
            } else {
                lblUsuario.setText(listUsuario.get(0).getNombre());
                lblCaja.setText(String.valueOf(listCajas.get(0).getCaja()));
                this.listUsuario = listUsuario;
                this.listCajas = listCajas;
            }

        }

        txtCategoria.setEnabled(false);
        RadioDepartamento.setSelected(true);
        RadioDepartamento.setForeground(new Color(0, 153, 51));
        departamento.buscarDepartamento(jtDepartamentos, "");

        OcultarMensajesValidaciones();
        new Conexion();
        ImageIcon imagen = new ImageIcon("C:\\Users\\J Cardoza\\Documents\\NetBeansProjects\\POS\\src\\Iconos\\WhatsApp Image 2019-07-29 at 9.37.26 PM.jpeg");
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT));
        lblLogo.setIcon(icono);
        this.repaint();

        CargarDatos();

        QuitarBordeBoton();

        RadioAgreagarCliente.setSelected(true);
        RadioAgreagarCliente.setForeground(new Color(51, 103, 214));
        txtPagosClientes.setEnabled(true);
        cliente.reportesCliente(TablaReporteClientes, idCliente);
        proveedor.reportesProveedor(TablaReportesProveedor, idProveedor);
        if (!rol.equalsIgnoreCase("Admin")) {
            RadioPagosCliente.setEnabled(false);
        }

        RadioIngresarProveedor.setSelected(true);
        RadioIngresarProveedor.setSelected(true);
        RadioIngresarProveedor.setForeground(new Color(44, 189, 165));
        txtPagosProveedor.setEnabled(false);

    }

    public void QuitarBordeBoton() {

        Button_PrimeroCLT2.setOpaque(false);
        Button_PrimeroCLT2.setContentAreaFilled(false);
        Button_PrimeroCLT2.setBorderPainted(false);
        Button_PrimeroCLT2.setOpaque(false);

        btnImprimirReciboProveedor.setOpaque(false);
        btnImprimirReciboProveedor.setContentAreaFilled(false);
        btnImprimirReciboProveedor.setBorderPainted(false);
        btnImprimirReciboProveedor.setOpaque(false);

    }

    public void cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    confirmarSalida();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void confirmarSalida() {
        int valor = JOptionPane.showConfirmDialog(this, "¿Estas Seguro de Salir?", "Mensaje del Sistema", JOptionPane.YES_NO_OPTION);
        if (valor == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, " </> SISTEMA DE VENTAS </> ", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        }
    }

    private void CargarDatos() {
        switch (Tab) {
            case 1:
                NumeroClientes = cliente.getClientes();
                cliente.BuscarCliente(TablaClientes, "", NumRegistro, PaginaSize);
                MaxReg = NumeroClientes.size();
                break;
            case 2:
                numProveedores = proveedor.getProveedores();
                proveedor.BuscarProveedores(TablaProveedores, "", NumRegistro, PaginaSize);
                MaxReg = numProveedores.size();
                break;

        }
        PaginaCount = (MaxReg / PaginaSize);
        if ((MaxReg % PaginaSize) > 0) {
            PaginaCount += 1;
        }
        switch (Tab) {
            case 1:
                lblPaginaClientes.setText("Paginas " + "1" + "/ " + String.valueOf(PaginaCount));
                break;
            case 2:
                lblPaginaProveedores.setText("Paginas " + "1" + "/ " + String.valueOf(PaginaCount));
                break;
        }
    }

    public void OcultarMensajesValidaciones() {
        lblImagenIDClientes.setVisible(false);
        lblImagenNombresClientes.setVisible(false);
        lblImagenApellidosClientes.setVisible(false);
        lblImagenDireccionClientes.setVisible(false);
        lblImagenTelefonoClientes.setVisible(false);
        lblImagenTel.setVisible(false);
        mensajeTelefonoProveedor.setVisible(false);
        lblImagenProveedor.setVisible(false);
        lblImagenEmail.setVisible(false);

        txtCategoria.setEnabled(false);
        RadioDepartamento.setSelected(true);
        RadioDepartamento.setForeground(new Color(0, 153, 51));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        Table_Clientes1 =   TablaClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        Button_PrimeroCLT2 = new javax.swing.JButton();
        btnPrimeroVentas = new rojeru_san.RSButtonRiple();
        btnSiguienteVentas = new rojeru_san.RSButtonRiple();
        btnUltimoVentas = new rojeru_san.RSButtonRiple();
        btnAnteriorVentas = new rojeru_san.RSButtonRiple();
        jPanel25 = new javax.swing.JPanel();
        Label_NombreCliente1 = new javax.swing.JLabel();
        Label_ApellidoCliente1 = new javax.swing.JLabel();
        Label_DireccionCliente1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        Label_ApellidoCliente2 = new javax.swing.JLabel();
        Label_DireccionCliente2 = new javax.swing.JLabel();
        Label_ApellidoCliente3 = new javax.swing.JLabel();
        Label_DireccionCliente3 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        rSMTextFull6 = new rojeru_san.RSMTextFull();
        rSButtonRiple10 = new rojeru_san.RSButtonRiple();
        rSButtonRiple11 = new rojeru_san.RSButtonRiple();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        Table_ReportesCLT1 = TablaReporteClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        rSMTextFull8 = new rojeru_san.RSMTextFull();
        jPanel27 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        rSMTextFull7 = new rojeru_san.RSMTextFull();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblPagoClientes = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        RadioButton_IngresarCliente = new javax.swing.JRadioButton();
        RadioButton_PagosCliente = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblNombreClientes = new javax.swing.JLabel();
        lblApellidosClientes = new javax.swing.JLabel();
        lblDireccionClientes = new javax.swing.JLabel();
        lblTelefonoClientes = new javax.swing.JLabel();
        txtNombreCliente = new rojeru_san.RSMTextFull();
        txtApellidosClientes = new rojeru_san.RSMTextFull();
        txtDireccionCliente = new rojeru_san.RSMTextFull();
        txtPagosClientes = new rojeru_san.RSMTextFull();
        btnCancelarClientes = new rojeru_san.RSButtonRiple();
        btnGuardarClientes = new rojeru_san.RSButtonRiple();
        btnEliminarClientes = new rojeru_san.RSButtonRiple();
        RadioPagosCliente = new javax.swing.JRadioButton();
        RadioAgreagarCliente = new javax.swing.JRadioButton();
        lblIDCliente = new javax.swing.JLabel();
        txtidCliente = new rojeru_san.RSMTextFull();
        jLabel52 = new javax.swing.JLabel();
        txtTelefonoCliente = new rojeru_san.RSMTextFull();
        lblImagenTelefonoClientes = new javax.swing.JLabel();
        lblImagenIDClientes = new javax.swing.JLabel();
        lblImagenNombresClientes = new javax.swing.JLabel();
        lblImagenApellidosClientes = new javax.swing.JLabel();
        lblImagenDireccionClientes = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaClientes =   TablaClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnPrimeroClientes = new rojeru_san.RSButtonRiple();
        btnAneriorClientes = new rojeru_san.RSButtonRiple();
        btnSiguienteClientes = new rojeru_san.RSButtonRiple();
        btnUltimoClientes = new rojeru_san.RSButtonRiple();
        lblPaginaClientes = new javax.swing.JLabel();
        btnImprimmirClientes = new rojeru_san.RSButtonRiple();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaReporteClientes = TablaReporteClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtBuscarClientes = new rojeru_san.RSMTextFull();
        jLabel46 = new javax.swing.JLabel();
        Panel_ReciboCliente = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        Label_SaldoActualCLT = new javax.swing.JLabel();
        Label_NombreCLT = new javax.swing.JLabel();
        Label_ApellidoCLT = new javax.swing.JLabel();
        Label_UltimoPagoCLT = new javax.swing.JLabel();
        Label_FechaPagoCLT = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnGuardarProveedor = new rojeru_san.RSButtonRiple();
        btnEliminarProveedor = new rojeru_san.RSButtonRiple();
        btnCancelarProveedor = new rojeru_san.RSButtonRiple();
        jPanel39 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        RadioButton_IngresarCliente2 = new javax.swing.JRadioButton();
        RadioButton_PagosCliente2 = new javax.swing.JRadioButton();
        lblNombreProveedor = new javax.swing.JLabel();
        txtNombreProveedor = new rojeru_san.RSMTextFull();
        txtEmailProveedor = new rojeru_san.RSMTextFull();
        lblEmailProveedor = new javax.swing.JLabel();
        txtNumeroTelefonoProveedor = new rojeru_san.RSMTextFull();
        lblTelefonoProveedor = new javax.swing.JLabel();
        lblPagosProveedor = new javax.swing.JLabel();
        txtPagosProveedor = new rojeru_san.RSMTextFull();
        jLabel57 = new javax.swing.JLabel();
        lblImagenProveedor = new javax.swing.JLabel();
        lblImagenEmail = new javax.swing.JLabel();
        lblImagenTel = new javax.swing.JLabel();
        RadioPagosProveedor = new javax.swing.JRadioButton();
        RadioIngresarProveedor = new javax.swing.JRadioButton();
        mensajeTelefonoProveedor = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TablaProveedores =   Table_ComprasPDT = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblPaginaProveedores = new javax.swing.JLabel();
        btnPrimeroProveedor = new rojeru_san.RSButtonRiple();
        btnAnteriorProveedor = new rojeru_san.RSButtonRiple();
        btnSigueinteProveedor = new rojeru_san.RSButtonRiple();
        btnUltmimoProveedor = new rojeru_san.RSButtonRiple();
        btnImprimirReciboProveedor = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        TablaReportesProveedor = Table_BodegaPDT = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        PanelReciboProveedor = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        nada = new javax.swing.JLabel();
        na = new javax.swing.JLabel();
        ok = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        lblSaldoActualPro = new javax.swing.JLabel();
        lblNombrePro = new javax.swing.JLabel();
        lblUltimoPagoPro = new javax.swing.JLabel();
        lblFechaPro = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        txtbuscarProveedor = new rojeru_san.RSMTextFull();
        jLabel55 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        Label_CodigoPDT = new javax.swing.JLabel();
        TextField_CodigoPDT = new javax.swing.JTextField();
        Label_DescripcionPDT = new javax.swing.JLabel();
        TextField_DescripcionPDT = new javax.swing.JTextField();
        TextField_PrecioUnidadPDT = new javax.swing.JTextField();
        TextField_PrecioMayoreoPDT = new javax.swing.JTextField();
        ComboBox_Departamento = new javax.swing.JComboBox();
        ComboBox_Categoria = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Label_DescripcionPDT1 = new javax.swing.JLabel();
        Label_DescripcionPDT2 = new javax.swing.JLabel();
        Label_DescripcionPDT3 = new javax.swing.JLabel();
        Label_DescripcionPDT4 = new javax.swing.JLabel();
        rSButtonRiple27 = new rojeru_san.RSButtonRiple();
        rSButtonRiple28 = new rojeru_san.RSButtonRiple();
        rSButtonRiple29 = new rojeru_san.RSButtonRiple();
        jPanel34 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        RadioButton_IngresarCliente1 = new javax.swing.JRadioButton();
        RadioButton_PagosCliente1 = new javax.swing.JRadioButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table_ComprasPDT =   Table_ComprasPDT = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel49 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Table_BodegaPDT = Table_BodegaPDT = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        Label = new javax.swing.JLabel();
        rSMTextFull10 = new rojeru_san.RSMTextFull();
        jLabel50 = new javax.swing.JLabel();
        rSButtonRiple20 = new rojeru_san.RSButtonRiple();
        rSButtonRiple21 = new rojeru_san.RSButtonRiple();
        rSButtonRiple22 = new rojeru_san.RSButtonRiple();
        rSButtonRiple23 = new rojeru_san.RSButtonRiple();
        jPanel33 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        RadioDepartamento = new javax.swing.JRadioButton();
        jPanel22 = new javax.swing.JPanel();
        txtCategoria = new javax.swing.JTextField();
        lblCategoria = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        lblDepartamento = new javax.swing.JLabel();
        txtDepartamento = new javax.swing.JTextField();
        RadioCategoria = new javax.swing.JRadioButton();
        btnGuardarDepartamento = new rojeru_san.RSButtonRiple();
        rSButtonRiple31 = new rojeru_san.RSButtonRiple();
        rSButtonRiple32 = new rojeru_san.RSButtonRiple();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtDepartamentos =   jtDepartamentos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        txtbuscarDepartamento = new rojeru_san.RSMTextFull();
        jLabel54 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        Table_Cat =   Table_Cat = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel35 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        RadioButton_Unidad = new javax.swing.JRadioButton();
        RadioButton_Mayoreo = new javax.swing.JRadioButton();
        Label_DescripcionCP = new javax.swing.JLabel();
        TextField_DescripcionCP = new javax.swing.JTextField();
        rSButtonRiple24 = new rojeru_san.RSButtonRiple();
        rSButtonRiple25 = new rojeru_san.RSButtonRiple();
        rSButtonRiple26 = new rojeru_san.RSButtonRiple();
        jPanel20 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Label_DescripcionCP1 = new javax.swing.JLabel();
        Label_DescripcionCP2 = new javax.swing.JLabel();
        TextField_DescripcionCP1 = new javax.swing.JTextField();
        Label_DescripcionCP3 = new javax.swing.JLabel();
        TextField_DescripcionCP2 = new javax.swing.JTextField();
        Label_DescripcionCP4 = new javax.swing.JLabel();
        TextField_DescripcionCP3 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Table_Compras =   Table_Compras = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        rSButtonRiple37 = new rojeru_san.RSButtonRiple();
        rSButtonRiple38 = new rojeru_san.RSButtonRiple();
        rSButtonRiple39 = new rojeru_san.RSButtonRiple();
        rSButtonRiple40 = new rojeru_san.RSButtonRiple();
        jPanel19 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        TextField_BuscarCompras = new javax.swing.JTextField();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtIdProducto = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtExistencia = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnGuardar = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnClientes = new rojeru_san.RSButtonRiple();
        btnVentas = new rojeru_san.RSButtonRiple();
        btnProductos = new rojeru_san.RSButtonRiple();
        btnConfiguracion = new rojeru_san.RSButtonRiple();
        btnCategoriasok = new rojeru_san.RSButtonRiple();
        btnProveedor = new rojeru_san.RSButtonRiple();
        btnCompras1 = new rojeru_san.RSButtonRiple();
        jPanel43 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblCaja = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_Clientes1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Table_Clientes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_Clientes1.setRowHeight(20);
        Table_Clientes1.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane7.setViewportView(Table_Clientes1);

        Button_PrimeroCLT2.setBackground(new java.awt.Color(204, 204, 204));
        Button_PrimeroCLT2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Button_PrimeroCLT2.setForeground(new java.awt.Color(255, 255, 255));
        Button_PrimeroCLT2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_BT_printer_905556.png"))); // NOI18N
        Button_PrimeroCLT2.setText("Factura");

        btnPrimeroVentas.setText("Primero");
        btnPrimeroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroVentasActionPerformed(evt);
            }
        });

        btnSiguienteVentas.setText("Siguiente");
        btnSiguienteVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteVentasActionPerformed(evt);
            }
        });

        btnUltimoVentas.setText("Ultimo");
        btnUltimoVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoVentasActionPerformed(evt);
            }
        });

        btnAnteriorVentas.setText("Anterior");
        btnAnteriorVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(btnPrimeroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnteriorVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSiguienteVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUltimoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Button_PrimeroCLT2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane7)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_PrimeroCLT2)
                    .addComponent(btnPrimeroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSiguienteVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUltimoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnteriorVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Label_NombreCliente1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_NombreCliente1.setForeground(new java.awt.Color(236, 82, 82));
        Label_NombreCliente1.setText("Pagó con");
        Label_NombreCliente1.setToolTipText("");
        jPanel25.add(Label_NombreCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        Label_ApellidoCliente1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_ApellidoCliente1.setForeground(new java.awt.Color(236, 82, 82));
        Label_ApellidoCliente1.setText("Total a Pagar");
        jPanel25.add(Label_ApellidoCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        Label_DireccionCliente1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        Label_DireccionCliente1.setText("0.00");
        Label_DireccionCliente1.setToolTipText("");
        jPanel25.add(Label_DireccionCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 110, -1));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 204, 0));
        jCheckBox1.setText("Credito");
        jPanel25.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));

        Label_ApellidoCliente2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_ApellidoCliente2.setForeground(new java.awt.Color(236, 82, 82));
        Label_ApellidoCliente2.setText(" Total");
        jPanel25.add(Label_ApellidoCliente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, -1, -1));

        Label_DireccionCliente2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        Label_DireccionCliente2.setText("0.00");
        Label_DireccionCliente2.setToolTipText("");
        jPanel25.add(Label_DireccionCliente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 120, -1));

        Label_ApellidoCliente3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_ApellidoCliente3.setForeground(new java.awt.Color(236, 82, 82));
        Label_ApellidoCliente3.setText("Vuelto");
        jPanel25.add(Label_ApellidoCliente3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        Label_DireccionCliente3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        Label_DireccionCliente3.setText("0.00");
        Label_DireccionCliente3.setToolTipText("");
        jPanel25.add(Label_DireccionCliente3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 110, -1));

        jLabel24.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 204, 0));
        jLabel24.setText("$");
        jPanel25.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 30, -1));

        jLabel38.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 204, 0));
        jLabel38.setText("$");
        jPanel25.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 30, -1));

        jLabel39.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 204, 0));
        jLabel39.setText("$");
        jPanel25.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 30, -1));

        jPanel17.setBackground(new java.awt.Color(44, 189, 165));

        jLabel16.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Detalles de Venta");
        jLabel16.setToolTipText("");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel16)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addContainerGap())
        );

        jPanel25.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 50));

        jLabel44.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 204, 0));
        jLabel44.setText("$");
        jPanel25.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 30, -1));

        rSMTextFull6.setForeground(new java.awt.Color(204, 51, 0));
        rSMTextFull6.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull6.setBotonColor(new java.awt.Color(255, 0, 102));
        rSMTextFull6.setPlaceholder("");
        jPanel25.add(rSMTextFull6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 140, -1));

        rSButtonRiple10.setBackground(new java.awt.Color(236, 82, 82));
        rSButtonRiple10.setText("Cancelar (Ctrl + X)");
        rSButtonRiple10.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel25.add(rSButtonRiple10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 290, 70));

        rSButtonRiple11.setBackground(new java.awt.Color(0, 204, 0));
        rSButtonRiple11.setText("Cobrar (Ctrl + Space)");
        rSButtonRiple11.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel25.add(rSButtonRiple11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 290, 70));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_ReportesCLT1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_ReportesCLT1.setRowHeight(20);
        Table_ReportesCLT1.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane10.setViewportView(Table_ReportesCLT1);

        jPanel26.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 57, 1318, 70));
        jPanel26.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 15, -1, 35));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_search_326690 (1).png"))); // NOI18N
        jPanel26.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(976, 15, 44, 35));

        rSMTextFull8.setForeground(new java.awt.Color(0, 0, 0));
        rSMTextFull8.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull8.setBordeColorNoFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull8.setBotonColor(new java.awt.Color(255, 51, 0));
        rSMTextFull8.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rSMTextFull8.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        rSMTextFull8.setPlaceholder("Busqueda de CLIENTE");
        jPanel26.add(rSMTextFull8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 530, -1));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel17.setText("Detalles de Venta");

        jLabel26.setText("Nombre:");

        jLabel27.setText("Deuda:");

        jLabel28.setText("Deuda anterior:");

        jLabel29.setText("Deuda actual:");

        jLabel30.setText("Ultimo pago:");

        jLabel31.setText("Fecha:");

        jLabel32.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel32.setText("0.00");

        jLabel33.setText("Nombre");

        jLabel34.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel34.setText("0.00");

        jLabel35.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel35.setText("0.00");

        jLabel36.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel36.setText("0.00");

        jLabel37.setText("Fecha");

        jLabel40.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 204, 0));
        jLabel40.setText("$");

        jLabel41.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 204, 0));
        jLabel41.setText("$");

        jLabel42.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 204, 0));
        jLabel42.setText("$");

        jLabel43.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 13)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 204, 0));
        jLabel43.setText("$");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel17))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel26)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31))
                        .addGap(1, 9, Short.MAX_VALUE)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel27Layout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addComponent(jLabel42))
                                        .addGroup(jPanel27Layout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(jLabel43))
                                        .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel37)))
                            .addComponent(jLabel33))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel34)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel32)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel35)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel36)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel37)))
        );

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_search_326690 (1).png"))); // NOI18N

        jPanel29.setBackground(new java.awt.Color(44, 189, 165));
        jPanel29.setForeground(new java.awt.Color(44, 189, 165));

        jLabel15.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Ventas");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(870, 870, 870))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        rSMTextFull7.setForeground(new java.awt.Color(0, 0, 0));
        rSMTextFull7.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull7.setBordeColorNoFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull7.setBotonColor(new java.awt.Color(255, 51, 0));
        rSMTextFull7.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rSMTextFull7.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        rSMTextFull7.setPlaceholder("Busqueda por TECLADO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(125, 125, 125))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(735, 735, 735)
                .addComponent(rSMTextFull7, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMTextFull7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ventas", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));

        lblPagoClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblPagoClientes.setText("Pagos de Deudas");
        lblPagoClientes.setToolTipText("");

        jPanel30.setBackground(new java.awt.Color(44, 189, 165));

        jLabel25.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Datos Clientes");
        jLabel25.setToolTipText("");

        RadioButton_IngresarCliente.setBackground(new java.awt.Color(44, 189, 165));
        RadioButton_IngresarCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioButton_IngresarCliente.setForeground(new java.awt.Color(255, 255, 255));
        RadioButton_IngresarCliente.setText("Ingresar cliente");

        RadioButton_PagosCliente.setBackground(new java.awt.Color(44, 189, 165));
        RadioButton_PagosCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioButton_PagosCliente.setForeground(new java.awt.Color(255, 255, 255));
        RadioButton_PagosCliente.setText("Pagos de cliente");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RadioButton_IngresarCliente)
                .addGap(18, 18, 18)
                .addComponent(RadioButton_PagosCliente)
                .addContainerGap())
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioButton_IngresarCliente)
                    .addComponent(RadioButton_PagosCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(236, 82, 82));

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ingresa la Información del Cliente*");
        jLabel3.setToolTipText("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(37, 37, 37))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        lblNombreClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblNombreClientes.setText("Nombres");
        lblNombreClientes.setToolTipText("");

        lblApellidosClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblApellidosClientes.setText("Apellidos");
        lblApellidosClientes.setToolTipText("");

        lblDireccionClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblDireccionClientes.setText("Direccion");
        lblDireccionClientes.setToolTipText("");

        lblTelefonoClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblTelefonoClientes.setText("Teléfono");
        lblTelefonoClientes.setToolTipText("");

        txtNombreCliente.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreCliente.setBordeColorFocus(new java.awt.Color(51, 51, 51));
        txtNombreCliente.setBotonColor(new java.awt.Color(255, 51, 0));
        txtNombreCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        txtNombreCliente.setPlaceholder("Escribe Nombre de Cliente");
        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        txtApellidosClientes.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidosClientes.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtApellidosClientes.setBordeColorNoFocus(new java.awt.Color(204, 204, 204));
        txtApellidosClientes.setBotonColor(new java.awt.Color(255, 51, 0));
        txtApellidosClientes.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtApellidosClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        txtApellidosClientes.setPlaceholder("Escribe Apellidos de Cliente");
        txtApellidosClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidosClientesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosClientesKeyTyped(evt);
            }
        });

        txtDireccionCliente.setForeground(new java.awt.Color(0, 0, 0));
        txtDireccionCliente.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtDireccionCliente.setBotonColor(new java.awt.Color(255, 51, 0));
        txtDireccionCliente.setPlaceholder("Escribe Direccion de Cliente");
        txtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyTyped(evt);
            }
        });

        txtPagosClientes.setForeground(new java.awt.Color(0, 0, 0));
        txtPagosClientes.setBordeColorFocus(new java.awt.Color(236, 82, 82));
        txtPagosClientes.setBordeColorNoFocus(new java.awt.Color(255, 255, 255));
        txtPagosClientes.setBotonColor(new java.awt.Color(255, 0, 0));
        txtPagosClientes.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtPagosClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        txtPagosClientes.setOpaque(false);
        txtPagosClientes.setPlaceholder("");
        txtPagosClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPagosClientesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagosClientesKeyTyped(evt);
            }
        });

        btnCancelarClientes.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelarClientes.setText("Cancelar");
        btnCancelarClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnCancelarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarClientesActionPerformed(evt);
            }
        });

        btnGuardarClientes.setBackground(new java.awt.Color(0, 119, 145));
        btnGuardarClientes.setText("Guardar (Enter)");
        btnGuardarClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnGuardarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClientesActionPerformed(evt);
            }
        });

        btnEliminarClientes.setBackground(new java.awt.Color(236, 82, 82));
        btnEliminarClientes.setText("Eliminar");
        btnEliminarClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnEliminarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClientesActionPerformed(evt);
            }
        });

        RadioPagosCliente.setBackground(new java.awt.Color(255, 255, 255));
        RadioPagosCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        RadioPagosCliente.setText("Pagos de Cliente");
        RadioPagosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPagosClienteActionPerformed(evt);
            }
        });

        RadioAgreagarCliente.setBackground(new java.awt.Color(255, 255, 255));
        RadioAgreagarCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        RadioAgreagarCliente.setText("Agregar Cliente");
        RadioAgreagarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioAgreagarClienteActionPerformed(evt);
            }
        });

        lblIDCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblIDCliente.setText("ID");
        lblIDCliente.setToolTipText("");
        lblIDCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblIDClienteKeyReleased(evt);
            }
        });

        txtidCliente.setForeground(new java.awt.Color(0, 0, 0));
        txtidCliente.setBordeColorFocus(new java.awt.Color(51, 51, 51));
        txtidCliente.setBotonColor(new java.awt.Color(255, 51, 0));
        txtidCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        txtidCliente.setPlaceholder("");
        txtidCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidClienteKeyTyped(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(236, 82, 82));
        jLabel52.setText("$");

        txtTelefonoCliente.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefonoCliente.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtTelefonoCliente.setBotonColor(new java.awt.Color(255, 0, 0));
        txtTelefonoCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        txtTelefonoCliente.setPlaceholder("Escribe Numero Tel/Cel");
        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });

        lblImagenTelefonoClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N

        lblImagenIDClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N

        lblImagenNombresClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N

        lblImagenApellidosClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N

        lblImagenDireccionClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(RadioAgreagarCliente)
                        .addGap(11, 11, 11)
                        .addComponent(RadioPagosCliente))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblIDCliente))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagenIDClientes))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreClientes)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagenNombresClientes))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApellidosClientes)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtApellidosClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagenApellidosClientes))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDireccionClientes)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagenDireccionClientes))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonoClientes)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagenTelefonoClientes))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblPagoClientes)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel52)
                        .addGap(0, 0, 0)
                        .addComponent(txtPagosClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnGuardarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(btnEliminarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnCancelarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioAgreagarCliente)
                    .addComponent(RadioPagosCliente))
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIDCliente)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagenIDClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreClientes)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagenNombresClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellidosClientes)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellidosClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagenApellidosClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccionClientes)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagenDireccionClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefonoClientes)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagenTelefonoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblPagoClientes))
                    .addComponent(jLabel52)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtPagosClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(btnGuardarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnEliminarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnCancelarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 73, 410, 740));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(null);

        TablaClientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaClientes.setRowHeight(20);
        TablaClientes.setSelectionBackground(new java.awt.Color(102, 204, 255));
        TablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaClientesMouseClicked(evt);
            }
        });
        TablaClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaClientesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(TablaClientes);

        jPanel8.add(jScrollPane2);
        jScrollPane2.setBounds(12, 15, 1444, 409);

        btnPrimeroClientes.setText("Primero");
        btnPrimeroClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroClientesActionPerformed(evt);
            }
        });
        jPanel8.add(btnPrimeroClientes);
        btnPrimeroClientes.setBounds(240, 440, 200, 30);

        btnAneriorClientes.setText("Anterior");
        btnAneriorClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAneriorClientesActionPerformed(evt);
            }
        });
        jPanel8.add(btnAneriorClientes);
        btnAneriorClientes.setBounds(440, 440, 200, 30);

        btnSiguienteClientes.setText("Siguiente");
        btnSiguienteClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteClientesActionPerformed(evt);
            }
        });
        jPanel8.add(btnSiguienteClientes);
        btnSiguienteClientes.setBounds(640, 440, 200, 30);

        btnUltimoClientes.setText("Ultimo");
        btnUltimoClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoClientesActionPerformed(evt);
            }
        });
        jPanel8.add(btnUltimoClientes);
        btnUltimoClientes.setBounds(840, 440, 200, 30);

        lblPaginaClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblPaginaClientes.setText("Registro");
        jPanel8.add(lblPaginaClientes);
        lblPaginaClientes.setBounds(20, 440, 150, 21);

        btnImprimmirClientes.setBackground(new java.awt.Color(0, 119, 145));
        btnImprimmirClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/paper-printer.png"))); // NOI18N
        btnImprimmirClientes.setText("Imprimir Detalle");
        btnImprimmirClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnImprimmirClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimmirClientesActionPerformed(evt);
            }
        });
        jPanel8.add(btnImprimmirClientes);
        btnImprimmirClientes.setBounds(1150, 440, 300, 40);

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 1470, 500));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TablaReporteClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaReporteClientes.setRowHeight(20);
        TablaReporteClientes.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane3.setViewportView(TablaReporteClientes);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("Reportes");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(850, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 607, 1210, -1));

        jPanel32.setBackground(new java.awt.Color(44, 189, 165));

        jLabel45.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Clientes");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(964, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addGap(870, 870, 870))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 1930, -1));

        txtBuscarClientes.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscarClientes.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtBuscarClientes.setBordeColorNoFocus(new java.awt.Color(0, 0, 0));
        txtBuscarClientes.setBotonColor(new java.awt.Color(255, 51, 0));
        txtBuscarClientes.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtBuscarClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtBuscarClientes.setPlaceholder("Busqueda CLIENTES");
        txtBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClientesActionPerformed(evt);
            }
        });
        txtBuscarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarClientesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClientesKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscarClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 500, 40));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_search_326690 (1).png"))); // NOI18N
        jPanel2.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 50, -1, -1));

        Panel_ReciboCliente.setBackground(new java.awt.Color(255, 255, 255));
        Panel_ReciboCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel77.setText("Recibo");

        jLabel78.setText("Nombre:");

        jLabel79.setText("Apellido:");

        jLabel80.setText("Deuda actual:");

        jLabel81.setText("Ultimo pago:");

        jLabel83.setText("Fecha:");

        Label_SaldoActualCLT.setText("$0.00");

        Label_NombreCLT.setText("Nombre");

        Label_ApellidoCLT.setText("Apellido");

        Label_UltimoPagoCLT.setText("$0.00");

        Label_FechaPagoCLT.setText("Fecha");

        javax.swing.GroupLayout Panel_ReciboClienteLayout = new javax.swing.GroupLayout(Panel_ReciboCliente);
        Panel_ReciboCliente.setLayout(Panel_ReciboClienteLayout);
        Panel_ReciboClienteLayout.setHorizontalGroup(
            Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ReciboClienteLayout.createSequentialGroup()
                .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_ReciboClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel78)
                            .addComponent(jLabel79)
                            .addComponent(jLabel80)
                            .addComponent(jLabel81)
                            .addComponent(jLabel83))
                        .addGap(15, 15, 15)
                        .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_NombreCLT)
                            .addComponent(Label_ApellidoCLT, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label_SaldoActualCLT)
                            .addComponent(Label_UltimoPagoCLT)
                            .addComponent(Label_FechaPagoCLT)))
                    .addGroup(Panel_ReciboClienteLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel77)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        Panel_ReciboClienteLayout.setVerticalGroup(
            Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ReciboClienteLayout.createSequentialGroup()
                .addComponent(jLabel77)
                .addGap(18, 18, 18)
                .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(Label_NombreCLT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(Label_ApellidoCLT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(Label_SaldoActualCLT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(Label_UltimoPagoCLT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_ReciboClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83)
                    .addComponent(Label_FechaPagoCLT)))
        );

        jPanel2.add(Panel_ReciboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1650, 620, 250, -1));

        jTabbedPane1.addTab("Clientes", jPanel2);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel37.setPreferredSize(new java.awt.Dimension(310, 352));
        jPanel37.setLayout(null);

        jPanel38.setBackground(new java.awt.Color(236, 82, 82));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ingresa la Información del Proveedor *");
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel37.add(jPanel38);
        jPanel38.setBounds(0, 50, 410, 40);

        btnGuardarProveedor.setBackground(new java.awt.Color(0, 119, 145));
        btnGuardarProveedor.setText("Guardar (Enter)");
        btnGuardarProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });
        jPanel37.add(btnGuardarProveedor);
        btnGuardarProveedor.setBounds(60, 550, 290, 50);

        btnEliminarProveedor.setBackground(new java.awt.Color(236, 82, 82));
        btnEliminarProveedor.setText("Eliminar");
        btnEliminarProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanel37.add(btnEliminarProveedor);
        btnEliminarProveedor.setBounds(80, 610, 250, 50);

        btnCancelarProveedor.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelarProveedor.setText("Cancelar");
        btnCancelarProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnCancelarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProveedorActionPerformed(evt);
            }
        });
        jPanel37.add(btnCancelarProveedor);
        btnCancelarProveedor.setBounds(110, 670, 200, 40);

        jPanel39.setBackground(new java.awt.Color(44, 189, 165));

        jLabel53.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Información Proveedor");
        jLabel53.setToolTipText("");

        RadioButton_IngresarCliente2.setBackground(new java.awt.Color(44, 189, 165));
        RadioButton_IngresarCliente2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioButton_IngresarCliente2.setForeground(new java.awt.Color(255, 255, 255));
        RadioButton_IngresarCliente2.setText("Ingresar cliente");

        RadioButton_PagosCliente2.setBackground(new java.awt.Color(44, 189, 165));
        RadioButton_PagosCliente2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioButton_PagosCliente2.setForeground(new java.awt.Color(255, 255, 255));
        RadioButton_PagosCliente2.setText("Pagos de cliente");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RadioButton_IngresarCliente2)
                .addGap(18, 18, 18)
                .addComponent(RadioButton_PagosCliente2)
                .addContainerGap())
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioButton_IngresarCliente2)
                    .addComponent(RadioButton_PagosCliente2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel37.add(jPanel39);
        jPanel39.setBounds(0, 0, 410, 50);

        lblNombreProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblNombreProveedor.setText("Nombre de Proveedor");
        lblNombreProveedor.setToolTipText("");
        lblNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblNombreProveedorKeyReleased(evt);
            }
        });
        jPanel37.add(lblNombreProveedor);
        lblNombreProveedor.setBounds(40, 240, 210, 21);

        txtNombreProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreProveedor.setBordeColorFocus(new java.awt.Color(51, 51, 51));
        txtNombreProveedor.setPlaceholder("Escribe Nombre de Proveedor");
        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyReleased(evt);
            }
        });
        jPanel37.add(txtNombreProveedor);
        txtNombreProveedor.setBounds(40, 260, 280, 42);

        txtEmailProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtEmailProveedor.setBordeColorFocus(new java.awt.Color(51, 51, 51));
        txtEmailProveedor.setPlaceholder("Escribe Correo Electronico");
        txtEmailProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailProveedorKeyReleased(evt);
            }
        });
        jPanel37.add(txtEmailProveedor);
        txtEmailProveedor.setBounds(40, 340, 280, 42);

        lblEmailProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblEmailProveedor.setText("Email");
        lblEmailProveedor.setToolTipText("");
        lblEmailProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblEmailProveedorKeyReleased(evt);
            }
        });
        jPanel37.add(lblEmailProveedor);
        lblEmailProveedor.setBounds(40, 310, 290, 21);

        txtNumeroTelefonoProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtNumeroTelefonoProveedor.setBordeColorFocus(new java.awt.Color(51, 51, 51));
        txtNumeroTelefonoProveedor.setPlaceholder("Escribe Numero de Proveedor");
        txtNumeroTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumeroTelefonoProveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroTelefonoProveedorKeyTyped(evt);
            }
        });
        jPanel37.add(txtNumeroTelefonoProveedor);
        txtNumeroTelefonoProveedor.setBounds(40, 410, 280, 42);

        lblTelefonoProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblTelefonoProveedor.setText("N. Telefono");
        lblTelefonoProveedor.setToolTipText("");
        lblTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblTelefonoProveedorKeyReleased(evt);
            }
        });
        jPanel37.add(lblTelefonoProveedor);
        lblTelefonoProveedor.setBounds(40, 390, 320, 21);

        lblPagosProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblPagosProveedor.setText("Pagos de Deudas");
        lblPagosProveedor.setToolTipText("");
        lblPagosProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblPagosProveedorKeyReleased(evt);
            }
        });
        jPanel37.add(lblPagosProveedor);
        lblPagosProveedor.setBounds(20, 490, 170, 21);

        txtPagosProveedor.setForeground(new java.awt.Color(153, 153, 153));
        txtPagosProveedor.setBordeColorFocus(new java.awt.Color(236, 82, 82));
        txtPagosProveedor.setPlaceholder("");
        txtPagosProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPagosProveedorActionPerformed(evt);
            }
        });
        txtPagosProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPagosProveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagosProveedorKeyTyped(evt);
            }
        });
        jPanel37.add(txtPagosProveedor);
        txtPagosProveedor.setBounds(220, 490, 140, 42);

        jLabel57.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(236, 82, 82));
        jLabel57.setText("$");
        jPanel37.add(jLabel57);
        jLabel57.setBounds(190, 480, 30, 56);

        lblImagenProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        jPanel37.add(lblImagenProveedor);
        lblImagenProveedor.setBounds(330, 270, 40, 30);

        lblImagenEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        jPanel37.add(lblImagenEmail);
        lblImagenEmail.setBounds(330, 340, 40, 32);

        lblImagenTel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        jPanel37.add(lblImagenTel);
        lblImagenTel.setBounds(330, 420, 40, 30);

        RadioPagosProveedor.setBackground(new java.awt.Color(255, 255, 255));
        RadioPagosProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioPagosProveedor.setText("Pagos de Proveedor");
        RadioPagosProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPagosProveedorActionPerformed(evt);
            }
        });
        jPanel37.add(RadioPagosProveedor);
        RadioPagosProveedor.setBounds(60, 150, 270, 37);

        RadioIngresarProveedor.setBackground(new java.awt.Color(255, 255, 255));
        RadioIngresarProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioIngresarProveedor.setForeground(new java.awt.Color(44, 189, 165));
        RadioIngresarProveedor.setText("Ingresar Proveedor");
        RadioIngresarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioIngresarProveedorActionPerformed(evt);
            }
        });
        jPanel37.add(RadioIngresarProveedor);
        RadioIngresarProveedor.setBounds(70, 110, 290, 37);

        mensajeTelefonoProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        mensajeTelefonoProveedor.setForeground(new java.awt.Color(204, 0, 0));
        mensajeTelefonoProveedor.setText("Solo Puedes Escribir 8 Digitos ");
        jPanel37.add(mensajeTelefonoProveedor);
        mensajeTelefonoProveedor.setBounds(70, 460, 230, 16);

        jPanel36.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 73, 410, 740));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel40.setLayout(null);

        TablaProveedores.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaProveedores.setRowHeight(20);
        TablaProveedores.setSelectionBackground(new java.awt.Color(102, 204, 255));
        TablaProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProveedoresMouseClicked(evt);
            }
        });
        TablaProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaProveedoresKeyReleased(evt);
            }
        });
        jScrollPane11.setViewportView(TablaProveedores);

        jPanel40.add(jScrollPane11);
        jScrollPane11.setBounds(14, 19, 1410, 372);

        lblPaginaProveedores.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblPaginaProveedores.setText("Registro");
        jPanel40.add(lblPaginaProveedores);
        lblPaginaProveedores.setBounds(20, 440, 150, 21);

        btnPrimeroProveedor.setText("Primero");
        btnPrimeroProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroProveedorActionPerformed(evt);
            }
        });
        jPanel40.add(btnPrimeroProveedor);
        btnPrimeroProveedor.setBounds(330, 420, 213, 30);

        btnAnteriorProveedor.setText("Anterior");
        btnAnteriorProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorProveedorActionPerformed(evt);
            }
        });
        jPanel40.add(btnAnteriorProveedor);
        btnAnteriorProveedor.setBounds(540, 420, 211, 30);

        btnSigueinteProveedor.setText("Siguiente");
        btnSigueinteProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSigueinteProveedorActionPerformed(evt);
            }
        });
        jPanel40.add(btnSigueinteProveedor);
        btnSigueinteProveedor.setBounds(750, 420, 200, 30);

        btnUltmimoProveedor.setText("Ultimo");
        btnUltmimoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltmimoProveedorActionPerformed(evt);
            }
        });
        jPanel40.add(btnUltmimoProveedor);
        btnUltmimoProveedor.setBounds(950, 420, 210, 30);

        btnImprimirReciboProveedor.setBackground(new java.awt.Color(204, 204, 204));
        btnImprimirReciboProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        btnImprimirReciboProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimirReciboProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_BT_printer_905556.png"))); // NOI18N
        btnImprimirReciboProveedor.setText("Factura");
        btnImprimirReciboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirReciboProveedorActionPerformed(evt);
            }
        });
        jPanel40.add(btnImprimirReciboProveedor);
        btnImprimirReciboProveedor.setBounds(1280, 420, 137, 41);

        jPanel36.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 1440, 480));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TablaReportesProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaReportesProveedor.setRowHeight(20);
        TablaReportesProveedor.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane12.setViewportView(TablaReportesProveedor);

        PanelReciboProveedor.setBackground(new java.awt.Color(255, 255, 255));
        PanelReciboProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel82.setText("Recibo");

        nada.setText("Nombre:");

        na.setText("Deuda actual:");

        ok.setText("Ultimo pago:");

        jLabel88.setText("Fecha:");

        lblSaldoActualPro.setText("$0.00");

        lblNombrePro.setText("Nombre");

        lblUltimoPagoPro.setText("$0.00");

        lblFechaPro.setText("Fecha");

        javax.swing.GroupLayout PanelReciboProveedorLayout = new javax.swing.GroupLayout(PanelReciboProveedor);
        PanelReciboProveedor.setLayout(PanelReciboProveedorLayout);
        PanelReciboProveedorLayout.setHorizontalGroup(
            PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReciboProveedorLayout.createSequentialGroup()
                .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelReciboProveedorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nada)
                            .addComponent(na)
                            .addComponent(ok)
                            .addComponent(jLabel88))
                        .addGap(15, 15, 15)
                        .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombrePro)
                            .addComponent(lblSaldoActualPro)
                            .addComponent(lblUltimoPagoPro)
                            .addComponent(lblFechaPro)))
                    .addGroup(PanelReciboProveedorLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel82)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        PanelReciboProveedorLayout.setVerticalGroup(
            PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReciboProveedorLayout.createSequentialGroup()
                .addComponent(jLabel82)
                .addGap(18, 18, 18)
                .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nada)
                    .addComponent(lblNombrePro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(na)
                    .addComponent(lblSaldoActualPro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(lblUltimoPagoPro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelReciboProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(lblFechaPro))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PanelReciboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelReciboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel36.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 607, 1440, 200));

        jPanel42.setBackground(new java.awt.Color(44, 189, 165));

        jLabel56.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Proveedores");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap(900, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(870, 870, 870))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel36.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 1920, -1));

        txtbuscarProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtbuscarProveedor.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtbuscarProveedor.setBordeColorNoFocus(new java.awt.Color(0, 0, 0));
        txtbuscarProveedor.setBotonColor(new java.awt.Color(255, 51, 0));
        txtbuscarProveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtbuscarProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtbuscarProveedor.setPlaceholder("Busqueda de PROVEEDORES");
        txtbuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarProveedorActionPerformed(evt);
            }
        });
        txtbuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscarProveedorKeyPressed(evt);
            }
        });
        jPanel36.add(txtbuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, 390, 40));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_search_326690 (1).png"))); // NOI18N
        jPanel36.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 60, -1, -1));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1915, Short.MAX_VALUE)
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel31Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 1915, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 829, Short.MAX_VALUE)
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel31Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Proveedores", jPanel31);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel11.setPreferredSize(new java.awt.Dimension(310, 352));
        jPanel11.setLayout(null);

        Label_CodigoPDT.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        Label_CodigoPDT.setText("Código de barra");
        Label_CodigoPDT.setToolTipText("");
        jPanel11.add(Label_CodigoPDT);
        Label_CodigoPDT.setBounds(10, 120, 121, 16);
        Label_CodigoPDT.getAccessibleContext().setAccessibleName("Código de barraS");

        jPanel11.add(TextField_CodigoPDT);
        TextField_CodigoPDT.setBounds(140, 114, 251, 30);

        Label_DescripcionPDT.setBackground(new java.awt.Color(44, 189, 165));
        Label_DescripcionPDT.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        Label_DescripcionPDT.setForeground(new java.awt.Color(44, 189, 165));
        Label_DescripcionPDT.setText("Categoria");
        jPanel11.add(Label_DescripcionPDT);
        Label_DescripcionPDT.setBounds(150, 350, 170, 28);
        jPanel11.add(TextField_DescripcionPDT);
        TextField_DescripcionPDT.setBounds(140, 150, 250, 30);
        jPanel11.add(TextField_PrecioUnidadPDT);
        TextField_PrecioUnidadPDT.setBounds(140, 190, 250, 30);
        jPanel11.add(TextField_PrecioMayoreoPDT);
        TextField_PrecioMayoreoPDT.setBounds(140, 230, 250, 30);

        jPanel11.add(ComboBox_Departamento);
        ComboBox_Departamento.setBounds(10, 310, 380, 30);
        jPanel11.add(ComboBox_Categoria);
        ComboBox_Categoria.setBounds(4, 389, 390, 30);

        jPanel14.setBackground(new java.awt.Color(236, 82, 82));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ingresa la Informacion del Producto *");
        jLabel4.setToolTipText("");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jPanel11.add(jPanel14);
        jPanel14.setBounds(0, 50, 410, 40);

        Label_DescripcionPDT1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        Label_DescripcionPDT1.setText("Descripción");
        jPanel11.add(Label_DescripcionPDT1);
        Label_DescripcionPDT1.setBounds(30, 170, 100, 16);

        Label_DescripcionPDT2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        Label_DescripcionPDT2.setText("Precio Unidad");
        jPanel11.add(Label_DescripcionPDT2);
        Label_DescripcionPDT2.setBounds(20, 200, 102, 16);

        Label_DescripcionPDT3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        Label_DescripcionPDT3.setText("Precio Mayoreo");
        jPanel11.add(Label_DescripcionPDT3);
        Label_DescripcionPDT3.setBounds(10, 240, 120, 16);

        Label_DescripcionPDT4.setBackground(new java.awt.Color(44, 189, 165));
        Label_DescripcionPDT4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        Label_DescripcionPDT4.setForeground(new java.awt.Color(44, 189, 165));
        Label_DescripcionPDT4.setText("Departamento");
        jPanel11.add(Label_DescripcionPDT4);
        Label_DescripcionPDT4.setBounds(140, 270, 170, 28);

        rSButtonRiple27.setBackground(new java.awt.Color(0, 119, 145));
        rSButtonRiple27.setText("Guardar (Enter)");
        rSButtonRiple27.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel11.add(rSButtonRiple27);
        rSButtonRiple27.setBounds(50, 470, 290, 50);

        rSButtonRiple28.setBackground(new java.awt.Color(236, 82, 82));
        rSButtonRiple28.setText("Eliminar");
        rSButtonRiple28.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel11.add(rSButtonRiple28);
        rSButtonRiple28.setBounds(70, 530, 250, 50);

        rSButtonRiple29.setBackground(new java.awt.Color(204, 204, 204));
        rSButtonRiple29.setText("Cancelar");
        rSButtonRiple29.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel11.add(rSButtonRiple29);
        rSButtonRiple29.setBounds(100, 590, 200, 40);

        jPanel34.setBackground(new java.awt.Color(44, 189, 165));

        jLabel48.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Informacion Productos");
        jLabel48.setToolTipText("");

        RadioButton_IngresarCliente1.setBackground(new java.awt.Color(44, 189, 165));
        RadioButton_IngresarCliente1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioButton_IngresarCliente1.setForeground(new java.awt.Color(255, 255, 255));
        RadioButton_IngresarCliente1.setText("Ingresar cliente");

        RadioButton_PagosCliente1.setBackground(new java.awt.Color(44, 189, 165));
        RadioButton_PagosCliente1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioButton_PagosCliente1.setForeground(new java.awt.Color(255, 255, 255));
        RadioButton_PagosCliente1.setText("Pagos de cliente");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RadioButton_IngresarCliente1)
                .addGap(18, 18, 18)
                .addComponent(RadioButton_PagosCliente1)
                .addContainerGap())
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioButton_IngresarCliente1)
                    .addComponent(RadioButton_PagosCliente1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel34);
        jPanel34.setBounds(0, 0, 410, 50);

        jPanel3.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 73, 410, 685));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_ComprasPDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Table_ComprasPDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_ComprasPDT.setRowHeight(20);
        Table_ComprasPDT.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane4.setViewportView(Table_ComprasPDT);

        jLabel49.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 51, 51));
        jLabel49.setText("Productos Comprados");
        jLabel49.setToolTipText("");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(490, 490, 490)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(434, 83, 1440, 400));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_BodegaPDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_BodegaPDT.setRowHeight(20);
        Table_BodegaPDT.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane5.setViewportView(Table_BodegaPDT);

        Label.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        Label.setText("Productos en Bodega");

        rSMTextFull10.setForeground(new java.awt.Color(0, 0, 0));
        rSMTextFull10.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull10.setBordeColorNoFocus(new java.awt.Color(0, 0, 0));
        rSMTextFull10.setBotonColor(new java.awt.Color(255, 51, 0));
        rSMTextFull10.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rSMTextFull10.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        rSMTextFull10.setPlaceholder("Busqueda PRODUCTOS");

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_search_326690 (1).png"))); // NOI18N

        rSButtonRiple20.setText("Ultimo");

        rSButtonRiple21.setText("Primero");

        rSButtonRiple22.setText("Anterior");

        rSButtonRiple23.setText("Siguiente");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1412, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(516, 516, 516)
                        .addComponent(Label)
                        .addGap(111, 111, 111)
                        .addComponent(rSMTextFull10, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel50)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rSButtonRiple21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSButtonRiple22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSButtonRiple23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSButtonRiple20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(318, 318, 318))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Label)
                        .addComponent(rSMTextFull10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSButtonRiple21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRiple23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRiple20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSButtonRiple22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(434, 482, 1440, 270));

        jPanel33.setBackground(new java.awt.Color(44, 189, 165));

        jLabel47.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Productos");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap(899, Short.MAX_VALUE)
                .addComponent(jLabel47)
                .addGap(870, 870, 870))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 1887, -1));

        jTabbedPane1.addTab("Productos", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));

        RadioDepartamento.setBackground(new java.awt.Color(255, 255, 255));
        RadioDepartamento.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioDepartamento.setForeground(new java.awt.Color(44, 189, 165));
        RadioDepartamento.setText("Departamento");
        RadioDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioDepartamentoActionPerformed(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCategoriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoriaKeyTyped(evt);
            }
        });

        lblCategoria.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblCategoria.setText("Categoria");
        lblCategoria.setToolTipText("");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCategoria))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblDepartamento.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        lblDepartamento.setText("Departamento");
        lblDepartamento.setToolTipText("");

        txtDepartamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDepartamentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDepartamentoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDepartamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        RadioCategoria.setBackground(new java.awt.Color(255, 255, 255));
        RadioCategoria.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        RadioCategoria.setForeground(new java.awt.Color(44, 189, 165));
        RadioCategoria.setText("Categoria");
        RadioCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCategoriaActionPerformed(evt);
            }
        });

        btnGuardarDepartamento.setBackground(new java.awt.Color(0, 119, 145));
        btnGuardarDepartamento.setText("Guardar (Enter)");
        btnGuardarDepartamento.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnGuardarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarDepartamentoActionPerformed(evt);
            }
        });

        rSButtonRiple31.setBackground(new java.awt.Color(236, 82, 82));
        rSButtonRiple31.setText("Eliminar");
        rSButtonRiple31.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N

        rSButtonRiple32.setBackground(new java.awt.Color(204, 204, 204));
        rSButtonRiple32.setText("Cancelar");
        rSButtonRiple32.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(rSButtonRiple31, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(rSButtonRiple32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(RadioCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(RadioDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 37, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(RadioDepartamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RadioCategoria)
                .addGap(18, 18, 18)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(btnGuardarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(rSButtonRiple31, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(rSButtonRiple32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 74, -1, 691));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jtDepartamentos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtDepartamentos.setRowHeight(20);
        jtDepartamentos.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jtDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtDepartamentosMouseClicked(evt);
            }
        });
        jtDepartamentos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtDepartamentosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtDepartamentosKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jtDepartamentos);

        txtbuscarDepartamento.setForeground(new java.awt.Color(0, 0, 0));
        txtbuscarDepartamento.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtbuscarDepartamento.setBordeColorNoFocus(new java.awt.Color(0, 0, 0));
        txtbuscarDepartamento.setBotonColor(new java.awt.Color(255, 51, 0));
        txtbuscarDepartamento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtbuscarDepartamento.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        txtbuscarDepartamento.setPlaceholder("Busqueda PRODUCTOS");

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/iconfinder_search_326690 (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(txtbuscarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel54)))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtbuscarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 75, 1120, 690));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_Cat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Table_Cat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_Cat.setRowHeight(20);
        Table_Cat.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane8.setViewportView(Table_Cat);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1526, 74, -1, -1));

        jPanel35.setBackground(new java.awt.Color(44, 189, 165));

        jLabel51.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Departamentos - Categorias");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(771, 771, 771)
                .addComponent(jLabel51)
                .addContainerGap(789, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 1887, 50));

        jTabbedPane1.addTab("Categorias y Departamentos", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RadioButton_Unidad.setBackground(new java.awt.Color(255, 255, 255));
        RadioButton_Unidad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        RadioButton_Unidad.setForeground(new java.awt.Color(0, 153, 153));
        RadioButton_Unidad.setSelected(true);
        RadioButton_Unidad.setText("Por Unidad");
        jPanel10.add(RadioButton_Unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 146, 139, -1));

        RadioButton_Mayoreo.setBackground(new java.awt.Color(255, 255, 255));
        RadioButton_Mayoreo.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        RadioButton_Mayoreo.setForeground(new java.awt.Color(51, 51, 51));
        RadioButton_Mayoreo.setText("Por Mayoreo");
        jPanel10.add(RadioButton_Mayoreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 146, 150, -1));

        Label_DescripcionCP.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_DescripcionCP.setText("Tipo de Compra");
        Label_DescripcionCP.setToolTipText("");
        jPanel10.add(Label_DescripcionCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 121, -1, -1));

        TextField_DescripcionCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.add(TextField_DescripcionCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 218, 321, 30));

        rSButtonRiple24.setBackground(new java.awt.Color(0, 119, 145));
        rSButtonRiple24.setText("Guardar");
        rSButtonRiple24.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel10.add(rSButtonRiple24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 295, 52));

        rSButtonRiple25.setBackground(new java.awt.Color(236, 82, 82));
        rSButtonRiple25.setText("Eliminar");
        rSButtonRiple25.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel10.add(rSButtonRiple25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, 220, -1));

        rSButtonRiple26.setBackground(new java.awt.Color(204, 204, 204));
        rSButtonRiple26.setText("Cancelar");
        rSButtonRiple26.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel10.add(rSButtonRiple26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 158, -1));

        jPanel20.setBackground(new java.awt.Color(0, 119, 145));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ingresa Informacion de Productos");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel5)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, -1, -1));

        Label_DescripcionCP1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_DescripcionCP1.setText("Descripción");
        Label_DescripcionCP1.setToolTipText("");
        jPanel10.add(Label_DescripcionCP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 62, -1, -1));

        Label_DescripcionCP2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_DescripcionCP2.setText("Cantidad");
        Label_DescripcionCP2.setToolTipText("");
        jPanel10.add(Label_DescripcionCP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 190, 103, -1));

        TextField_DescripcionCP1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.add(TextField_DescripcionCP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 90, 321, 30));

        Label_DescripcionCP3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_DescripcionCP3.setText("Importe");
        Label_DescripcionCP3.setToolTipText("");
        jPanel10.add(Label_DescripcionCP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 337, 80, -1));

        TextField_DescripcionCP2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.add(TextField_DescripcionCP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 294, 183, 30));

        Label_DescripcionCP4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Label_DescripcionCP4.setText("Precio");
        Label_DescripcionCP4.setToolTipText("");
        jPanel10.add(Label_DescripcionCP4, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 260, 103, -1));

        TextField_DescripcionCP3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.add(TextField_DescripcionCP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 361, 183, 30));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_Compras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Table_Compras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_Compras.setRowHeight(20);
        Table_Compras.setSelectionBackground(new java.awt.Color(102, 204, 255));
        jScrollPane9.setViewportView(Table_Compras);

        rSButtonRiple37.setText("Anterior");

        rSButtonRiple38.setText("Primero");

        rSButtonRiple39.setText("Siguiente");

        rSButtonRiple40.setText("Ultimo");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1474, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(rSButtonRiple38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSButtonRiple37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSButtonRiple39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSButtonRiple40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 432, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rSButtonRiple40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rSButtonRiple39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rSButtonRiple37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rSButtonRiple38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setForeground(java.awt.SystemColor.activeCaption);
        jLabel18.setText("Compras");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(java.awt.SystemColor.activeCaption);
        jLabel19.setText("Buscar");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(204, 204, 204)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(TextField_BuscarCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(TextField_BuscarCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Compras", jPanel5);

        jPanel46.setBackground(new java.awt.Color(44, 189, 165));

        jLabel58.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("BODEGA");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(938, Short.MAX_VALUE)
                .addComponent(jLabel58)
                .addGap(870, 870, 870))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel47.setBackground(new java.awt.Color(51, 204, 255));
        jPanel47.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel47.setForeground(new java.awt.Color(240, 240, 240));

        jLabel1.setFont(new java.awt.Font("Arial Black", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INFORMACION DE BODEGA");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei UI", 3, 14)); // NOI18N
        jLabel9.setText("IdBodega");

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei UI", 3, 14)); // NOI18N
        jLabel11.setText("IdProducto");

        jLabel13.setFont(new java.awt.Font("Microsoft YaHei UI", 3, 14)); // NOI18N
        jLabel13.setText("Codigo");

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei UI", 3, 14)); // NOI18N
        jLabel14.setText("Existencia");

        jLabel22.setFont(new java.awt.Font("Microsoft YaHei UI", 3, 14)); // NOI18N
        jLabel22.setText("Fecha");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        txtExistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExistenciaActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(102, 0, 153));
        btnGuardar.setFont(new java.awt.Font("Arial Black", 3, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel22))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(357, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bodega", jPanel45);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel28.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 13, 192, 131));

        btnClientes.setBackground(new java.awt.Color(66, 103, 178));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/users.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jPanel28.add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(693, 69, -1, 70));

        btnVentas.setBackground(new java.awt.Color(66, 103, 178));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/sale (1).png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel28.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 69, 181, 70));

        btnProductos.setBackground(new java.awt.Color(66, 103, 178));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/product (1).png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel28.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 69, 225, 70));

        btnConfiguracion.setBackground(new java.awt.Color(66, 103, 178));
        btnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/settings (1).png"))); // NOI18N
        btnConfiguracion.setText("Configuracion");
        btnConfiguracion.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jPanel28.add(btnConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1634, 69, 268, 70));

        btnCategoriasok.setBackground(new java.awt.Color(66, 103, 178));
        btnCategoriasok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/list (1).png"))); // NOI18N
        btnCategoriasok.setText("Categorias");
        btnCategoriasok.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnCategoriasok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriasokActionPerformed(evt);
            }
        });
        jPanel28.add(btnCategoriasok, new org.netbeans.lib.awtextra.AbsoluteConstraints(1174, 69, 230, 70));

        btnProveedor.setBackground(new java.awt.Color(66, 103, 178));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fast-delivery.png"))); // NOI18N
        btnProveedor.setText("Proveedores");
        btnProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        jPanel28.add(btnProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 69, 267, 70));

        btnCompras1.setBackground(new java.awt.Color(66, 103, 178));
        btnCompras1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fast-delivery.png"))); // NOI18N
        btnCompras1.setText("Compras");
        btnCompras1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnCompras1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompras1ActionPerformed(evt);
            }
        });
        jPanel28.add(btnCompras1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1411, 69, 214, 70));

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel2.setText("Le Atiende:");

        lblUsuario.setBackground(new java.awt.Color(44, 189, 165));
        lblUsuario.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(44, 189, 165));
        lblUsuario.setText("Usuario");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblUsuario)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jPanel28.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 10, 370, 50));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel10.setText("Caja:");

        lblCaja.setBackground(new java.awt.Color(44, 189, 165));
        lblCaja.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 48)); // NOI18N
        lblCaja.setForeground(new java.awt.Color(44, 189, 165));
        lblCaja.setText("0");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(lblCaja)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(lblCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(1620, 10, 268, 50));

        fecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        fecha.setForeground(new java.awt.Color(44, 189, 165));
        fecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fecha.setText("DIA - MES - AÑO");
        jPanel28.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 260, -1));

        hora.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        hora.setForeground(new java.awt.Color(44, 189, 165));
        hora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hora.setText("HORA");
        jPanel28.add(hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 280, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1920, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        btnVentas.setEnabled(true);
        btnClientes.setEnabled(false);
        btnProductos.setEnabled(true);
        btnCategoriasok.setEnabled(true);
        btnProveedor.setEnabled(true);
        btnProveedor.setEnabled(true);
        RestablecerCamposClientes();


    }//GEN-LAST:event_btnClientesActionPerformed

    private void RadioAgreagarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioAgreagarClienteActionPerformed
        RadioAgreagarCliente.setForeground(new Color(0, 153, 51));
        RadioPagosCliente.setForeground(Color.black);
        txtNombreCliente.setEnabled(true);
        txtApellidosClientes.setEnabled(true);
        txtDireccionCliente.setEnabled(true);
        txtTelefonoCliente.setEnabled(true);
        txtidCliente.setEnabled(true);
        txtPagosClientes.setEnabled(false);
        RadioPagosCliente.setSelected(false);
    }//GEN-LAST:event_RadioAgreagarClienteActionPerformed

    private void RadioPagosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPagosClienteActionPerformed
        RadioPagosCliente.setForeground(new Color(0, 153, 51));
        RadioAgreagarCliente.setForeground(Color.black);
        txtPagosClientes.setEnabled(true);
        txtNombreCliente.setEnabled(false);
        txtApellidosClientes.setEnabled(false);
        txtDireccionCliente.setEnabled(false);
        txtTelefonoCliente.setEnabled(false);
        txtidCliente.setEnabled(false);
        RadioAgreagarCliente.setSelected(false);

//         RadioButton_PagosCliente.setForeground(new Color(0, 153, 51));
//        RadioButton_IngresarCliente.setForeground(Color.black);
//        TextField_PagosCliente.setEnabled(true);
//        TextField_NombreCliente.setEnabled(false);
//        TextField_ApellidioCliente.setEnabled(false);
//        TextField_DireccioCliente.setEnabled(false);
//        TextField_TelefonoCliente.setEnabled(false);
//        TextField_IdCliente.setEnabled(false);
//        RadioButton_IngresarCliente.setSelected(false);
    }//GEN-LAST:event_RadioPagosClienteActionPerformed

    private void txtNombreClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyReleased
        if (txtNombreCliente.getText().equals("")) {
            lblNombreClientes.setForeground(new Color(187, 187, 187));
        } else {
            lblNombreClientes.setText("Nombres ");
            lblNombreClientes.setForeground(new Color(44, 189, 165));
            lblImagenNombresClientes.setVisible(false);

        }
    }//GEN-LAST:event_txtNombreClienteKeyReleased

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        evento.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtApellidosClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosClientesKeyReleased
        if (txtApellidosClientes.getText().equals("")) {
            lblApellidosClientes.setForeground(new Color(187, 187, 187));
        } else {
            lblApellidosClientes.setText("Apellidos");
            lblApellidosClientes.setForeground(new Color(44, 189, 165));
            lblImagenApellidosClientes.setVisible(false);

        }
    }//GEN-LAST:event_txtApellidosClientesKeyReleased

    private void txtApellidosClientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosClientesKeyTyped
        evento.textKeyPress(evt);

    }//GEN-LAST:event_txtApellidosClientesKeyTyped

    private void txtDireccionClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyReleased
        if (txtDireccionCliente.getText().equals("")) {
            lblDireccionClientes.setForeground(new Color(187, 187, 187));
        } else {
            lblDireccionClientes.setText("Direccion");
            lblDireccionClientes.setForeground(new Color(44, 189, 165));
            lblImagenDireccionClientes.setVisible(false);
        }
    }//GEN-LAST:event_txtDireccionClienteKeyReleased

    private void txtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyTyped

    }//GEN-LAST:event_txtDireccionClienteKeyTyped

    private void txtidClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidClienteKeyReleased
        if (txtidCliente.getText().equals("")) {
            lblIDCliente.setForeground(new Color(187, 187, 187));

        } else {
            lblIDCliente.setText("ID");
            lblIDCliente.setForeground(new Color(44, 189, 165));
            lblImagenIDClientes.setVisible(false);
        }
    }//GEN-LAST:event_txtidClienteKeyReleased

    private void txtidClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidClienteKeyTyped


    }//GEN-LAST:event_txtidClienteKeyTyped

    private void txtTelefonoClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyReleased
        if (txtTelefonoCliente.getText().equals("")) {
            lblTelefonoClientes.setForeground(new Color(187, 187, 187));
        } else {
            lblTelefonoClientes.setText("Telefono");
            lblTelefonoClientes.setForeground(new Color(44, 189, 165));
            lblImagenTelefonoClientes.setVisible(false);

        }
    }//GEN-LAST:event_txtTelefonoClienteKeyReleased

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        evento.NumberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void lblIDClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblIDClienteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblIDClienteKeyReleased

    private void btnGuardarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClientesActionPerformed
        if (RadioAgreagarCliente.isSelected()) {
            GuardarClientes();

        } else {
            GuardarReportes();
        }
    }//GEN-LAST:event_btnGuardarClientesActionPerformed

    private void btnPrimeroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroVentasActionPerformed

    }//GEN-LAST:event_btnPrimeroVentasActionPerformed

    private void btnAnteriorVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorVentasActionPerformed

    }//GEN-LAST:event_btnAnteriorVentasActionPerformed

    private void btnSiguienteVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteVentasActionPerformed

    }//GEN-LAST:event_btnSiguienteVentasActionPerformed

    private void btnUltimoVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoVentasActionPerformed

    }//GEN-LAST:event_btnUltimoVentasActionPerformed

    private void btnPrimeroClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroClientesActionPerformed
        NumPagi = 0;
        lblPaginaClientes.setText("Paginas " + "1" + "/" + String.valueOf(PaginaCount));
        cliente.BuscarCliente(TablaClientes, "", NumPagi, PaginaSize);
    }//GEN-LAST:event_btnPrimeroClientesActionPerformed

    private void btnAneriorClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAneriorClientesActionPerformed
        if (PaginaCount != 1) {
            if (NumPagi > 0) {
                if (PaginaCount == NumPagi) {
                    NumPagi -= 1;

                }
                lblPaginaClientes.setText("Paginas " + String.valueOf(NumPagi)
                        + "/ " + String.valueOf(PaginaCount));
                NumPagi -= 1;
                NumRegistro = PaginaSize * NumPagi;
                cliente.BuscarCliente(TablaClientes, "", NumRegistro, PaginaSize);
            }
        }
    }//GEN-LAST:event_btnAneriorClientesActionPerformed

    private void btnSiguienteClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteClientesActionPerformed
        if (PaginaCount != 1) {
            if (NumPagi < PaginaCount) {
                if (NumPagi == 0) {
                    NumPagi += 1;
                }
                NumRegistro = PaginaSize * NumPagi;
                cliente.BuscarCliente(TablaClientes, "", NumRegistro, PaginaSize);
                NumPagi += 1;
                lblPaginaClientes.setText("Paginas " + String.valueOf(NumPagi)
                        + "/ " + String.valueOf(PaginaCount));
            }

        }
    }//GEN-LAST:event_btnSiguienteClientesActionPerformed

    private void btnUltimoClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoClientesActionPerformed
        NumPagi = PaginaCount;
        NumPagi--;
        NumRegistro = PaginaSize * NumPagi;
        lblPaginaClientes.setText("Paginas " + String.valueOf(PaginaCount)
                + "/ " + String.valueOf(PaginaCount));
        cliente.BuscarCliente(TablaClientes, "", NumRegistro, PaginaSize);
        NumPagi = PaginaCount;
    }//GEN-LAST:event_btnUltimoClientesActionPerformed

    private void btnCancelarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarClientesActionPerformed
        RestablecerCamposClientes();
        OcultarMensajesValidaciones();
    }//GEN-LAST:event_btnCancelarClientesActionPerformed

    private void TablaClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaClientesKeyReleased

    }//GEN-LAST:event_TablaClientesKeyReleased

    private void TablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaClientesMouseClicked
        if (TablaClientes.getSelectedRows().length > 0) {
            datosClientes();

        }
    }//GEN-LAST:event_TablaClientesMouseClicked

    private void btnEliminarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClientesActionPerformed
        if (idCliente == 0) {
            JOptionPane.showMessageDialog(null, "Selecciona Un Cliente para Eliminar");
        } else {
            if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Deseas Eliminar los Registros? " + " ", "Eliminar Registros ",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                cliente.EliminarCliente(idCliente, idRegistro);
                RestablecerCamposClientes();

            }
        }
    }//GEN-LAST:event_btnEliminarClientesActionPerformed

    private void lblNombreProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblNombreProveedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNombreProveedorKeyReleased

    private void lblEmailProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblEmailProveedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmailProveedorKeyReleased

    private void lblTelefonoProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTelefonoProveedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTelefonoProveedorKeyReleased

    private void lblPagosProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblPagosProveedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPagosProveedorKeyReleased

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed

        jTabbedPane1.setSelectedIndex(2);
        btnVentas.setEnabled(true);
        btnClientes.setEnabled(true);
        btnProductos.setEnabled(true);
        btnCategoriasok.setEnabled(true);
        btnCompras1.setEnabled(true);
        btnProveedor.setEnabled(false);
        restablecerProveedor();

    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnCompras1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompras1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCompras1ActionPerformed

    private void txtBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClientesActionPerformed

    }//GEN-LAST:event_txtBuscarClientesActionPerformed

    private void btnImprimmirClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimmirClientesActionPerformed
        cliente.ImprimirRecibo(Panel_ReciboCliente);
    }//GEN-LAST:event_btnImprimmirClientesActionPerformed

    private void txtNombreProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyReleased
        if (txtNombreProveedor.getText().equals("")) {
            lblNombreProveedor.setForeground(new Color(204, 0, 0));
        } else {
            lblNombreProveedor.setText("Proveedor");
            lblNombreProveedor.setForeground(new Color(44, 189, 165));
            lblImagenProveedor.setVisible(false);

        }
    }//GEN-LAST:event_txtNombreProveedorKeyReleased

    private void txtEmailProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailProveedorKeyReleased
        if (txtEmailProveedor.getText().equals("")) {
            lblEmailProveedor.setForeground(new Color(204, 0, 0));
        } else {
            lblEmailProveedor.setText("Email");
            lblEmailProveedor.setForeground(new Color(44, 189, 165));
            lblImagenEmail.setVisible(false);
        }
    }//GEN-LAST:event_txtEmailProveedorKeyReleased

    private void txtNumeroTelefonoProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTelefonoProveedorKeyReleased
        if (txtNumeroTelefonoProveedor.getText().equals("")) {
            lblTelefonoProveedor.setForeground(new Color(204, 0, 0));
        } else {
            lblTelefonoProveedor.setText("Telefono");
            lblTelefonoProveedor.setForeground(new Color(44, 189, 165));
        }
        if (!txtNumeroTelefonoProveedor.getText().matches("[0-9--6]*")) {
            txtNumeroTelefonoProveedor.setText("");
            txtNumeroTelefonoProveedor.requestFocus();
        }
    }//GEN-LAST:event_txtNumeroTelefonoProveedorKeyReleased

    private void txtPagosProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagosProveedorKeyReleased
        if (TablaReportesProveedor.getRowCount() == 0) {
            lblPagosProveedor.setText("Selecciona el Proveedor");
            lblPagosProveedor.setForeground(Color.RED);
        } else {
            if (!txtPagosProveedor.getText().equalsIgnoreCase("")) {
                lblPagosProveedor.setText("Pagos de Deudas");
                lblPagosProveedor.setForeground(new Color(0, 153, 51));
                String deuda1;
                double deuda2, deuda3, deudaTotal;
                deuda1 = (String) tablaModelReportPd.getValueAt(0, 2);
                pattern = Pattern.compile("[$]");
                matcher = pattern.matcher(deuda1);
                deuda1 = matcher.replaceAll("");
                deuda2 = formato.reconstruir(deuda1);
                deuda3 = Double.parseDouble(txtPagosProveedor.getText());
                pago = formato.decimal(deuda3);
                deudaTotal = deuda2 - deuda3;
                DeudaActual = formato.decimal(deudaTotal);
            }
        }
    }//GEN-LAST:event_txtPagosProveedorKeyReleased

    private void txtNumeroTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTelefonoProveedorKeyTyped
        evento.NumberKeyPress(evt);
        if (txtNumeroTelefonoProveedor.getText().length() == 8) {
            lblImagenTel.setVisible(true);
            mensajeTelefonoProveedor.setVisible(true);
            txtNumeroTelefonoProveedor.setText("");
            txtNumeroTelefonoProveedor.requestFocus();
            evt.consume();
        } else if (txtNumeroTelefonoProveedor.getText().length() == 7) {
            lblImagenTel.setVisible(false);
            mensajeTelefonoProveedor.setVisible(false);

        }
    }//GEN-LAST:event_txtNumeroTelefonoProveedorKeyTyped

    private void txtPagosProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagosProveedorKeyTyped
        evento.NumberDecimalKeyPress(evt, txtPagosProveedor);

    }//GEN-LAST:event_txtPagosProveedorKeyTyped

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        if (RadioIngresarProveedor.isSelected()) {
            GuardarProveedor();
        } else {
            guardarReportesProveedor();
        }

    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void RadioIngresarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioIngresarProveedorActionPerformed
        RadioIngresarProveedor.setForeground(new Color(44, 189, 165));
        RadioPagosProveedor.setForeground(Color.black);
        txtNombreProveedor.setEnabled(true);
        txtEmailProveedor.setEnabled(true);
        txtNumeroTelefonoProveedor.setEnabled(true);
        txtPagosProveedor.setEnabled(false);
        RadioPagosProveedor.setSelected(false);


    }//GEN-LAST:event_RadioIngresarProveedorActionPerformed

    private void btnAnteriorProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorProveedorActionPerformed
        if (PaginaCount != 1) {
            if (NumPagi > 0) {
                if (PaginaCount == NumPagi) {
                    NumPagi -= 1;

                }
                lblPaginaProveedores.setText("Paginas " + String.valueOf(NumPagi) + "/ " + String.valueOf(PaginaCount));
                NumPagi -= 1;
                NumRegistro = PaginaSize * NumPagi;
                proveedor.BuscarProveedores(TablaProveedores, "", NumRegistro, PaginaSize);
            }
        }

    }//GEN-LAST:event_btnAnteriorProveedorActionPerformed

    private void txtBuscarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClientesKeyPressed

    }//GEN-LAST:event_txtBuscarClientesKeyPressed

    private void txtBuscarClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClientesKeyReleased
        cliente.BuscarCliente(TablaClientes, txtBuscarClientes.getText(), NumPagi, PaginaSize);
    }//GEN-LAST:event_txtBuscarClientesKeyReleased

    private void TablaProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaProveedoresKeyReleased
        if (TablaProveedores.getSelectedRows().length > 0) {
            DatosProveedor();
        }
    }//GEN-LAST:event_TablaProveedoresKeyReleased

    private void TablaProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProveedoresMouseClicked
        if (TablaProveedores.getSelectedRows().length > 0) {
            DatosProveedor();
        }
    }//GEN-LAST:event_TablaProveedoresMouseClicked
    private void DatosProveedor() {
        accion = "update";
        tablaModelPD = proveedor.getModelo();
        int filas = TablaProveedores.getSelectedRow();
        idProveedor = Integer.valueOf((String) tablaModelPD.getValueAt(filas, 0));
        txtNombreProveedor.setText((String) tablaModelPD.getValueAt(filas, 1));
        txtEmailProveedor.setText((String) tablaModelPD.getValueAt(filas, 2));
        txtNumeroTelefonoProveedor.setText((String) tablaModelPD.getValueAt(filas, 3));
        tablaModelReportPd = proveedor.reportesProveedor(TablaReportesProveedor, idProveedor);
        idRegistro = Integer.valueOf((String) tablaModelReportPd.getValueAt(0, 0));
        lblNombrePro.setText((String) tablaModelReportPd.getValueAt(0, 1));
        lblSaldoActualPro.setText((String) tablaModelReportPd.getValueAt(0, 2));
        lblUltimoPagoPro.setText((String) tablaModelReportPd.getValueAt(0, 4));
        lblFechaPro.setText((String) tablaModelReportPd.getValueAt(0, 3));
    }
    private void btnCancelarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProveedorActionPerformed
        restablecerProveedor();
    }//GEN-LAST:event_btnCancelarProveedorActionPerformed

    private void RadioPagosProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPagosProveedorActionPerformed
        RadioPagosProveedor.setForeground(new Color(44, 189, 165));
        RadioIngresarProveedor.setForeground(Color.black);
        txtNombreProveedor.setEnabled(false);
        txtEmailProveedor.setEnabled(false);
        txtNumeroTelefonoProveedor.setEnabled(false);
        txtPagosProveedor.setEnabled(true);
        RadioIngresarProveedor.setSelected(false);
    }//GEN-LAST:event_RadioPagosProveedorActionPerformed

    private void btnPrimeroProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroProveedorActionPerformed
        NumPagi = 0;
        lblPaginaProveedores.setText("Paginas " + "1" + "/ " + String.valueOf(PaginaCount));
        proveedor.BuscarProveedores(TablaProveedores, "", NumPagi, PaginaSize);
    }//GEN-LAST:event_btnPrimeroProveedorActionPerformed

    private void btnSigueinteProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSigueinteProveedorActionPerformed
        if (PaginaCount != 1) {
            if (NumPagi < PaginaCount) {
                if (NumPagi == 0) {
                    NumPagi += 1;

                }
                NumRegistro = PaginaSize * NumPagi;
                proveedor.BuscarProveedores(TablaProveedores, "", NumRegistro, PaginaSize);
                NumPagi += 1;
                lblPaginaProveedores.setText("Pginas " + String.valueOf(NumPagi) + "/ " + String.valueOf(PaginaCount));

            }
        }
    }//GEN-LAST:event_btnSigueinteProveedorActionPerformed

    private void btnUltmimoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltmimoProveedorActionPerformed
        NumPagi = PaginaCount;
        NumPagi--;
        NumRegistro = PaginaSize * NumPagi;
        lblPaginaProveedores.setText("Paginas " + String.valueOf(PaginaCount) + "/ " + String.valueOf(PaginaCount));
        proveedor.BuscarProveedores(TablaProveedores, "", NumRegistro, PaginaSize);
        NumPagi = PaginaCount;
    }//GEN-LAST:event_btnUltmimoProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (idProveedor == 0) {
            JOptionPane.showMessageDialog(null, "Selecciona Un Proveedor para poderlo Eliminar", "Mensaje del Sistema", JOptionPane.WARNING_MESSAGE);

        } else {
            if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Estas Seguro de Eliminar el Proveedor Seleccionado? " + "'",
                    "Eliminando Registros", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                proveedor.eliminarProveedor(idProveedor, idRegistro);
                restablecerProveedor();
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void txtbuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarProveedorActionPerformed

    }//GEN-LAST:event_txtbuscarProveedorActionPerformed

    private void txtbuscarProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarProveedorKeyPressed
        proveedor.BuscarProveedores(TablaProveedores, txtbuscarProveedor.getText(), NumRegistro, PaginaCount);
    }//GEN-LAST:event_txtbuscarProveedorKeyPressed

    private void txtPagosProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPagosProveedorActionPerformed

    }//GEN-LAST:event_txtPagosProveedorActionPerformed

    private void btnImprimirReciboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirReciboProveedorActionPerformed
        proveedor.ImprimirRecibo(PanelReciboProveedor);
    }//GEN-LAST:event_btnImprimirReciboProveedorActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Deseas Salir del Sistema? " + " ", "Cerrar Sesion",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {

            RestablecerCamposClientes();
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            new Acceso().setVisible(true);
            int idUsuario = listUsuario.get(0).getIdUsuario();
            String nombre = listUsuario.get(0).getNombre();
            String apellido = listUsuario.get(0).getApellido();
            String user = listUsuario.get(0).getUsuario();

            if (rol.equals("Admin")) {
                caja.insertarCajaRegistro(idUsuario, nombre, apellido, user, rol, 0, 0, false, new Calendario().getHora(), new Calendario().getFecha());
            } else {
                int idCaja = listCajas.get(0).getIdCaja();
                int cajas = listCajas.get(0).getCaja();
                caja.actualizarCaja(idCaja, true);
                caja.insertarCajaRegistro(idUsuario, nombre, apellido, user, rol, idCaja, cajas, false, new Calendario().getHora(), new Calendario().getFecha());
            }
            dispose();
        } else {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }


    }//GEN-LAST:event_formWindowClosing


    private void btnCategoriasokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriasokActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        btnVentas.setEnabled(true);
        btnClientes.setEnabled(true);
        btnProductos.setEnabled(true);
        btnCategoriasok.setEnabled(true);
        btnCompras1.setEnabled(true);
        btnProveedor.setEnabled(true);
        btnCategoriasok.setEnabled(false);
    }//GEN-LAST:event_btnCategoriasokActionPerformed

    private void RadioDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioDepartamentoActionPerformed
        txtCategoria.setEnabled(false);
        txtDepartamento.setEnabled(true);
        RadioDepartamento.setForeground(new Color(44, 189, 165));
        RadioCategoria.setForeground(Color.black);
        RadioCategoria.setSelected(false);
        txtDepartamento.setText("");
    }//GEN-LAST:event_RadioDepartamentoActionPerformed

    private void RadioCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioCategoriaActionPerformed
        txtDepartamento.setEnabled(false);
        txtCategoria.setEnabled(true);
        RadioDepartamento.setForeground(Color.black);
        RadioDepartamento.setSelected(false);
        RadioCategoria.setForeground(new Color(44, 189, 165));
        txtCategoria.setText("");
    }//GEN-LAST:event_RadioCategoriaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date sistemaFech = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("EEEE dd MMMM yyyy");
        fecha.setText(formato.format(sistemaFech));

        Timer tiempo = new Timer(100, new Menu.horas());
        tiempo.start();
    }//GEN-LAST:event_formWindowOpened

    private void txtDepartamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDepartamentoKeyReleased
        if (txtDepartamento.getText().equals("")) {
            lblCategoria.setForeground(new Color(102, 102, 102));
        } else {
            lblCategoria.setText("Categoria");
            lblCategoria.setForeground(new Color(0, 153, 51));
        }
    }//GEN-LAST:event_txtDepartamentoKeyReleased

    private void txtDepartamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDepartamentoKeyTyped
        evento.textKeyPress(evt);
    }//GEN-LAST:event_txtDepartamentoKeyTyped

    private void txtCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoriaKeyReleased
        if (txtCategoria.getText().equals("")) {
            lblDepartamento.setForeground(new Color(102, 102, 102));
        } else {
            lblDepartamento.setText("Departamento");
            lblDepartamento.setForeground(new Color(0, 153, 51));
        }
    }//GEN-LAST:event_txtCategoriaKeyReleased

    private void txtCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoriaKeyTyped
        evento.textKeyPress(evt);
    }//GEN-LAST:event_txtCategoriaKeyTyped

    private void btnGuardarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarDepartamentoActionPerformed
        guardarDepartartamento();
    }//GEN-LAST:event_btnGuardarDepartamentoActionPerformed

    private void jtDepartamentosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDepartamentosKeyPressed

    }//GEN-LAST:event_jtDepartamentosKeyPressed

    private void jtDepartamentosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDepartamentosKeyReleased
        if (jtDepartamentos.getSelectedRows().length > 0) {
            datosDpto();
        }
    }//GEN-LAST:event_jtDepartamentosKeyReleased

    private void jtDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtDepartamentosMouseClicked
        if (jtDepartamentos.getSelectedRows().length > 0) {
            datosDpto();
        }
    }//GEN-LAST:event_jtDepartamentosMouseClicked

    private void txtPagosClientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagosClientesKeyTyped
        evento.NumberKeyPress(evt);
    }//GEN-LAST:event_txtPagosClientesKeyTyped

    private void txtPagosClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagosClientesKeyReleased
        if (TablaReporteClientes.getRowCount() == 0) {
            lblPagoClientes.setText("Selecciona el Cliente...");
            lblPagoClientes.setForeground(Color.RED);
        } else {
            if (!txtPagosClientes.getText().equalsIgnoreCase("")) {
                lblPagoClientes.setText("Pagos de Deudas");
                lblPagoClientes.setForeground(new Color(0, 153, 51));
                String deuda1;
                double deuda2, deuda3, deudaTotal;
                deuda1 = (String) TablaReporteClientes.getValueAt(0, 3);
                pattern = Pattern.compile("[$]");
                matcher = pattern.matcher(deuda1);
                deuda1 = matcher.replaceAll("");
                deuda2 = formato.reconstruir(deuda1);
                deuda3 = Double.parseDouble(txtPagosClientes.getText());
                pago = formato.decimal(deuda3);
                deudaTotal = deuda2 - deuda3;
                DeudaActual = formato.decimal(deudaTotal);

            }

        }
        if (txtPagosClientes.getText().equals("")) {
            txtPagosClientes.setForeground(new Color(187, 187, 187));
        } else {
            lblPagoClientes.setText("Pago de Clientes");
            lblPagoClientes.setForeground(new Color(44, 189, 165));
        }
    }//GEN-LAST:event_txtPagosClientesKeyReleased

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtExistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExistenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExistenciaActionPerformed

    private void datosDpto() {
        if (RadioDepartamento.isSelected()) {
            accion = "update";
        }
        tablaModelDpt = departamento.getModelo();
        int filas = jtDepartamentos.getSelectedRow();
        idDpto = Integer.valueOf((String) tablaModelDpt.getValueAt(filas, 0));
        txtDepartamento.setText((String) tablaModelDpt.getValueAt(filas, 1));
    }

    private void guardarDepartartamento() {
        if (RadioDepartamento.isSelected()) {
            if (txtDepartamento.getText().equals("")) {
                lblDepartamento.setText("Ingresa Departamento");
                lblDepartamento.setForeground(Color.RED);
                txtDepartamento.requestFocus();

            } else {
                switch (accion) {
                    case "insert":
                        departamento.inseratDepartamentoCategoria(txtDepartamento.getText(), 0, "dpto");
                        break;
                    case "update":
                        departamento.actualizarDepartamentoCategoria(txtDepartamento.getText(), idDpto, 0, "dpto");
                        break;
                }
                retsablecerDepCategorias();
            }
        }
    }

    private void retsablecerDepCategorias() {
        accion = "insert";
        txtDepartamento.setEnabled(true);
        RadioDepartamento.setForeground(new Color(0, 153, 51));
        txtDepartamento.setText("");
        txtCategoria.setEnabled(false);
        RadioDepartamento.setForeground(Color.black);
        RadioDepartamento.setSelected(true);
        txtCategoria.setText("");
        lblDepartamento.setForeground(Color.black);
        lblCategoria.setForeground(Color.black);
        departamento.buscarDepartamento(jtDepartamentos, "");
    }

    class horas implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Date sistemaHora = new Date();
            String pmAm = "hh:mm:ss a";
            SimpleDateFormat formato = new SimpleDateFormat(pmAm);
            Calendar now = Calendar.getInstance();
            hora.setText(String.format(formato.format(sistemaHora), now));
        }
    }

    private void restablecerProveedor() {
        Tab = 2;
        accion = "Insert";
        idProveedor = 0;
        idRegistro = 0;
        NumRegistro = 0;
        NumPagi = 0;
        txtNombreProveedor.setText("");
        txtEmailProveedor.setText("");
        txtPagosProveedor.setText("");
        lblPagosProveedor.setText("Pagos Proveedor");
        lblPagosProveedor.setForeground(new Color(102, 102, 102));
        txtNumeroTelefonoProveedor.setText("");
        txtNombreProveedor.requestFocus();
        lblNombreProveedor.setText("Proveedor");
        lblNombreProveedor.setForeground(new Color(102, 102, 102));
        lblEmailProveedor.setText("Email");
        lblEmailProveedor.setForeground(new Color(102, 102, 102));
        lblTelefonoProveedor.setText("N.Tel/Cel");
        lblTelefonoProveedor.setForeground(new Color(102, 102, 102));
        RadioIngresarProveedor.setSelected(true);
        RadioIngresarProveedor.setForeground(new Color(0, 153, 51));
        txtNombreProveedor.setEnabled(true);
        txtEmailProveedor.setEnabled(true);
        txtNumeroTelefonoProveedor.setEnabled(true);
        txtPagosProveedor.setEnabled(false);
        RadioPagosProveedor.setSelected(false);
        RadioPagosProveedor.setForeground(Color.black);
        lblNombrePro.setText("Proveedor");
        lblSaldoActualPro.setText("$0.00");
        lblUltimoPagoPro.setText("$0.00");
        lblFechaPro.setText("Fecha");
        CargarDatos();
        proveedor.reportesProveedor(TablaReportesProveedor, idProveedor);
    }

    private void GuardarProveedor() {
        if (txtNombreProveedor.getText().equals("")) {
            lblNombreProveedor.setText("Ingrese el Proveedor");
            lblImagenProveedor.setVisible(true);
            lblNombreProveedor.setForeground(new Color(229, 0, 39));
            lblNombreProveedor.requestFocus();
        } else {
            if (txtEmailProveedor.getText().equals("")) {
                lblEmailProveedor.setText("Ingresa el Email");
                lblImagenEmail.setVisible(true);
                lblEmailProveedor.setForeground(new Color(229, 0, 39));
                lblEmailProveedor.requestFocus();
            } else {
                if (txtNumeroTelefonoProveedor.getText().equals("")) {
                    lblTelefonoProveedor.setText("Ingresa el Telefono");
                    lblTelefonoProveedor.setForeground(new Color(229, 0, 39));
                    lblTelefonoProveedor.requestFocus();

                } else {
                    if (evento.ValidaEmail(txtEmailProveedor.getText())) {
                        String Proveedor = txtNombreProveedor.getText();
                        String Email = txtEmailProveedor.getText();
                        String Telefono = txtNumeroTelefonoProveedor.getText();
                        boolean valor;
                        switch (accion) {
                            case "Insert":
                                dataProveedor = proveedor.InsertarProveedores(Proveedor, Email, Telefono);
                                if (0 == dataProveedor.size()) {
                                    restablecerProveedor();
                                } else {
                                    if (dataProveedor.get(0).getTelefono().equals(Telefono)) {
                                        lblTelefonoProveedor.setText("El Telefono ya esta Registrado");
                                        lblTelefonoProveedor.setForeground(Color.RED);
                                        txtNumeroTelefonoProveedor.requestFocus();

                                    }
                                    if (dataProveedor.get(0).getEmail().equals(Email)) {
                                        lblEmailProveedor.setText("El Email ya esta Registrado");
                                        lblEmailProveedor.setForeground(Color.RED);
                                        txtEmailProveedor.requestFocus();

                                    }
                                }
                                break;
                            case "update":
                                if (rol.equals("Admin")) {
                                    dataProveedor = proveedor.actualizarProveedor(idProveedor, Proveedor, Email, Telefono);
                                    if (0 == dataProveedor.size()) {
                                        restablecerProveedor();
                                    } else {
                                        if (idProveedor != dataProveedor.get(0).getIDProveedor()) {
                                            lblTelefonoProveedor.setText("El Tel/Cel ya esta Registrado");
                                            lblTelefonoProveedor.setForeground(Color.RED);
                                            txtNumeroTelefonoProveedor.requestFocus();
                                        }
                                        if (2 == dataProveedor.size() && idProveedor != dataProveedor.get(1).getIDProveedor()) {
                                            lblEmailProveedor.setText("El Email ya fue Registrado");
                                            lblEmailProveedor.setForeground(Color.RED);
                                            txtEmailProveedor.requestFocus();
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "No Cuentas con el Permiso Requerido", "Mensaje del Sistema", JOptionPane.WARNING_MESSAGE);
                                }
                                break;
                        }
                    } else {

                        lblEmailProveedor.setText("Ingresa ejemplo@hotmail.com");
                        lblEmailProveedor.setForeground(new Color(229, 0, 39));
                        lblImagenEmail.setVisible(true);
                        lblEmailProveedor.requestFocus();
                    }
                }
            }
        }
    }

    private void datosClientes() {
        accion = "update";
        tablaModeloCLT = cliente.getModelo();
        int filas = TablaClientes.getSelectedRow();
        idCliente = Integer.valueOf((String) tablaModeloCLT.getValueAt(filas, 0));

        txtidCliente.setText((String) tablaModeloCLT.getValueAt(filas, 1));
        txtNombreCliente.setText((String) tablaModeloCLT.getValueAt(filas, 2));
        txtApellidosClientes.setText((String) tablaModeloCLT.getValueAt(filas, 3));
        txtDireccionCliente.setText((String) tablaModeloCLT.getValueAt(filas, 4));
        txtTelefonoCliente.setText((String) tablaModeloCLT.getValueAt(filas, 5));
        tablaModelReporteCliente = cliente.reportesCliente(TablaReporteClientes, idCliente);

        idRegistro = Integer.valueOf((String) tablaModelReporteCliente.getValueAt(0, 0));
        Label_NombreCLT.setText((String) tablaModelReporteCliente.getValueAt(0, 1));
        Label_ApellidoCLT.setText((String) tablaModelReporteCliente.getValueAt(0, 2));
        Label_SaldoActualCLT.setText((String) tablaModelReporteCliente.getValueAt(0, 3));
        Label_UltimoPagoCLT.setText((String) tablaModelReporteCliente.getValueAt(0, 5));
        Label_FechaPagoCLT.setText((String) tablaModelReporteCliente.getValueAt(0, 6));

    }

    public void RestablecerCamposClientes() {
        Tab = 1;
        accion = "Insert";
        idCliente = 0;
        idRegistro = 0;
        NumRegistro = 0;
        NumPagi = 0;
        txtidCliente.setText("");
        txtNombreCliente.setText("");
        txtApellidosClientes.setText("");
        txtDireccionCliente.setText("");
        txtTelefonoCliente.setText("");
        txtPagosClientes.setText("");

        txtidCliente.setEnabled(true);
        txtNombreCliente.setEnabled(true);
        txtApellidosClientes.setEnabled(true);
        txtDireccionCliente.setEnabled(true);
        txtTelefonoCliente.setEnabled(true);
        txtPagosClientes.setEditable(false);
        lblIDCliente.setForeground(new Color(236, 82, 82));
        lblIDCliente.setText("ID");
        lblNombreClientes.setForeground(new Color(236, 82, 82));
        lblNombreClientes.setText("Nombre Completo");
        lblApellidosClientes.setForeground(new Color(236, 82, 82));
        lblApellidosClientes.setText("Apellidos");
        lblDireccionClientes.setForeground(new Color(236, 82, 82));
        lblDireccionClientes.setText("Direccion");
        lblTelefonoClientes.setForeground(new Color(236, 82, 82));
        lblTelefonoClientes.setText("Telefono");
        lblPagoClientes.setForeground(new Color(236, 82, 82));
        lblPagoClientes.setText("Pagos de Deudas");
        RadioAgreagarCliente.setSelected(true);
        RadioPagosCliente.setSelected(false);
        RadioAgreagarCliente.setForeground(new Color(0, 153, 51));
        RadioPagosCliente.setForeground(Color.black);
        CargarDatos();
        cliente.reportesCliente(TablaReporteClientes, idCliente);
        Label_NombreCLT.setText("Nombres");
        Label_ApellidoCLT.setText("Apellidos");
        Label_SaldoActualCLT.setText("$0.00");
        Label_UltimoPagoCLT.setText("$0.00");
        Label_FechaPagoCLT.setText("Fecha");

    }

    private void GuardarClientes() {
        if (txtidCliente.getText().equals("")) {
            lblIDCliente.setText("Ingresa ID");
            lblImagenIDClientes.setVisible(true);
            lblIDCliente.setForeground(new Color(236, 82, 82));
            txtidCliente.requestFocus();
        } else {
            if (txtNombreCliente.getText().equals("")) {
                lblNombreClientes.setText("Ingresa los Nombres");
                lblImagenNombresClientes.setVisible(true);
                lblNombreClientes.setForeground(new Color(236, 82, 82));

                txtNombreCliente.requestFocus();

            } else {
                if (txtApellidosClientes.getText().equals("")) {
                    lblApellidosClientes.setText("Ingresa los Apellidos");
                    lblImagenApellidosClientes.setVisible(true);
                    lblApellidosClientes.setForeground(new Color(236, 82, 82));
                    txtApellidosClientes.requestFocus();
                } else {
                    if (txtDireccionCliente.getText().equals("")) {
                        lblDireccionClientes.setText("Ingresa la Direccion");
                        lblImagenDireccionClientes.setVisible(true);
                        lblDireccionClientes.setForeground(new Color(236, 82, 82));
                        txtDireccionCliente.requestFocus();
                    } else {

                        if (txtTelefonoCliente.getText().equals("")) {
                            lblTelefonoClientes.setText("Ingresa el Numero de Telefono/Celular");
                            lblImagenTelefonoClientes.setVisible(true);
                            lblTelefonoClientes.setForeground(new Color(236, 82, 82));
                            txtTelefonoCliente.requestFocus();
                        } else {
                            String ID = txtidCliente.getText();
                            String Nombre = txtNombreCliente.getText();
                            String Apellido = txtApellidosClientes.getText();
                            String Direccion = txtDireccionCliente.getText();
                            String Telefono = txtTelefonoCliente.getText();
                            boolean valor;
                            if (accion.equals("Insert")) {

                                valor = cliente.InsertarCliente(ID, Nombre, Apellido, Direccion, Telefono);
                                if (valor) {
                                    RestablecerCamposClientes();
                                } else {
                                    lblIDCliente.setText("EL Numero de ID ya esta Registrada");
                                    lblIDCliente.setForeground(Color.RED);
                                    txtidCliente.requestFocus();
                                }
                            }
                            if (rol.equals("Admin")) {
                                if (accion.equals("update")) {
                                    valor = cliente.ActualizarClientes(ID, Nombre, Apellido, Direccion, Telefono, idCliente);
                                    if (valor) {
                                        RestablecerCamposClientes();
                                    } else {
                                        lblIDCliente.setText("EL Numero de ID ya esta Registrada");
                                        lblIDCliente.setForeground(Color.RED);
                                        txtidCliente.requestFocus();

                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "No Cuentas con el Permiso Requerido", "Mensaje del Sistema", JOptionPane.WARNING_MESSAGE);
                            }

                        }
                    }
                }
            }
        }
    }

    private void guardarReportesProveedor() {
        if (txtPagosProveedor.getText().equals("")) {
            lblPagosProveedor.setText("Ingresa el Pago");
            lblPagosProveedor.setForeground(Color.RED);
            txtPagosProveedor.requestFocus();

        } else {
            proveedor.actualizarReportePro(DeudaActual, new Calendario().getFecha(), pago, idProveedor);
            restablecerProveedor();
        }
    }

    private void GuardarReportes() {
        if (txtPagosClientes.getText().equals("")) {
            lblPagoClientes.setText("Ingresa el Pago");
            lblPagoClientes.setForeground(Color.RED);
            txtPagosClientes.requestFocus();

        } else {
            cliente.ActualizarReportes(DeudaActual, new Calendario().getFecha(), pago, idCliente);
            RestablecerCamposClientes();
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_PrimeroCLT2;
    private javax.swing.JComboBox ComboBox_Categoria;
    private javax.swing.JComboBox ComboBox_Departamento;
    private javax.swing.JLabel Label;
    private javax.swing.JLabel Label_ApellidoCLT;
    private javax.swing.JLabel Label_ApellidoCliente1;
    private javax.swing.JLabel Label_ApellidoCliente2;
    private javax.swing.JLabel Label_ApellidoCliente3;
    private javax.swing.JLabel Label_CodigoPDT;
    private javax.swing.JLabel Label_DescripcionCP;
    private javax.swing.JLabel Label_DescripcionCP1;
    private javax.swing.JLabel Label_DescripcionCP2;
    private javax.swing.JLabel Label_DescripcionCP3;
    private javax.swing.JLabel Label_DescripcionCP4;
    private javax.swing.JLabel Label_DescripcionPDT;
    private javax.swing.JLabel Label_DescripcionPDT1;
    private javax.swing.JLabel Label_DescripcionPDT2;
    private javax.swing.JLabel Label_DescripcionPDT3;
    private javax.swing.JLabel Label_DescripcionPDT4;
    private javax.swing.JLabel Label_DireccionCliente1;
    private javax.swing.JLabel Label_DireccionCliente2;
    private javax.swing.JLabel Label_DireccionCliente3;
    private javax.swing.JLabel Label_FechaPagoCLT;
    private javax.swing.JLabel Label_NombreCLT;
    private javax.swing.JLabel Label_NombreCliente1;
    private javax.swing.JLabel Label_SaldoActualCLT;
    private javax.swing.JLabel Label_UltimoPagoCLT;
    private javax.swing.JPanel PanelReciboProveedor;
    private javax.swing.JPanel Panel_ReciboCliente;
    private javax.swing.JRadioButton RadioAgreagarCliente;
    private javax.swing.JRadioButton RadioButton_IngresarCliente;
    private javax.swing.JRadioButton RadioButton_IngresarCliente1;
    private javax.swing.JRadioButton RadioButton_IngresarCliente2;
    private javax.swing.JRadioButton RadioButton_Mayoreo;
    private javax.swing.JRadioButton RadioButton_PagosCliente;
    private javax.swing.JRadioButton RadioButton_PagosCliente1;
    private javax.swing.JRadioButton RadioButton_PagosCliente2;
    private javax.swing.JRadioButton RadioButton_Unidad;
    private javax.swing.JRadioButton RadioCategoria;
    private javax.swing.JRadioButton RadioDepartamento;
    private javax.swing.JRadioButton RadioIngresarProveedor;
    private javax.swing.JRadioButton RadioPagosCliente;
    private javax.swing.JRadioButton RadioPagosProveedor;
    private javax.swing.JTable TablaClientes;
    private javax.swing.JTable TablaProveedores;
    private javax.swing.JTable TablaReporteClientes;
    private javax.swing.JTable TablaReportesProveedor;
    private javax.swing.JTable Table_BodegaPDT;
    private javax.swing.JTable Table_Cat;
    private javax.swing.JTable Table_Clientes1;
    private javax.swing.JTable Table_Compras;
    private javax.swing.JTable Table_ComprasPDT;
    private javax.swing.JTable Table_ReportesCLT1;
    private javax.swing.JTextField TextField_BuscarCompras;
    private javax.swing.JTextField TextField_CodigoPDT;
    private javax.swing.JTextField TextField_DescripcionCP;
    private javax.swing.JTextField TextField_DescripcionCP1;
    private javax.swing.JTextField TextField_DescripcionCP2;
    private javax.swing.JTextField TextField_DescripcionCP3;
    private javax.swing.JTextField TextField_DescripcionPDT;
    private javax.swing.JTextField TextField_PrecioMayoreoPDT;
    private javax.swing.JTextField TextField_PrecioUnidadPDT;
    private rojeru_san.RSButtonRiple btnAneriorClientes;
    private rojeru_san.RSButtonRiple btnAnteriorProveedor;
    private rojeru_san.RSButtonRiple btnAnteriorVentas;
    private rojeru_san.RSButtonRiple btnCancelarClientes;
    private rojeru_san.RSButtonRiple btnCancelarProveedor;
    private rojeru_san.RSButtonRiple btnCategoriasok;
    private rojeru_san.RSButtonRiple btnClientes;
    private rojeru_san.RSButtonRiple btnCompras1;
    private rojeru_san.RSButtonRiple btnConfiguracion;
    private rojeru_san.RSButtonRiple btnEliminarClientes;
    private rojeru_san.RSButtonRiple btnEliminarProveedor;
    private javax.swing.JButton btnGuardar;
    private rojeru_san.RSButtonRiple btnGuardarClientes;
    private rojeru_san.RSButtonRiple btnGuardarDepartamento;
    private rojeru_san.RSButtonRiple btnGuardarProveedor;
    private javax.swing.JButton btnImprimirReciboProveedor;
    private rojeru_san.RSButtonRiple btnImprimmirClientes;
    private rojeru_san.RSButtonRiple btnPrimeroClientes;
    private rojeru_san.RSButtonRiple btnPrimeroProveedor;
    private rojeru_san.RSButtonRiple btnPrimeroVentas;
    private rojeru_san.RSButtonRiple btnProductos;
    private rojeru_san.RSButtonRiple btnProveedor;
    private rojeru_san.RSButtonRiple btnSigueinteProveedor;
    private rojeru_san.RSButtonRiple btnSiguienteClientes;
    private rojeru_san.RSButtonRiple btnSiguienteVentas;
    private rojeru_san.RSButtonRiple btnUltimoClientes;
    private rojeru_san.RSButtonRiple btnUltimoVentas;
    private rojeru_san.RSButtonRiple btnUltmimoProveedor;
    private rojeru_san.RSButtonRiple btnVentas;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel hora;
    private javax.swing.JCheckBox jCheckBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jtDepartamentos;
    private javax.swing.JLabel lblApellidosClientes;
    private javax.swing.JLabel lblCaja;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDepartamento;
    private javax.swing.JLabel lblDireccionClientes;
    private javax.swing.JLabel lblEmailProveedor;
    private javax.swing.JLabel lblFechaPro;
    private javax.swing.JLabel lblIDCliente;
    private javax.swing.JLabel lblImagenApellidosClientes;
    private javax.swing.JLabel lblImagenDireccionClientes;
    private javax.swing.JLabel lblImagenEmail;
    private javax.swing.JLabel lblImagenIDClientes;
    private javax.swing.JLabel lblImagenNombresClientes;
    private javax.swing.JLabel lblImagenProveedor;
    private javax.swing.JLabel lblImagenTel;
    private javax.swing.JLabel lblImagenTelefonoClientes;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNombreClientes;
    private javax.swing.JLabel lblNombrePro;
    private javax.swing.JLabel lblNombreProveedor;
    private javax.swing.JLabel lblPaginaClientes;
    private javax.swing.JLabel lblPaginaProveedores;
    private javax.swing.JLabel lblPagoClientes;
    private javax.swing.JLabel lblPagosProveedor;
    private javax.swing.JLabel lblSaldoActualPro;
    private javax.swing.JLabel lblTelefonoClientes;
    private javax.swing.JLabel lblTelefonoProveedor;
    private javax.swing.JLabel lblUltimoPagoPro;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel mensajeTelefonoProveedor;
    private javax.swing.JLabel na;
    private javax.swing.JLabel nada;
    private javax.swing.JLabel ok;
    private rojeru_san.RSButtonRiple rSButtonRiple10;
    private rojeru_san.RSButtonRiple rSButtonRiple11;
    private rojeru_san.RSButtonRiple rSButtonRiple20;
    private rojeru_san.RSButtonRiple rSButtonRiple21;
    private rojeru_san.RSButtonRiple rSButtonRiple22;
    private rojeru_san.RSButtonRiple rSButtonRiple23;
    private rojeru_san.RSButtonRiple rSButtonRiple24;
    private rojeru_san.RSButtonRiple rSButtonRiple25;
    private rojeru_san.RSButtonRiple rSButtonRiple26;
    private rojeru_san.RSButtonRiple rSButtonRiple27;
    private rojeru_san.RSButtonRiple rSButtonRiple28;
    private rojeru_san.RSButtonRiple rSButtonRiple29;
    private rojeru_san.RSButtonRiple rSButtonRiple31;
    private rojeru_san.RSButtonRiple rSButtonRiple32;
    private rojeru_san.RSButtonRiple rSButtonRiple37;
    private rojeru_san.RSButtonRiple rSButtonRiple38;
    private rojeru_san.RSButtonRiple rSButtonRiple39;
    private rojeru_san.RSButtonRiple rSButtonRiple40;
    private rojeru_san.RSMTextFull rSMTextFull10;
    private rojeru_san.RSMTextFull rSMTextFull6;
    private rojeru_san.RSMTextFull rSMTextFull7;
    private rojeru_san.RSMTextFull rSMTextFull8;
    private rojeru_san.RSMTextFull txtApellidosClientes;
    private rojeru_san.RSMTextFull txtBuscarClientes;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDepartamento;
    private rojeru_san.RSMTextFull txtDireccionCliente;
    private rojeru_san.RSMTextFull txtEmailProveedor;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdProducto;
    private rojeru_san.RSMTextFull txtNombreCliente;
    private rojeru_san.RSMTextFull txtNombreProveedor;
    private rojeru_san.RSMTextFull txtNumeroTelefonoProveedor;
    private rojeru_san.RSMTextFull txtPagosClientes;
    private rojeru_san.RSMTextFull txtPagosProveedor;
    private rojeru_san.RSMTextFull txtTelefonoCliente;
    private rojeru_san.RSMTextFull txtbuscarDepartamento;
    private rojeru_san.RSMTextFull txtbuscarProveedor;
    private rojeru_san.RSMTextFull txtidCliente;
    // End of variables declaration//GEN-END:variables
}
