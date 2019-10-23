package ModeloClases;

import Conexion.Consulta;
import Modelo.ClsDepartamentos;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Departamento extends Consulta {

    private String sql;
    private Object[] object;
    private DefaultTableModel modelo;
    private List<ClsDepartamentos> departamentoFilter;

    public void inseratDepartamentoCategoria(String dptocat, int idDpto, String type) {
        if (type.equals("dpto")) {
            sql = "INSERT INTO Departamentos (Departamento) VALUES(?)";
            object = new Object[]{dptocat};
            Insertar(sql, object);

        } else {
            sql = "INSERT INTO Categorias(Categoria,IdDpto)VALUES(?,?)";
            object = new Object[]{dptocat, idDpto};
            Insertar(sql, object);
        }

    }

    public void buscarDepartamento(JTable table, String campo) {
        String[] registros = new String[2];
        String[] titulos = {"ID", "Departamento"};
        modelo = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            departamentoFilter = departamentos().stream().collect(Collectors.toList());

        } else {
            departamentoFilter = departamentos().stream()
                    .filter(d -> d.getDepartamento().startsWith(campo))
                    .collect(Collectors.toList());
        }
        departamentoFilter.forEach(item -> {
            registros[0] = String.valueOf(item.getIDpto());
            registros[1] = item.getDepartamento();
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

    public void actualizarDepartamentoCategoria(String dptocat, int idDpto, int idCat, String type) {
        if (type.equals("dpto")) {
            sql = "UPDATE departamentos SET Departamento = ? WHERE IdDpto =" + idDpto;
            object = new Object[]{dptocat};
            Actualizar(sql, object);
        }
    }

}
