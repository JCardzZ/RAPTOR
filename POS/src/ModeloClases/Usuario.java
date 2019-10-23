package ModeloClases;

import Conexion.Consulta;
import Modelo.ClsCajas;
import Modelo.ClsUsuarios;
import static Vista.Acceso.lblImagenValidar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario extends Consulta {

    private Calendar calendario = new GregorianCalendar();
    private Caja caja = new Caja();
    private List<ClsUsuarios> listUsuarios, listUsuario;
    private List<ClsCajas> listCaja;

    public Usuario() {
        listUsuarios = new ArrayList<ClsUsuarios>();
        listCaja = new ArrayList<ClsCajas>();

    }


 public Object[] login(String usuario, String password) {
        listUsuarios.clear();
        listUsuarios = Usuarios().stream()
                .filter(P -> P.getUsuario().equals(usuario))
                .collect(Collectors.toList());
        if (0 < listUsuarios.size()) {
            try {
                String pass = Encriptar.Decript(listUsuarios.get(0).getPassword());
                if (pass.equals(password)) {
                    listUsuario = listUsuarios;
                    int idUsuario = listUsuarios.get(0).getIdUsuario();
                    String nombre = listUsuarios.get(0).getNombre();
                    String apellido = listUsuarios.get(0).getApellido();
                    String user = listUsuarios.get(0).getUsuario();
                    String role = listUsuarios.get(0).getRole();
                    if (role.equals("Admin")) {
                        caja.insertarCajaRegistro(idUsuario, nombre, apellido, user, role, 0, 0, false, new Calendario().getHora(), new Calendario().getFecha());
                    } else {
                        listCaja = caja.getCaja();
                        int idCaja = listCaja.get(0).getIdCaja();
                        int cajas = listCaja.get(0).getCaja();
                        boolean estado = listCaja.get(0).isEstado();
                        caja.actualizarCaja(idCaja, false);
                        caja.insertarCajaRegistro(idUsuario, nombre, apellido, user, role, idCaja, cajas, estado, new Calendario().getHora(), new Calendario().getFecha());
                    }
                }
            } catch (Exception ex) {

            }
        }else{
          
           
             listUsuario = new ArrayList<ClsUsuarios>();
        
        }
        Object[] objects = {listUsuario, listCaja};
        return objects;
    }
}