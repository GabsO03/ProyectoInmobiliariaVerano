package Model.Managers;

import DAO.DAOManager;
import DAO.InversionSQL;
import Model.BusinessClases.Inversion;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Proyecto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static Model.Biblioteca.AccountSettings.enviarCorreoConAdjunto;

public class GestionInversiones {
    private Inversor inversor;
    private ArrayList<Inversion> inversiones;
    public InversionSQL inversionSQL;

    public Inversor getInversor() {
        return inversor;
    }

    public ArrayList<Inversion> getInversiones() {
        return inversiones;
    }

    public GestionInversiones (Inversor inversor, GestionProyectos gestionProyectos, DAOManager daoManager, String rutaFicheroLog) {
        this.inversor = inversor;
        this.inversiones = new ArrayList<>();
        this.inversionSQL = new InversionSQL(rutaFicheroLog);
        cargarInversiones(gestionProyectos, daoManager);
    }

    public void cargarInversiones (GestionProyectos gestionProyectos, DAOManager daoManager) {
        inversiones = inversionSQL.cargarInversiones(inversor.getId(), gestionProyectos, daoManager);
    }

    /**
     * Funcion para realizar una nueva inversion
     * @param proyecto como objeto de la clase Proyecto
     * @param primerIngreso como double
     * @param dao como un objeto de la clase DAOManager
     */
    public boolean nuevaInversion (Proyecto proyecto, double primerIngreso, DAOManager dao) {
        Inversion aux = new Inversion(proyecto, primerIngreso);
        boolean alright = inversionSQL.insertar(aux, inversor.getUsername(), inversor.getId(), dao);
        if (alright) inversiones.add(aux);
        return alright;
    }

    /**
     * Actualiza una inversion ya realizada
     * @param codigo_inversion como entero
     * @param cantidadEntrante como double
     * @param dao como un objeto de la clase DAOManager
     */
    public boolean actualizarInversion (int codigo_inversion, double cantidadEntrante, DAOManager dao) {
        return  inversionSQL.update(inversor.getUsername(), "cantidadParticipada", String.valueOf(cantidadEntrante), codigo_inversion, dao);
    }

    public void buscaInversiones(String orderBy, String direccion, String atributo, String valor, GestionProyectos gestionProyectos, DAOManager daoManager) {
        if (atributo.equals("nombre")||atributo.equals("tipo")) inversiones = inversionSQL.buscarInversionesProyecto(inversor.getId(), orderBy, direccion, atributo, valor, gestionProyectos, daoManager);
        else  inversiones = inversionSQL.buscarInversiones(inversor.getId(), orderBy, direccion, atributo, valor, gestionProyectos, daoManager);
    }

    public Inversion devuelveInversion (int id_inversor, Proyecto proyecto, DAOManager daoManager) {
        return inversionSQL.conseguirInversion(proyecto, id_inversor, daoManager);
    }

    /**
     * Función que sirve para escribir un excel y enviarlo a un correo en especifico
     * @return true si se ha podido escribir y mandar el excel o false si no se ha podido
     */
    public boolean escribirMandarExcel() {
        String rutaFicheroCompleta = "C:/Users/pollo/Programación T1DA_ejs/Intellij/ProyectoInmobiliaria/src/main/resources/FicheroInversiones/" + inversor.getName() + "_tusInversiones.csv";
        //TODO arreglar ruta
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaFicheroCompleta));
            bw.write("Nombre del proyecto;Cantidad Participada;Fecha Inicio;Ultima Actualizacion\n");
            for (Inversion inversion : inversiones) {
                bw.write(inversion.getProyecto().getNombre() + ";" + inversion.getCantidadParticipada() + ";" + inversion.getFechaInicio() + ";" + inversion.getUltimaActualizacion() + "\n");
            }
            bw.close();
            return enviarCorreoConAdjunto(inversor.getEmail(),"Tus Inversiones","Este archivo contiene tus inversiones","src/FicherosInversiones/" + inversor.getEmail() + "_tusInversiones.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
