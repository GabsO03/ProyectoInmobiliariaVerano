package DAO;

import Model.BusinessClases.Proyecto;
import Model.Managers.GestionApp;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            String rutaFicheroCompleta = this.getClass().getClassLoader().getResource(rutaFicheroLog).toString();
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(rutaFicheroCompleta, true));
            bw.write(LocalDateTime.now()+ ";" + usuario + ";" + tipoTarea + "\n");
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero");
        } catch (IOException e) {
            System.out.println("No se puede leer el archivo");
        }
    }
    /**
     * Funcion para cargar en nuestro sistema los proyectos que se encuentran en la base de datos
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los proyectos
     */
    public ArrayList<Proyecto> cargaProyectos(DAOManager daoManager) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String select = "SELECT * FROM proyectos";
        try {
            PreparedStatement preparedStatement = daoManager.getConn().prepareStatement(select);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    String tipo = resultSet.getString("tipo");
                    String fechaInicio = resultSet.getString("fechaInicio");
                    String fechaFin = resultSet.getString("fechaFin");
                    double cantidadNecesaria = resultSet.getDouble("cantidadNecesaria");
                    double cantidadFinanciada = resultSet.getDouble("cantidadFinanciada");
                    Proyecto aux = new Proyecto(codigo, nombre, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria, cantidadFinanciada, true);
                    proyectos.add(aux);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proyectos;
    }
    /**
     * Funcion para cargar en nuestro sistema los proyectos que se encuentran en la base de datos
     * @param id como un entero
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los proyectos
     */
    public ArrayList<Proyecto> cargaProyectos(int id, DAOManager daoManager) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String select = "SELECT * FROM proyectos WHERE `id_gestor` LIKE ?";
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    String tipo = resultSet.getString("tipo");
                    String fechaInicio = resultSet.getString("fechaInicio");
                    String fechaFin = resultSet.getString("fechaFin");
                    double cantidadNecesaria = resultSet.getDouble("cantidadNecesaria");
                    double cantidadFinanciada = resultSet.getDouble("cantidadFinanciada");
                    Proyecto aux = new Proyecto(codigo, nombre, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria, cantidadFinanciada, true);
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
                    int codigo = resultSet.getInt("codigo");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    String tipo = resultSet.getString("tipo");
                    String fechaInicio = resultSet.getString("fechaInicio");
                    String fechaFin = resultSet.getString("fechaFin");
                    double cantidadNecesaria = resultSet.getDouble("cantidadNecesaria");
                    double cantidadFinanciada = resultSet.getDouble("cantidadFinanciada");
                    Proyecto aux = new Proyecto(codigo, nombre, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria, cantidadFinanciada, true);
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
        String select = "SELECT * FROM proyectos ORDER BY " + orderBy + " " + direccion + " WHERE `id_gestor` LIKE ?";
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    String tipo = resultSet.getString("tipo");
                    String fechaInicio = resultSet.getString("fechaInicio");
                    String fechaFin = resultSet.getString("fechaFin");
                    double cantidadNecesaria = resultSet.getDouble("cantidadNecesaria");
                    double cantidadFinanciada = resultSet.getDouble("cantidadFinanciada");
                    Proyecto aux = new Proyecto(codigo, nombre, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria, cantidadFinanciada, true);
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
        String insertSentencia = "INSERT INTO `proyectos` (`nombre`, `descripcion`, `tipo`, `fechaInicio`, `fechaFin`, `cantidadNecesaria`, `cantidadFinanciada`, `habilitado`, `id_gestor`) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        try {
            ps = daoManager.getConn().prepareStatement(insertSentencia);
            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setString(3, proyecto.getTipo());
            ps.setString(4, proyecto.getFechaInicio());
            ps.setString(5, proyecto.getFechaFin());
            ps.setDouble(6, proyecto.getCantidadNecesaria());
            ps.setDouble(7, proyecto.getCantidadFinanciada());
            ps.setBoolean(8, proyecto.isHabilitado());
            ps.setInt(9, id_gestor);
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
        String sentencia = "DELETE FROM `proyectos` WHERE `codigo`= ?";
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
