package Vista;

import Modelo.ClsCajas;
import Modelo.ClsUsuarios;
import ModeloClases.Usuario;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.util.List;

public class Acceso extends javax.swing.JFrame {

    Usuario usuario = new Usuario();

    public Acceso() {
        initComponents();
        QuitarBordeBoton();
        this.setLocationRelativeTo(null);
        ocultarValidacion();
        lblMensaje.setText("");

        txtUsuario.requestFocus();

    }

    private void ocultarValidacion() {
        lblImagenUsuario.setVisible(false);
        lblImagenContraseña.setVisible(false);
        lblImagenValidar.setVisible(false);
        lblImagenValidar1.setVisible(false);
    }

    public void QuitarBordeBoton() {
        btnMini.setOpaque(false);
        btnMini.setContentAreaFilled(false);
        btnMini.setBorderPainted(false);
        btnMini.setOpaque(false);

        btnIngresar.setOpaque(false);
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setOpaque(false);

        btnSalir.setOpaque(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setOpaque(false);

        btnMini.setOpaque(false);
        btnMini.setContentAreaFilled(false);
        btnMini.setBorderPainted(false);
        btnMini.setOpaque(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImagenProveedor2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMini = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        txtUsuario = new rojeru_san.RSMTextFull();
        txtContraseña = new rojeru_san.RSMPassView();
        lblMensaje = new javax.swing.JLabel();
        btnIngresar = new rojeru_san.RSButtonRiple();
        lblImagenUsuario = new javax.swing.JLabel();
        lblImagenContraseña = new javax.swing.JLabel();
        lblImagenValidar = new javax.swing.JLabel();
        lblImagenValidar1 = new javax.swing.JLabel();

        lblImagenProveedor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(44, 189, 165));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMini.setBackground(new java.awt.Color(44, 189, 165));
        btnMini.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        btnMini.setForeground(new java.awt.Color(255, 255, 255));
        btnMini.setText("-");
        btnMini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiniActionPerformed(evt);
            }
        });
        jPanel2.add(btnMini, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 60, 40));

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Acceso");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        btnSalir.setBackground(new java.awt.Color(44, 189, 165));
        btnSalir.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("x");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 60, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 44));

        lblUsuario.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        lblUsuario.setText("Usuario");
        jPanel1.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 71, -1, -1));

        lblContraseña.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        lblContraseña.setText("Contraseña");
        jPanel1.add(lblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 161, -1, -1));

        txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
        txtUsuario.setText("MCJ");
        txtUsuario.setBordeColorFocus(new java.awt.Color(51, 51, 51));
        txtUsuario.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        txtUsuario.setPlaceholder("USUARIO");
        txtUsuario.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyReleased(evt);
            }
        });
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 106, -1, -1));

        txtContraseña.setForeground(new java.awt.Color(0, 0, 0));
        txtContraseña.setText("123");
        txtContraseña.setBordeColorFocus(new java.awt.Color(0, 0, 0));
        txtContraseña.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        txtContraseña.setPlaceholder("CONTRASEÑA");
        txtContraseña.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyReleased(evt);
            }
        });
        jPanel1.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 198, -1, -1));

        lblMensaje.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        lblMensaje.setForeground(new java.awt.Color(229, 0, 39));
        lblMensaje.setText("Validar");
        jPanel1.add(lblMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 249, -1, -1));

        btnIngresar.setBackground(new java.awt.Color(44, 189, 165));
        btnIngresar.setText("Iniciar Sesón");
        btnIngresar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        btnIngresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnIngresarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnIngresarKeyTyped(evt);
            }
        });
        jPanel1.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 322, 473, 48));

        lblImagenUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        jPanel1.add(lblImagenUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 40, 30));

        lblImagenContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        jPanel1.add(lblImagenContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 40, 30));

        lblImagenValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        lblImagenValidar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblImagenValidarKeyReleased(evt);
            }
        });
        jPanel1.add(lblImagenValidar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 40, 30));

        lblImagenValidar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Advertencia.png"))); // NOI18N
        jPanel1.add(lblImagenValidar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 40, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMiniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiniActionPerformed

    }//GEN-LAST:event_btnMiniActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        iniciarSesion();

    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyReleased
        if (txtUsuario.getText().equals("")) {
            lblUsuario.setForeground(new Color(44, 189, 165));
        } else {
            lblUsuario.setText("Usuario");
            lblUsuario.setForeground(new Color(44, 189, 165));
            lblImagenUsuario.setVisible(false);

        }
        lblMensaje.setText("");


    }//GEN-LAST:event_txtUsuarioKeyReleased

    private void txtContraseñaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyReleased
        String pass = new String(txtContraseña.getPassword());
        if (pass.equals("")) {
            lblContraseña.setForeground(new Color(44, 189, 165));

        } else {
            lblContraseña.setText("Contraseña");
            lblContraseña.setForeground(new Color(44, 189, 165));
            lblImagenContraseña.setVisible(false);
        }
        lblMensaje.setText("");
    }//GEN-LAST:event_txtContraseñaKeyReleased

    private void lblImagenValidarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblImagenValidarKeyReleased

    }//GEN-LAST:event_lblImagenValidarKeyReleased

    private void btnIngresarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIngresarKeyTyped

    }//GEN-LAST:event_btnIngresarKeyTyped

    private void btnIngresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIngresarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        }
    }//GEN-LAST:event_btnIngresarKeyPressed

    private void iniciarSesion() {
        if (txtUsuario.getText().equals("")) {
            lblImagenUsuario.setVisible(true);
            lblUsuario.setText("Ingrese el usuario");
            lblUsuario.setForeground(Color.RED);
            txtUsuario.requestFocus();
        } else {
            String pass = new String(txtContraseña.getPassword());
            if (pass.equals("")) {
                lblImagenContraseña.setVisible(true);
                lblContraseña.setText("Ingrese la contraseña");
                lblContraseña.setForeground(Color.RED);
                txtContraseña.requestFocus();
            } else {
                Object[] objects = usuario.login(txtUsuario.getText(), pass);
                List<ClsUsuarios> listUsuario = (List<ClsUsuarios>) objects[0];
                List<ClsCajas> listCaja = (List<ClsCajas>) objects[1];
                if (0 < listUsuario.size()) {
                    if (listUsuario.get(0).getRole().equals("Admin")) {
                        Menu sisten = new Menu(listUsuario, listCaja);
                        sisten.setVisible(true);
                        sisten.setExtendedState(MAXIMIZED_BOTH);
                        this.setVisible(false);
                    } else {
                        if (0 < listCaja.size()) {
                            Menu sisten = new Menu(listUsuario, listCaja);
                            sisten.setVisible(true);
                            sisten.setExtendedState(MAXIMIZED_BOTH);
                            this.setVisible(false);
                        } else {
                            lblMensaje.setText("No hay cajas disponibles");
                        }
                    }
                } else {
                    lblMensaje.setText("Usuario o Contraseña incorrecta");
//                    lblImagenValidar.setVisible(true);

                }

            }
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Acceso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.RSButtonRiple btnIngresar;
    private javax.swing.JButton btnMini;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblImagenContraseña;
    private javax.swing.JLabel lblImagenProveedor2;
    private javax.swing.JLabel lblImagenUsuario;
    public static javax.swing.JLabel lblImagenValidar;
    private javax.swing.JLabel lblImagenValidar1;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblUsuario;
    private rojeru_san.RSMPassView txtContraseña;
    private rojeru_san.RSMTextFull txtUsuario;
    // End of variables declaration//GEN-END:variables
}
