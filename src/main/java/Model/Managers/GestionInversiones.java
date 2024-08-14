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

    public GestionInversiones (Inversor inversor, String rutaFicheroLog) {
        this.inversor = inversor;
        this.inversiones = new ArrayList<>();
        this.inversionSQL = new InversionSQL(rutaFicheroLog);
    }
    public GestionInversiones (Inversor inversor, GestionProyectos gestionProyectos, DAOManager daoManager, String rutaFicheroLog) {
        this.inversor = inversor;
        this.inversiones = new ArrayList<>();
        this.inversionSQL = new InversionSQL(rutaFicheroLog);
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
     * @param pos como entero
     * @param cantidadEntrante como double
     * @param dao como un objeto de la clase DAOManager
     */
    public void actualizarInversion (int pos, double cantidadEntrante, DAOManager dao) {
        inversiones.get(pos).aumentaInversion(cantidadEntrante);
        inversionSQL.update(inversor.getUsername(), "cantidadParticipada", String.valueOf(inversiones.get(pos).getCantidadParticipada()), inversiones.get(pos).getCodigo(), dao);
    }


    /**
     * Funcion para mostrar los proyectos en los que aún no se han invertido
     * @param todosLosProyectos como objeto de la clase GestionProyectos
     */
    public void proyectosAunNoInvertidos (GestionProyectos todosLosProyectos) {
        boolean esta;
        String cadena = "Proyectos aún no invertidos:\n============================";
        for (int i = 0; i < todosLosProyectos.getCantidadProyectos(); i++) {
            esta = false;
            for (Inversion inversiones : inversiones) {
                if (todosLosProyectos.devuelveProyectoPos(i).getNombre().equals(inversiones.getProyecto().getNombre()))
                    esta = true;
            }
            if (!esta) cadena = cadena.concat("\n" + todosLosProyectos.devuelveProyectoPos(i));
        }
        System.out.println(cadena);
    }

    public int buscaInversion(int codigo) {
        for (int i = 0; i < inversiones.size(); i++) {
            if (inversiones.get(i).getCodigo() == codigo) return i;
        }
        return -1;
    }
    public int buscaInversionPorProyecto(int proyectoCodigo) {
        for (int i = 0; i < inversiones.size(); i++) {
            if (inversiones.get(i).getProyecto().getCodigo() == proyectoCodigo) return i;
        }
        return -1;
    }

    public Inversion devuelveInversion (int codigoInversion) {
        return inversiones.get(buscaInversion(codigoInversion));
    }

    /**
     * Función que sirve para escribir un excel y enviarlo a un correo en especifico
     * @return true si se ha podido escribir y mandar el excel o false si no se ha podido
     */
    public boolean escribirMandarExcel() {
        String rutaFicheroCompleta = this.getClass().getClassLoader().getResource("FicheroInversiones/" + inversor.getName() + "_tusInversiones.csv").toString();
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
