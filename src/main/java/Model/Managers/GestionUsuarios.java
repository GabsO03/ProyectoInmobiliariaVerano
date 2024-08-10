package Model.Managers;

import DAO.DAOManager;
import DAO.UsuarioSQL;
import Model.BusinessClases.Admin;
import Model.BusinessClases.Gestor;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Usuario;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
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


    public GestionUsuarios(DAOManager daoManager, String rutaFicheroLog, String ubicacionLogins) {
        this.hashMapUsuarios = new HashMap<>();
        this.iniciosSesion = new Properties();
        this.usuarioSQL = new UsuarioSQL(rutaFicheroLog);
        cargarUsuarios(daoManager);
        cargarIniciosSesionUsuarios(ubicacionLogins);
    }

    public void cargarUsuarios(DAOManager daoManager) {
        hashMapUsuarios.putAll(usuarioSQL.cargaAdmins(daoManager));
        hashMapUsuarios.putAll(usuarioSQL.cargaGestores(daoManager));
        hashMapUsuarios.putAll(usuarioSQL.cargaInversores(daoManager));
    }

    /**
     * Muestra todos los usuarios del programa
     */
    public String devuelveListaUsuarios() {
        int i = 1;
        String cadena = "Lista de usuarios:\n===============================";
        for (Map.Entry<String, Usuario> entry : hashMapUsuarios.entrySet()) {
            Usuario u = entry.getValue();
            if (u.getClass().getSimpleName().equalsIgnoreCase("Gestor")) {
                Gestor aux = (Gestor) u;
                cadena = cadena.concat("\n" + i++ + ") " + aux + " - Gestor");
            }
            if (u.getClass().getSimpleName().equalsIgnoreCase("Inversor")) {
                Inversor aux = (Inversor) u;
                cadena = cadena.concat("\n" + i++ + ") " + aux + " - Inversor");
            }
        }
        return cadena;
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
        if (correcto) {
            hashMapUsuarios.put(user,aux);
            lastId = usuarioSQL.cargaUltimoCodigo(daoManager);
            hashMapUsuarios.get(user).setId(lastId);
        }
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
        if (correcto){
            hashMapUsuarios.put(user, aux);
            lastId = usuarioSQL.cargaUltimoCodigo(daoManager);
            hashMapUsuarios.get(user).setId(lastId);
        }
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
        boolean correcto = false;
        switch (atributo) {
            case "name" -> {
                correcto = usuarioSQL.updateUsuarios(atributo, nuevoValor, userName, id, daoManager);
                if (correcto) hashMapUsuarios.get(userName).setName(nuevoValor);
            }
            case "username" -> {
                correcto = usuarioSQL.updateUsuarios(atributo, nuevoValor, userName, id, daoManager);
                if (correcto) hashMapUsuarios.get(userName).setUsername(nuevoValor);
            }
            case "email" -> {
                correcto =  usuarioSQL.updateUsuarios(atributo, nuevoValor, userName, id, daoManager);
                if (correcto) hashMapUsuarios.get(userName).setEmail(nuevoValor);
            }
            case "password" -> {
                correcto = usuarioSQL.updateUsuarios(atributo, String.valueOf(cypherPassword(nuevoValor)), userName, id, daoManager);
                if (correcto) hashMapUsuarios.get(userName).setContrasenia(nuevoValor);
            }
        }
        return correcto;
    }


    /**
     * Funcion para comprobar si el nombre del usuario y la contraseña corresponden con el usuario que inicia sesion
     *
     * @param username    como cadena
     * @param contrasenia como cadena
     * @return true si si corresponde o false si no corresponde
     */
    public boolean correspondeUsuyContrasenia(String username, String contrasenia) {
        if (existeNombreUsuario(username)) {
            return hashMapUsuarios.get(username).getContrasenia().equals(String.valueOf(cypherPassword(contrasenia)));
        }
        return false;
    }

    /**
     * Funcion que comprueba si el nombre de usuario introducido ya existe o no
     * @param username como cadena
     * @return true or false dependiendo de si existe o no el nombre del usuario
     */
    public boolean existeNombreUsuario(String username) {
        return hashMapUsuarios.containsKey(username);
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
        FileOutputStream fos;
        try {
            if (ubicacionLogins!=null) {
                String rutaLoginsCompleta = String.valueOf(this.getClass().getClassLoader().getResource(ubicacionLogins));
                fos = new FileOutputStream(rutaLoginsCompleta);
                iniciosSesion.store(fos, "Fichero de configuración");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza el ultimo inicio de sesión de un usuario en especifico
     * @param username como una cadena
     */
    public void actualizarUltimoInicioSesionUsuario(String username) {
        iniciosSesion.setProperty(username, String.valueOf(LocalDateTime.now()));
    }

    /**
     * Recupera el ultimo inicio de sesión de un usuario en especifico
     * @param username como una cadena
     * @return una cadena con el username del usuario
     */
    public LocalDateTime recuperarUltimoInicioSesionUsuario(String username) {
        return LocalDateTime.parse(iniciosSesion.getProperty(username, String.valueOf(LocalDateTime.now())));
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
    public boolean inversorPaga(String username, double cantidad, DAOManager dao) {
        Inversor aux = (Inversor) hashMapUsuarios.get(username);
        boolean right = false;
        if (aux.puedePagarInversor(cantidad)) {
            right = usuarioSQL.updateInversores("saldo", String.valueOf(aux.getSaldo() - cantidad), username, aux.getId(), dao);
            if (right) aux.setSaldo(aux.getSaldo() - cantidad);
        }
        return right;
    }
    public HashMap<String, Usuario> getHashMapUsuarios() {
        return hashMapUsuarios;
    }
}
