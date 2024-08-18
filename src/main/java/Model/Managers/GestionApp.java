package Model.Managers;


import DAO.DAOManager;
import Model.BusinessClases.Inversion;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class GestionApp  {

    private GestionProyectos gestionProyectos;
    private GestionUsuarios gestionUsuarios;
    private ArrayList<GestionInversiones> gestionesInversiones;
    private Properties ubicaciones;
    private final String RUTA_UBICACIONES_MODO_INVITADO = "FicherosDatosSistema/UbicacionesYmodoInvitado.properties";
    public GestionApp(DAOManager daoManager) {
        this.ubicaciones = new Properties();
        try {
            InputStream rutaProperties = this.getClass().getClassLoader().getResourceAsStream(RUTA_UBICACIONES_MODO_INVITADO);
            ubicaciones.load(rutaProperties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String rutaFicheroLog = ubicaciones.getProperty("LogUbicacion");
        String rutaLastLogins = ubicaciones.getProperty("UltimosIniciosSesionUbicacion");

        this.gestionProyectos = new GestionProyectos(rutaFicheroLog);
        gestionProyectos.cargarProyectos(daoManager);
        this.gestionUsuarios = new GestionUsuarios(daoManager, rutaFicheroLog, rutaLastLogins);
        this.gestionesInversiones = new ArrayList<>();
    }

    public ArrayList<GestionInversiones> getGestionesInversiones() {
        return gestionesInversiones;
    }

    public GestionUsuarios getGestionUsuarios() {
        return gestionUsuarios;
    }

    public GestionProyectos getGestionProyectos() {
        return gestionProyectos;
    }


    /**
     * Funcion para recuperar el properties de la ubicacion
     * @param archivo como una cadena
     * @return una cadena con la ubicacion
     */
    public String recuperaUbicacion(String archivo) {
        return ubicaciones.getProperty(archivo);
    }

    /**
     * Funcion para recuperar el properties del modo invitado
     * @return un boolean dependiendo de si el modo invitado esta activado o no
     */
    public boolean devuelveModoInvitado() {
        return Boolean.parseBoolean(ubicaciones.getProperty("ModoInvitado"));
    }

    /**
     * Funcion para poder actualizar las properties
     * @param propiedad como cadeana
     * @param valor como cadena
     */
    public void actualizarPropiedades(String propiedad, String valor) {
        ubicaciones.setProperty(propiedad, valor);
    }

    /**
     * Funcion para guardar todos los properties
     */
    public void guardarPropiedades() {
        try {
            //String rutaPrincipal = System.getProperty("user.dir");
            //File properties = new File(rutaPrincipal, RUTA_UBICACIONES_MODO_INVITADO);
            //TODO ARREGLAR RUTA
            String properties = "C:\\Users\\pollo\\Programación T1DA_ejs\\Intellij\\ProyectoInmobiliaria\\src\\main\\resources\\FicherosDatosSistema\\UbicacionesYmodoInvitado.properties";
            FileOutputStream fos = new FileOutputStream(properties);
            ubicaciones.store(fos, "Fichero de configuración");
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funcion para cargar a las inversiones de los inversores
     * @param daoManager como un objeto de la clase DAOManager
     */
    public void cargarGestionesInversiones(DAOManager daoManager) {
        String rutaFicheroLog = ubicaciones.getProperty("LogUbicacion");
        for (Map.Entry usuariosPareja: gestionUsuarios.getHashMapUsuarios().entrySet()) {
            Usuario aux= (Usuario) usuariosPareja.getValue();
            if (gestionUsuarios.averiguarClase(aux.getUsername()).equals("Inversor")) gestionesInversiones.add(new GestionInversiones((Inversor) aux, gestionProyectos, daoManager, rutaFicheroLog));
        }
    }
    public GestionInversiones consigueGestionInversion(DAOManager daoManager, String username) {
        String rutaFicheroLog = ubicaciones.getProperty("LogUbicacion");
        if (gestionUsuarios.existeNombreUsuario(username)) {
            return new GestionInversiones((Inversor) gestionUsuarios.devuelveUsuario(username), gestionProyectos, daoManager,
                    rutaFicheroLog);
        }
        return null;
    }

    public Inversion consigueInversion (DAOManager daoManager, String username) {
        return null;
    }

    public boolean enviarInversiones() {
        boolean correcto = false;
        for (GestionInversiones gestionInversiones: gestionesInversiones) {
            correcto = gestionInversiones.escribirMandarExcel();
        }
        return correcto;
    }
    /**
     * Funcion para buscar las inversiones realizadas por un inversor
     * @param username como cadena
     * @return un número entero dependiendo de la posición del array donde se encuentre ese usuario o -1 si no se encuentra
     */
    public int encuentraGestionInversiones (String username) {
        for (int i = 0; i < gestionesInversiones.size(); i++) {
            if (gestionesInversiones.get(i).getInversor().getUsername().equals(username)) return i;
        }
        return -1;
    }
}
