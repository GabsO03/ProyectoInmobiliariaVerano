package Model.Biblioteca;

import DAO.DAOManager;
import Model.BusinessClases.Inversor;
import Model.Managers.GestionApp;
import Model.Managers.GestionUsuarios;

import java.io.IOException;
import java.sql.SQLException;

import static Model.Biblioteca.Lectura_De_Datos.*;

public class UsuariosVista {

    /**
     * Muestra el panel de control de cada usuario
     * @param usuarios como objeto de la clase GestionUsuarios
     * @param paraGuardar como objeto de la clase GestionApp
     * @param daoManager como un objeto de la clase DAOManager
     */
    public static void panelControlUsuarios(GestionUsuarios usuarios, GestionApp paraGuardar, DAOManager daoManager) throws SQLException {
        int opcion;
        String nombreUsuario;
        do {
            System.out.println("Menú del panel de control de usuarios.");
            System.out.println(usuarios.devuelveListaUsuarios());
            System.out.println("Introduzca el nombre de usuario del usuario que quieras bloquear o desbloquear ('Cancelar' para salir)");
            nombreUsuario = leerOpcionLiteral();
            if (!nombreUsuario.equalsIgnoreCase("Cancelar")){
                System.out.println("1. Bloquear.\n2. Desbloquear\n3. Cancelar");
                opcion = leerOpcionNumerica();
                if (opcion == 1 || opcion == 2) {
                    usuarios.bloquearDesbloquearUsuario(nombreUsuario, daoManager);
                }
            }
        } while (!nombreUsuario.equalsIgnoreCase("Cancelar"));
    }
    /**
     * Funcion para mostrar y añadir saldo al inversor
     * @param inversor como objeto de la clase Inversor
     */
    public static void mostrarYAniadirSaldo(Inversor inversor){
        String respuesta;
        double cantidad;
        System.out.println("Tu saldo actual es de " + inversor.getSaldo() );
        System.out.println("¿Quieres añadir saldo? (Sí o No)");
        respuesta=leerOpcionLiteral();
        if(respuesta.equalsIgnoreCase("Sí")){
            System.out.println("Introduce cuanto saldo quieres añadir");
            cantidad=leerOpcionDouble();
            inversor.setSaldo(inversor.getSaldo() + cantidad);
            System.out.println("Tu saldo ahora es de " + inversor.getSaldo());
        } else System.out.println("Cancelando.");
    }
}
