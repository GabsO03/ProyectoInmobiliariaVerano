package DAO;

import Model.BusinessClases.Proyecto;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProyectoSQL {

    private String rutaFicheroLog;
    private PreparedStatement ps;
    public ProyectoSQL (String rutaFicheroLog) {
        this.rutaFicheroLog = rutaFicheroLog;
    }

    /**
     * Funcion para escribir el log
     * @param tipoTarea como una cadena
     * @param usuario como una cadena
     */
    public void escribirLog(String tipoTarea, String usuario) {
        try {
            //File log = new File(String.valueOf(this.getClass().getClassLoader().getResourceAsStream(rutaFicheroLog)));
            //TODO ARREGLAR RUTA
            String log = "C:\\Users\\pollo\\Programación T1DA_ejs\\Intellij\\ProyectoInmobiliaria\\src\\main\\resources\\FicherosDatosSistema\\log.txt";
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(log, true));
            bw.write(LocalDateTime.now()+ ";" + usuario + ";" + tipoTarea + "\n");
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero");
        } catch (IOException e) {
            System.out.println("No se puede leer el archivo");
        }
    }

    public Proyecto cargaProyecto (ResultSet rs) throws SQLException{
        int codigo = rs.getInt("codigo");
        String nombre = rs.getString("nombre");
        String imagen = rs.getString("imagen");
        String descripcion = rs.getString("descripcion");
        String tipo = rs.getString("tipo");
        String fechaInicio = rs.getString("fechaInicio");
        String fechaFin = rs.getString("fechaFin");
        double cantidadNecesaria = rs.getDouble("cantidadNecesaria");
        double cantidadFinanciada = rs.getDouble("cantidadFinanciada");
        boolean habilitado = rs.getBoolean("habilitado");
        int idGestor = rs.getInt("id_gestor");
        return new Proyecto(codigo, nombre, imagen, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria, cantidadFinanciada, habilitado, idGestor);
    }
    public Proyecto consigueProyecto (int codigo_proyecto, DAOManager daoManager) {
        String select = "SELECT * FROM proyectos where codigo = ?";
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setInt(1, codigo_proyecto);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return cargaProyecto(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Funcion para cargar en nuestro sistema los proyectos que se encuentran en la base de datos de manera ordenada
     * @param orderBy como una cadena
     * @param direccion como una cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los proyectos
     */
    public ArrayList<Proyecto> cargaProyectos(String orderBy, String direccion, DAOManager daoManager) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String select = "SELECT * FROM proyectos ORDER BY " + orderBy + " " + direccion;
        try {
            PreparedStatement preparedStatement = daoManager.getConn().prepareStatement(select);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Proyecto aux = cargaProyecto(resultSet);
                    proyectos.add(aux);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proyectos;
    }
    /**
     * Funcion para cargar en nuestro sistema los proyectos que se encuentran en la base de datos de manera ordenada
     * @param id como un entero
     * @param orderBy como una cadena
     * @param direccion como una cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los proyectos
     */
    public ArrayList<Proyecto> cargaProyectos(int id, String orderBy, String direccion, DAOManager daoManager) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String select = "SELECT * FROM proyectos WHERE `id_gestor` = ? ORDER BY " + orderBy + " " + direccion;
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Proyecto aux = cargaProyecto(resultSet);
                    proyectos.add(aux);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proyectos;
    }

    /**
     * Funcion para cargar en nuestro sistema los proyectos que se encuentran en la base de datos de manera ordenada y con un atributo específico
     * @param orderBy como una cadena
     * @param direccion como una cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los proyectos
     */
    public ArrayList<Proyecto> buscaProyectos(String orderBy, String direccion, String atributo, String valor, DAOManager daoManager) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String select = "SELECT * FROM proyectos WHERE " + atributo + " = ? ORDER BY " + orderBy + " " + direccion;
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setString(1, valor);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Proyecto aux = cargaProyecto(resultSet);
                    proyectos.add(aux);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proyectos;
    }

    /**
     * Funcion para cargar en nuestro sistema los proyectos que se encuentran en la base de datos de manera ordenada y con un atributo específico para un gestor específico
     * @param idGestor como un entero
     * @param orderBy como una cadena
     * @param direccion como una cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los proyectos
     */
    public ArrayList<Proyecto> buscaProyectos(int idGestor, String orderBy, String direccion, String atributo, String valor, DAOManager daoManager) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String select = "SELECT * FROM proyectos WHERE `id_gestor` = ? and " + atributo + " = ? ORDER BY " + orderBy + " " + direccion;
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setInt(1, idGestor);
            ps.setString(2, valor);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Proyecto aux = cargaProyecto(resultSet);
                    proyectos.add(aux);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proyectos;
    }

    public int cargaUltimoCodigo (DAOManager daoManager) {
        int codigo = 0;
        String select = "SELECT `codigo` FROM `proyectos` ORDER BY `codigo` DESC LIMIT 1;";
        try {
            ps = daoManager.getConn().prepareStatement(select);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    codigo = resultSet.getInt("codigo");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return codigo;
    }

    /**
     * Funcion para insertar un proyecto en la base de datos
     * @param proyecto como un objeto de la clase proyecto
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el proyecto correctamente o false si no
     * @throws SQLException
     */
    public boolean insertar(Proyecto proyecto, String username, int id_gestor, DAOManager daoManager) {
        String insertSentencia = "INSERT INTO `proyectos` (`nombre`, `imagen`, `descripcion`, `tipo`, `fechaInicio`, `fechaFin`, `cantidadNecesaria`, `cantidadFinanciada`, `habilitado`, `id_gestor`) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        try {
            String imagen = "data:image/jpeg;base64," + proyecto.getImagen();
            ps = daoManager.getConn().prepareStatement(insertSentencia);
            ps.setString(1, proyecto.getNombre());
            ps.setString(2, imagen);
            ps.setString(3, proyecto.getDescripcion());
            ps.setString(4, proyecto.getTipo());
            ps.setString(5, proyecto.getFechaInicio());
            ps.setString(6, proyecto.getFechaFin());
            ps.setDouble(7, proyecto.getCantidadNecesaria());
            ps.setDouble(8, proyecto.getCantidadFinanciada());
            ps.setBoolean(9, proyecto.isHabilitado());
            ps.setInt(10, id_gestor);
            if (ps.executeUpdate()>0) {
                escribirLog("Proyecto " + proyecto.getNombre() + " creado correctamente", username);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    /**
     * Funcion para actualizar un proyecto en la base de datos
     * @param username como cadena
     * @param atributoCambiar como cadena
     * @param nuevoValor como cadena
     * @param codigo como entero
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido modificar el proyecto correctamente o false si no
     * @throws SQLException
     */
    public boolean update (String atributoCambiar, String nuevoValor, int codigo, DAOManager daoManager, String username) {
        String update = "UPDATE `proyectos` SET `" + atributoCambiar + "` = ? WHERE `proyectos`.`codigo` = ?;";
        try {
            ps = daoManager.getConn().prepareStatement(update);
            ps.setString(1, nuevoValor);
            ps.setInt(2, codigo);
            if (ps.executeUpdate()>0) {
                escribirLog("Proyecto " + codigo + " actualizado - " + atributoCambiar, username);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Funcion para poder eliminar un proyecto de nuestra base de datos
     * @param codigo como entero
     * @param daoManager como una instancia de la clase DAOManager
     * @param username como cadena
     * @return true si se ha podido eliminar el proyecto correctamente o false si no
     * @throws SQLException
     */
    public boolean delete(int codigo, DAOManager daoManager, String username) {
        String sentencia = "DELETE FROM `proyectos` WHERE `codigo` = ?";
        try {
            ps = daoManager.getConn().prepareStatement(sentencia);
            ps.setInt(1, codigo);
            if (ps.executeUpdate()>0) {
                escribirLog("Proyecto " + codigo + " eliminado", username);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
