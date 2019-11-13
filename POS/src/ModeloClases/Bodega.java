
package ModeloClases;
import Conexion.Conexion;
import Modelo.ClsBodega;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.time.LocalDate;
/**
 *
 * @author Stephany-AG
 */
public class Bodega {
   private Connection con = (Connection) new Conexion();
   private String Sql; 
   
       public void Almacenar(ClsBodega bd){
          try {
        Statement sl = con.createStatement();
        String sql =("INSERT INTO bodega (Id,IdProducto,Codigo,Existencia,Fecha)\n" + "VALUES (?,?,?,?,?)");
        PreparedStatement ps =null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt (1, bd.getIdBodega());
            ps.setInt(2, bd.getIdProducto());
            ps.setString(3, bd.getCodigo());
            ps.setInt(4, bd.getExistencia());
            ps.setDate(5, Date.valueOf(bd.getFecha()));
          ps.executeUpdate();
          System.out.println("Registro Exitoso!! de la bodega");
        }catch(SQLException ex ){
        JOptionPane.showMessageDialog(null, ex);
    } 
        
    } catch(SQLException ex ){
            JOptionPane.showMessageDialog(null, ex);
    } 
    }
           public ArrayList<ClsBodega> MostrarBodega() {
            ArrayList<ClsBodega> ClsBodega = new ArrayList<>();
            try {
                CallableStatement cb = con.prepareCall("Select*from Bodega");
                ResultSet rs = cb.executeQuery();
                while (rs.next()) {
                    ClsBodega e = new ClsBodega();
                    e.setIdBodega(rs.getInt("Id"));
                    e.setIdProducto(rs.getInt("IdProducto"));
                    e.setCodigo(rs.getString("Codigo"));
                    e.setExistencia(rs.getInt("Existencia"));
                 //  e.getFecha(rs.getLocalDate("Fecha"));
                    ClsBodega.add(e);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en la consulta!!" + e);
            }
            return ClsBodega;
        }
}
