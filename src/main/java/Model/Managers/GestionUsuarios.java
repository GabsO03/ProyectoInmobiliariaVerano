package Model.Managers;

import DAO.DAOManager;
import DAO.UsuarioSQL;
import Model.BusinessClases.Admin;
import Model.BusinessClases.Gestor;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Usuario;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static Model.Biblioteca.FuncionesCadenas.cypherPassword;

public class GestionUsuarios {

    //La clave será el nombre de usuario y el valor el propio usuario
    private HashMap<String, Usuario> hashMapUsuarios;
    private Properties iniciosSesion;
    private UsuarioSQL usuarioSQL;
    private int lastId;

    public GestionUsuarios(String rutaFicheroLog, String ubicacionLogins) {
        this.hashMapUsuarios = new HashMap<>();
        this.iniciosSesion = new Properties();
        this.usuarioSQL = new UsuarioSQL(rutaFicheroLog);
        cargarIniciosSesionUsuarios(ubicacionLogins);
    }

    public void cargarUsuarios(DAOManager daoManager) {
        hashMapUsuarios.putAll(usuarioSQL.cargaAdmins(daoManager));
        hashMapUsuarios.putAll(usuarioSQL.cargaGestores(daoManager));
        hashMapUsuarios.putAll(usuarioSQL.cargaInversores(daoManager));
    }

    public void buscarUsuarios (String tipo_nombre, String name, String tipo, String estado, String lastLogin, DAOManager daoManager) {
        HashMap<String, Usuario> aux = usuarioSQL.buscarUsuarios(tipo_nombre, name, tipo, estado, daoManager);
        for (Map.Entry user : aux.entrySet()) {
            Usuario usuario = (Usuario) user.getValue();
            LocalDate lastSession = (LocalDate) iniciosSesion.get(usuario.getId());
            switch (lastLogin) {
                case "today" -> {
                    if (lastSession == LocalDate.now()) hashMapUsuarios.put(usuario.getUsername(), usuario);
                }
                case "month" -> {
                    for (int i = 1; i < 32; i++) {
                        if (lastSession == LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i)) hashMapUsuarios.put(usuario.getUsername(), usuario);
                    }
                }
                case "year" -> {
                    for (int i = 1; i < 13 ; i++) {
                        if (lastSession == LocalDate.of(LocalDate.now().getYear(), i, LocalDate.now().getDayOfMonth())) hashMapUsuarios.put(usuario.getUsername(), usuario);
                    }
                }
                case "always" -> {
                    hashMapUsuarios.putAll(aux);
                }
            }
        }
    }

    /**
     * Funcion para devolver un objeto de la clase Usuario
     * @param username como String
     * @return un objeto de la clase Usuario
     */
    public Usuario devuelveUsuario(String username) {
        return hashMapUsuarios.get(username);
    }

    /**
     * Funcion para insertar un nuevo usuario gestor
     *
     * @param nombre      como cadena
     * @param user        como cadena
     * @param contrasenia como cadena
     * @param email       como cadena
     * @param daoManager como una instancia de la clase DAOManager
     */
    public boolean insertarUsuarioGestor(String nombre, String user, String contrasenia, String email, DAOManager daoManager){
        Gestor aux = new Gestor(nombre, user, String.valueOf(cypherPassword(contrasenia)), email);
        boolean correcto;
        correcto = usuarioSQL.insertar(aux, daoManager);
        return correcto;
    }

    /**
     * Funcion para insertar un nuevo admin
     *
     * @param nombre      como cadena
     * @param user        como cadena
     * @param contrasenia como cadena
     * @param email       como cadena
     * @param daoManager como una instancia de la clase DAOManager
     */
    public void insertarUsuarioAdmin(String nombre, String user, String contrasenia, String email, DAOManager daoManager) throws SQLException {
        boolean correcto = usuarioSQL.insertar(new Admin(++lastId, nombre, user, contrasenia, email), daoManager);
        hashMapUsuarios.put(user, new Admin(lastId, nombre, user, contrasenia, email));
    }

    /**
     * Funcion para insertar un nuevo inversor
     *
     * @param nombre      como cadena
     * @param user        como cadena
     * @param contrasenia como cadena
     * @param email       como cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario correctamente o false si no
     */
    public boolean insertarUsuarioInversor(String nombre, String user, String contrasenia, String email, DAOManager daoManager) {
        Inversor aux = new Inversor(nombre, user, String.valueOf(cypherPassword(contrasenia)), email);
        boolean correcto;
        correcto = usuarioSQL.insertar(aux, daoManager);
        return correcto;
    }

    /**
     * Funcion para modificar un usuario que ya existe
     * @param atributo como un entero
     * @param nuevoValor como cadena
     * @param userName    como cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario correctamente o false si no
     */
    public boolean modificarUsuario(String atributo, String nuevoValor, String userName, DAOManager daoManager) {
        int id = devuelveUsuario(userName).getId();
        boolean correcto;
        if (atributo.equals("password")) correcto = usuarioSQL.updateUsuarios(atributo, String.valueOf(cypherPassword(nuevoValor)), userName, id, daoManager);
        else correcto = usuarioSQL.updateUsuarios(atributo, nuevoValor, userName, id, daoManager);
        if (correcto) {
            if (atributo.equals("username")) buscarUsuarios("userName", nuevoValor, "Usuario", "0", "always", daoManager);
            else buscarUsuarios("userName", userName, "Usuario", "0", "always", daoManager);
        }
        return correcto;
    }


    /**
     * Funcion para comprobar si el nombre del usuario y la contraseña corresponden con el usuario que inicia sesion
     * @param username    como cadena
     * @param contrasenia como cadena
     * @return true si si corresponde o false si no corresponde
     */
    public boolean correspondeUsuyContrasenia(String username, String contrasenia, DAOManager daoManager) {
        if (existeNombreUsuario(username, daoManager)) {
            return hashMapUsuarios.get(username).getContrasenia().equals(String.valueOf(cypherPassword(contrasenia)));
        }
        return false;
    }

    /**
     * Funcion que comprueba si el nombre de usuario introducido ya existe o no
     * @param username como cadena
     * @return true or false dependiendo de si existe o no el nombre del usuario
     */
    public boolean existeNombreUsuario(String username, DAOManager daoManager) {
        boolean existe;
        buscarUsuarios("userName", username, "Usuario", "0", "always", daoManager);
        existe = hashMapUsuarios.containsKey(username);
        if (!existe) {
            buscarUsuarios("userName", username, "Usuario", "1", "always", daoManager);
            existe = hashMapUsuarios.containsKey(username);
        }
        return existe;
    }

    /**
     * Funcion para averiguar la clase a la que pertenece un objeto
     *
     * @param userName como cadena
     * @return una cadena con el nombre de la clase
     */
    public String averiguarClase(String userName) {
        Usuario aux = devuelveUsuario(userName);
        return aux.getClass().getSimpleName();
    }

    /**
     * Funcion para bloquear o desbloqueaar usuarios
     *
     * @param username como cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido bloquear o desbloquear al usuario correctamente o false si no
     */
    public void bloquearDesbloquearUsuario(String username, DAOManager daoManager) {
        int id = devuelveUsuario(username).getId();
        String clase = hashMapUsuarios.get(username).getClass().getSimpleName();
        try {
            boolean right;
            switch (clase) {
                case "Inversor" -> {
                    Inversor aux = (Inversor) hashMapUsuarios.get(username);
                    if (aux.isBloqueado()) {
                        right = usuarioSQL.updateInversores("bloqueado", "0", username, id, daoManager);
                        if (right) aux.desbloqueo();
                    }
                    else {
                        right = usuarioSQL.updateInversores("bloqueado", "1", username, id, daoManager);
                        if (right) aux.bloqueo();
                    }
                }
                case "Gestor" -> {
                    Gestor aux = (Gestor) hashMapUsuarios.get(username);
                    if (aux.isBloqueado()) {
                        right = usuarioSQL.updateGestores("bloqueado", "0", username, id, daoManager);
                        if (right) aux.desbloqueo();
                    }
                    else {
                        right = usuarioSQL.updateGestores("bloqueado", "1", username, id, daoManager);
                        if (right) aux.bloqueo();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funcion para cargar los inicios de sesion que realizan los usuarios
     * @param ubicacionLogins como una cadena
     */
    public void cargarIniciosSesionUsuarios(String ubicacionLogins) {
        this.iniciosSesion = new Properties();
        try {
            InputStream rutaLoginsCompleta = this.getClass().getClassLoader().getResourceAsStream(ubicacionLogins);
            if (rutaLoginsCompleta!=null) this.iniciosSesion.load(rutaLoginsCompleta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Guarda los inicios de sesion de los distintos usuarios
     * @param ubicacionLogins como una cadena
     */
    public void guardarIniciosSesionUsuarios(String ubicacionLogins) {
        try {
            //TODO ARREGLAR RUTA
            String properties = "C:\\Users\\pollo\\Programación T1DA_ejs\\Intellij\\ProyectoInmobiliaria\\src\\main\\resources\\FicherosDatosSistema\\LastLogins.properties";
            FileOutputStream fos = new FileOutputStream(properties);
            iniciosSesion.store(fos, "Fichero de configuración");
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza el ultimo inicio de sesión de un usuario en especifico
     * @param id_usuario como un entero
     */
    public void actualizarUltimoInicioSesionUsuario(int id_usuario) {
        iniciosSesion.setProperty(String.valueOf(id_usuario), String.valueOf(LocalDateTime.now()));
    }

    /**
     * Recupera el ultimo inicio de sesión de un usuario en especifico
     * @param id_usuario como un entero
     * @return una cadena con el username del usuario
     */
    public LocalDateTime recuperarUltimoInicioSesionUsuario(int id_usuario) {
        return LocalDateTime.parse(iniciosSesion.getProperty(String.valueOf(id_usuario), String.valueOf(LocalDateTime.now())));
    }

    /**
     * Función para aumentarle el saldo al inversor
     * @param username como cadena
     * @param monto como double
     * @param daoManager como instancia de la clase DAOManager
     * @return
     */
    public boolean agregarMontoInversor (String username, double monto, DAOManager daoManager) {
        Inversor aux = (Inversor) hashMapUsuarios.get(username);
        boolean right = usuarioSQL.updateInversores("saldo", String.valueOf(aux.getSaldo() + monto), username, aux.getId(), daoManager);
        if (right) aux.setSaldo(aux.getSaldo() + monto);
        return right;
    }

    /**
     * Funcion que se utiliza para cuando el inversor realiza una paga en un proyecto, es decir, invierte
     * @param username como cadena
     * @param cantidad como double
     * @param dao como una instancia de la clase DAOManager
     * @return true si se ha podido insertar el usuario correctamente o false si no
     */
    public boolean inversorPaga (String username, double cantidad, DAOManager dao) {
        Inversor aux = (Inversor) hashMapUsuarios.get(username);
        boolean right = false;
        if (aux.puedePagarInversor(cantidad)) {
            return usuarioSQL.updateInversores("saldo", String.valueOf(aux.getSaldo() - cantidad), username, aux.getId(), dao);
        }
        return right;
    }
    public HashMap<String, Usuario> getHashMapUsuarios() {
        return hashMapUsuarios;
    }
}
