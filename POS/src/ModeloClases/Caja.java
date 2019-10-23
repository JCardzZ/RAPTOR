package ModeloClases;

import Conexion.Consulta;
import Modelo.ClsCajas;
import java.util.List;
import java.util.stream.Collectors;

public class Caja extends Consulta {

    private String sql;
    private Object[] object;
    private List<ClsCajas> listCajas;

    public List<ClsCajas> getCaja() {
        listCajas = cajas().stream()
                .filter(c -> c.isEstado() == true)
                .collect(Collectors.toList());
        return listCajas;
    }

    public void insertarCajaRegistro(int idUsuario, String nombre, String apellido,
            String usuario, String rol, int idCaja, int Caja, boolean estado,
            String hora, String fecha) {
        sql = "INSERT INTO cajas_registros(IdUsuario,Nombre, Apellido,Usuario"
                + ",Role,IdCaja,Caja,Estado,Hora,Fecha)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        object = new Object[]{idUsuario, nombre, apellido, usuario, rol, idCaja, Caja, estado, hora, fecha};
        Insertar(sql, object);

    }

    public void actualizarCaja(int idCaja, boolean estado) {
        sql = "UPDATE cajas SET Estado = ? WHERE IdCaja =" + idCaja;
        object = new Object[]{estado};
        Actualizar(sql, object);
    }
}
