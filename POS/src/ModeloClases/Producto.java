/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloClases;

import Conexion.Conexion;
import Modelo.ClsBodega;
import Modelo.ClsProductos;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.Global.getDate;

/**
 *
 * @author edwin
 */
public class Producto {
       private Connection con = (Connection) new Conexion();
   private String Sql; 
   
   public void InsertarProductos(ClsProductos P){
       
       try {
           Statement st = con.createStatement();
           String sql=("INSERT INTO productos(Codigo,Producto,Precio,Descuento,Departamento,Categoria,Fecha)\n"+
                   "VALUES(?,?,?,?,?,?,?)");
           PreparedStatement prs =null;
           try {
               prs.setString(2, P.getCodigo());
               prs.setString(3,P.getProducto());
               prs.setDouble(4,P.getPrecio());
               prs.setDouble(5, P.getDescuento());
               prs.setString(6, P.getDepartamento());
               prs.setString(7, P.getCategoria());
               prs.setDate(8, Date.valueOf(P.getFecha()));
               prs.execute();
               JOptionPane.showMessageDialog(null, "Producto Registrado Exitosamente");
           } catch (Exception e) {
               System.out.println("ERROR AL REGISTRAR EL PRODUCTO"+e);
           }
           
           
       } catch (SQLException e) {
           System.out.println("ERROR"+e);
       }
       
   }
   
     public ArrayList<ClsProductos> MostrarProductos() {
            ArrayList<ClsProductos> ClsProductos = new ArrayList<>();
            try {
                CallableStatement cb = con.prepareCall("Select*from productos");
                ResultSet rs = cb.executeQuery();
                while (rs.next()) {
                    ClsProductos P = new ClsProductos();
                    P.setCodigo(rs.getString("Codigo"));
                    P.setProducto(rs.getString("Producto"));
                    P.setPrecio(rs.getDouble("Precio"));
                    P.setDescuento(rs.getDouble("Descuento"));
                    P.setDepartamento(rs.getString("Departamento"));
                    P.setCategoria(rs.getString("Categoria"));
//                    P.setFecha(rs.);
                    ClsProductos.add(P);
                    
                }
            } catch (Exception e) {
                System.out.println("ERROR EN LA CONSULTA"+e);
            }
            return ClsProductos;
        }
   
   
   
    
}
