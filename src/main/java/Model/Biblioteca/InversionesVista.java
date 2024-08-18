package Model.Biblioteca;

import DAO.DAOManager;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
import Model.Managers.GestionProyectos;
import Model.Managers.GestionUsuarios;

import static Model.Biblioteca.Lectura_De_Datos.*;

public class InversionesVista {
    /**
     * Funcion para realizar una nueva inversion
     *
     * @param proyectos   como objeto de la clase GestionProyectos
     * @param inversiones proyectos como objeto de la clase GestionInversiones
     */
    public static void nuevaInversion(GestionUsuarios gestionUsuarios, GestionProyectos proyectos, GestionInversiones inversiones, DAOManager daoManager) {
        int respuesta;
        double cantidadParticipativa;
        //inversiones.proyectosAunNoInvertidos(proyectos);
        System.out.println("Escriba el código del proyecto en el que quiere invertir:");
        respuesta = leerOpcionNumerica();
        int pos = 0; //proyectos.buscarProyectos(1, String.valueOf(respuesta));
        boolean yaInvirtioAqui = true; // inversiones.buscaInversionPorProyecto(respuesta)>-1;
        if (pos >= 0 && !yaInvirtioAqui) {
            System.out.println("Introduzca la cantidad que quieres invertir en el proyecto:");
            cantidadParticipativa = leerOpcionDouble();
            /*if (inversiones.nuevaInversion(gestionUsuarios, proyectos.devuelveProyectoPos(pos), cantidadParticipativa, daoManager)) {
                System.out.println("Inversión existosa, los detalles están disponibles para revisión.");
            } else
                System.out.println("No cuentas con saldo suficiente para realizar esta transacción.");
        */
        } else System.out.println("Introduce un código correcto");

    }

    /**
     * Funcion para actualizar una inversion ya realizada anteriormente
     *
     * @param inversiones proyectos como objeto de la clase GestionInversiones
     */
    public static void actualizarInversion(GestionUsuarios usuarios, GestionInversiones inversiones, DAOManager daoManager, GestionApp paraGuardar) {
        int respuesta;
        double cantidadParticipativa;
        //System.out.println(inversiones.devuelveMisInversiones());
        System.out.println("Escribe el código de la inversión que quiere actualizar");
        respuesta = leerOpcionNumerica();
        int pos = 0; // inversiones.buscaInversion(respuesta);
        if (pos >= 0) {
            System.out.println("Introduzca la cantidad que quieres añadir en el proyecto:");
            cantidadParticipativa = leerOpcionDouble();
            /*if (inversiones.actualizarInversion(pos, usuarios, cantidadParticipativa, daoManager)) {
                System.out.println("Inversión existosa, los detalles están disponibles para revisión.");
            } else
                System.out.println("No cuentas con saldo suficiente para realizar esta transacción.");
*/
        } else System.out.println("Ese proyecto no existe o escribiste el nombre incorrectamente.");
    }
}
