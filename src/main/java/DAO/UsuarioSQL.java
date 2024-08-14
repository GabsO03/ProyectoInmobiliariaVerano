package DAO;

import Model.BusinessClases.Admin;
import Model.BusinessClases.Gestor;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Usuario;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class UsuarioSQL {

    private String rutaFicheroLog;
    private PreparedStatement ps;

    public UsuarioSQL(String rutaFicheroLog){
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

    /**
     * Funcion para cargar en nuestro sistema los usuarios inversores que se encuentran en la base de datos
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los usuarios inversores
     */
    public HashMap<String, Inversor> cargaInversores(DAOManager daoManager) {
        HashMap<String, Inversor> inversores = new HashMap<>();
        String select = "SELECT usuarios.id, usuarios.userName, usuarios.name, usuarios.password, usuarios.email, inversores.saldo, inversores.bloqueado FROM `usuarios` JOIN `inversores` WHERE usuarios.id = inversores.id_usuario;";
        try {
            PreparedStatement preparedStatement = daoManager.getConn().prepareStatement(select);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String userName = resultSet.getString("userName");
                    String name = resultSet.getString("name");
                    String passWord =  resultSet.getString("password");
                    String email = resultSet.getString("email");
                    double saldo = resultSet.getDouble("saldo");
                    boolean bloqueado = resultSet.getBoolean("bloqueado");
                    inversores.put(userName, new Inversor(id, name, userName, passWord, email, saldo, bloqueado));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inversores;
    }
    /**
     * Funcion para cargar en nuestro sistema los usuarios gestores que se encuentran en la base de datos
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los usuarios gestores
     */
    public HashMap<String, Gestor> cargaGestores(DAOManager daoManager) {
        HashMap<String, Gestor> gestors = new HashMap<>();
        String select = "SELECT usuarios.id, usuarios.userName, usuarios.name, usuarios.password, usuarios.email, gestores.bloqueado FROM `usuarios` JOIN `gestores` WHERE usuarios.id = gestores.id_usuario;";
        try {
            PreparedStatement preparedStatement = daoManager.getConn().prepareStatement(select);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String userName = resultSet.getString("userName");
                    String name = resultSet.getString("name");
                    String passWord =  resultSet.getString("password");
                    String email = resultSet.getString("email");
                    boolean bloqueado = resultSet.getBoolean("bloqueado");
                    gestors.put(userName, new Gestor(id, name, userName, passWord, email, bloqueado));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gestors;
    }
    /**
     * Funcion para cargar en nuestro sistema los usuarios administradores que se encuentran en la base de datos
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todos los usuarios administradores
     */
    public HashMap<String, Admin> cargaAdmins(DAOManager daoManager) {
        HashMap<String, Admin> admins = new HashMap<>();
        String select = "SELECT usuarios.id, usuarios.userName, usuarios.name, usuarios.password, usuarios.email FROM `usuarios` JOIN `admins` WHERE usuarios.id = admins.id_usuario;";
        try {
            PreparedStatement preparedStatement = daoManager.getConn().prepareStatement(select);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String userName = resultSet.getString("userName");
                    String name = resultSet.getString("name");
                    String passWord =  resultSet.getString("password");
                    String email = resultSet.getString("email");
                    admins.put(userName, new Admin(id, name, userName, passWord, email));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }

    public int cargaUltimoCodigo (DAOManager daoManager) {
        int id = 0;
        String select = "SELECT `id` FROM `usuarios` ORDER BY `id` DESC LIMIT 1;";
        try {
            ps = daoManager.getConn().prepareStatement(select);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * Función para insertar un usuarios en la tabla de usuarios
     * @param usuario incluye cualquier obeto que herede de la clase usuario
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario correctamente o false si no
     */
    public boolean insertarUsuario (Usuario usuario, DAOManager daoManager) {
        String insertUsuarios = "INSERT INTO `usuarios`(`userName`, `name`, `password`, `email`) VALUES (?,?,?,?)";
        try {
            ps = daoManager.getConn().prepareStatement(insertUsuarios);
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getName());
            ps.setString(3, usuario.getContrasenia());
            ps.setString(4, usuario.getEmail());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funcion para insertar un usuario inversor en la tabla `inversores`
     * @param inversor como un objeto de la clase inversor
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario inversor correctamente o false si no
     */
    public boolean insertar(Inversor inversor, DAOManager daoManager) {
        boolean primerInsert = insertarUsuario(inversor, daoManager);
        String insertInversores = "INSERT INTO `inversores`(`id_usuario`, `userName`) VALUES (?,?)";
        try {
            int lastId = cargaUltimoCodigo(daoManager);
            ps = daoManager.getConn().prepareStatement(insertInversores);
            ps.setInt(1, lastId);
            ps.setString(2, inversor.getUsername());
            boolean segundoInsert = ps.executeUpdate()>0;
            boolean correcto= primerInsert && segundoInsert;
            if (correcto){
                escribirLog("Usuario inversor insertado - " + inversor.getUsername(), "system");
            }
            return correcto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Funcion para insertar un usuario gestor en la tabla `gestores`
     * @param gestor como un objeto de la clase gestor
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario gestor correctamente o false si no
     */
    public boolean insertar(Gestor gestor, DAOManager daoManager) {
        boolean primerInsert = insertarUsuario(gestor, daoManager);
        String insertGestores = "INSERT INTO `gestores` (`id_usuario`, `userName`) VALUES (?,?)";
        try {
            int lastId = cargaUltimoCodigo(daoManager);
            ps = daoManager.getConn().prepareStatement(insertGestores);
            ps.setInt(1, lastId);
            ps.setString(2, gestor.getUsername());

            boolean segundoInsert = ps.executeUpdate()>0;
            boolean correcto = primerInsert && segundoInsert;
            if (correcto){
                escribirLog("Usuario gestor insertado - " + gestor.getId(), "system");
            }
            return correcto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Funcion para insertar un usuario administrador en la tabla `admins`
     * @param admin como un objeto de la clase admin
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario administrador correctamente o false si no
     */
    public boolean insertar(Admin admin, DAOManager daoManager) {
        boolean primerInsert = insertarUsuario(admin, daoManager);
        String insertAdmins = "INSERT INTO `admins` (`id_usuario`, `userName`) VALUES ?";
        try {
            ps = daoManager.getConn().prepareStatement(insertAdmins);
            ps.setInt(1, admin.getId());
            ps.setString(2, admin.getUsername());

            boolean segundoInsert = ps.executeUpdate()>0;
            boolean correcto = primerInsert && segundoInsert;
            if (correcto){
                escribirLog("Usuario administrador insertado - " + admin.getId(), "system");
            }
            return correcto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funcion para modificar un usuario en la base de datos
     * @param atributoCambiar como cadena
     * @param nuevoValor como cadena
     * @param userName como cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido modificar el usuario correctamente o false si no
     */
    public boolean updateUsuarios(String atributoCambiar, String nuevoValor, String userName, int id, DAOManager daoManager) {
        String updateUsuario = "UPDATE `usuarios` SET `" + atributoCambiar + "` = ? WHERE `usuarios`.`id` = ?;";
        try {
            ps = daoManager.getConn().prepareStatement(updateUsuario);
            ps.setString(1, nuevoValor);
            ps.setInt(2, id);
            boolean correcto = ps.executeUpdate()>0;
            if (correcto) escribirLog("Usuario modificado - " + atributoCambiar, userName);
            return correcto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Funcion para modificar un usuario inversor en la base de datos
     * @param atributoCambiar como cadena
     * @param nuevoValor como cadena
     * @param userName como cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido modificar el usuario inversor correctamente o false si no
     */
    public boolean updateInversores(String atributoCambiar, String nuevoValor, String userName, int id, DAOManager daoManager) {
        String updateUsuario = "UPDATE `inversores` SET `" + atributoCambiar + "` = ? WHERE `inversores`.`id_usuario` = ?;";
        try {
            ps = daoManager.getConn().prepareStatement(updateUsuario);
            ps.setString(1, nuevoValor);
            ps.setInt(2, id);
            boolean correcto = ps.executeUpdate()>0;
            if (correcto) escribirLog("Usuario inversor modificado - " + atributoCambiar, userName);
            return correcto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Funcion para modificar un usuario gestor en la base de datos
     * @param atributoCambiar como cadena
     * @param nuevoValor como cadena
     * @param userName como cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido modificar el usuario gestor correctamente o false si no
     */
    public boolean updateGestores(String atributoCambiar, String nuevoValor, String userName, int id, DAOManager daoManager) throws SQLException {
        String updateUsuario = "UPDATE `gestores` SET `" + atributoCambiar + "` = ? WHERE `gestores`.`id_usuario` = ?;";
        try {
            ps = daoManager.getConn().prepareStatement(updateUsuario);
            ps.setString(1, nuevoValor);
            ps.setInt(2, id);
            boolean correcto = ps.executeUpdate()>0;
            if (correcto) escribirLog("Usuario gestor modificado - " + atributoCambiar, userName);
            return correcto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        //POR SI ACASO NOS HACE FALTA BORRAR USUARIOS 😀
   /* public boolean deleteInversor(String userName, DAOManager daoManager) throws SQLException {
        String sentencia = "DELETE FROM `inversores` WHERE userName = " + userName +
                "; DELETE FROM `usuarios` WHERE userName = " + userName;
        return ejecutaSentencia(sentencia, daoManager);
    }
    public boolean deleteGestor(String userName, DAOManager daoManager) throws SQLException {
        String sentencia = "DELETE FROM `gestores` WHERE userName = " + userName +
                "; DELETE FROM `usuarios` WHERE userName = " + userName;
        return ejecutaSentencia(sentencia, daoManager);
    }
    public boolean deleteAdmin(String userName, DAOManager daoManager) throws SQLException {
        String sentencia = "DELETE FROM `admins` WHERE userName = " + userName +
                "; DELETE FROM `usuarios` WHERE userName = " + userName;
        return ejecutaSentencia(sentencia, daoManager);
    }*/
}
