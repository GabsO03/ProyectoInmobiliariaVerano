package Model.Biblioteca;

import DAO.DAOManager;
import Model.BusinessClases.Inversor;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
import Model.Managers.GestionProyectos;
import Model.Managers.GestionUsuarios;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static Model.Biblioteca.AccountSettings.modificarCuenta;
import static Model.Biblioteca.InversionesVista.*;
import static Model.Biblioteca.Lectura_De_Datos.*;
import static Model.Biblioteca.ProyectosVista.*;
import static Model.Biblioteca.UsuariosVista.mostrarYAniadirSaldo;
import static Model.Biblioteca.UsuariosVista.panelControlUsuarios;


public class Menus {
    /**
     * Funcion que muestra el menu inicial
     */
    public static void menuInicio(boolean invitadoHabilitado) {
        if (invitadoHabilitado)
            System.out.println("Seleccione qué quiere hacer: \n1. Registro\n2. Iniciar sesión\n3. Modo invitado\n4. Salir");
        else
            System.out.println("Seleccione qué quiere hacer: \n1. Registro\n2. Iniciar sesión\n3. Salir");
    }

    /**
     * Funcion para activar o desactivar el modo Invitado del programa
     * @param aplicacion como instancia de la clase GestionApp
     */
    public static void activarYDesactivarModoInvitado(GestionApp aplicacion){
        boolean modoInvitadoHabilitado;
        modoInvitadoHabilitado = aplicacion.devuelveModoInvitado();

        System.out.println("Modo invitado: (ON/OFF)");
        if (modoInvitadoHabilitado) {
            aplicacion.actualizarPropiedades("ModoInvitado", "false");
            FileOutputStream fos;
            aplicacion.guardarPropiedades();
            System.out.println("Se ha desactivado el modo invitado");
        } else {
            aplicacion.actualizarPropiedades("ModoInvitado", "true");
            aplicacion.guardarPropiedades();
            System.out.println("Se ha activado el modo invitado");
        }
    }

    /**
     * Funcion para mostrar el menu de los proyectos del gestor
     */
    public static void menuProyectosGestor() {
        System.out.println("Seleccione que desea realizar con los proyectos: \n1. Mostrar proyectos creados.\n2. Crear nuevo proyecto.\n3. Configuración de cuenta.\n4. Salir");
    }

    /**
     * Muestra el menu del administrador
     */
    public static void menuAdministrador() {
        System.out.println("Seleccione dónde quiere acceder: \n1. Panel de control\n2. Proyectos\n3. Configuración de la cuenta\n4. Configuración del programa\n5. Enviar inversiones\n6. Salir");
    }

    /**
     * Muestra el menu de la configuración del Programa
     */
    public static void menuConfiguracionPrograma() {
        System.out.println("Configuración del programa:\n1. Activar/Desactivar modo invitado\n2. Últimas conexiones\n3. Salir\n-> ");
    }

    /**
     * Muestra el menu para modificar algún proyecto
     */
    public static void menuModificarProyecto() {
        System.out.println("Elija qué quiere modificar:\n1. Nombre\n2. Descripcion\n3. Tipo\n4. Cantidad necesaria\n5. Fecha fin\n6. Nada");
    }

    /**
     * Muestra el menu del inversor
     */
    public static void menuOpcinesInversor() {
        System.out.println("Seleccione donde quiere acceder:\n1. Mis inversiones\n2. Buscar proyecto\n3. Invertir\n4. Cartera Virtual\n5. Configuración de cuenta\n6. Salir");
    }

    /**
     * Muestra el menu de la configuracion para todos los usuarios
     */
    public static void menuConfiguracion() {
        System.out.println("Seleccione qué quiere hacer:");
        System.out.println("1. Cambiar contraseña\n2. Cambiar email\n3. Cambiar nombre de usuario\n4. Cambiar nombre\n3. Salir");
    }

    /**
     * Muestra el segundo menu del administrador
     * @param username como una cadena
     * @param usuarios como un objeto de la clase GestionUsuarios
     * @param proyectos como un objeto de la clase GestionProyectos
     * @param paraGuardar como un objeto de la clase GestionApp
     * @param rutaArchivoUltimoLogin como una cadena
     * @param daoManager como un objeto de la clase DAOManager
     */
    public static void menuAdmin(String username, GestionUsuarios usuarios, GestionProyectos proyectos, GestionApp paraGuardar, String rutaArchivoUltimoLogin, DAOManager daoManager) throws SQLException {
        int primerSubmenu, segundoSubmenu, tercerSubmenu;
        do {
            menuAdministrador();
            primerSubmenu = leerOpcionNumerica();
            switch (primerSubmenu) {
                case 1 -> panelControlUsuarios(usuarios, paraGuardar, daoManager);
                case 2 -> {
                    do {
                        System.out.println("Menú proyectos:\n1. Modificar/Eliminar proyectos\n2. Buscar proyectos\n3. Cancelar");
                        segundoSubmenu = leerOpcionNumerica();
                        switch (segundoSubmenu) {
                            case 1 -> {
                                System.out.println("Seleccione el orden:\n1. Predeterminado 2. Ordenar por:");
                                int opcion = leerOpcionNumerica();
                                mostrarProyectos(opcion,1, proyectos, daoManager);
                                segundoSubmenu = leerOpcionNumerica();
                                switch (segundoSubmenu) {
                                    case 1 -> proyectosDetallados(proyectos);
                                    case 2 -> {
                                        System.out.println("Introduzca el código del proyecto");
                                        int codigoProyecto = leerOpcionNumerica();
                                        if (codigoProyecto >= 0 && codigoProyecto < proyectos.getCantidadProyectos()) {
                                            System.out.println("Escoja la opción: 1. Eliminar 2. Modificar");
                                            int respuesta = leerOpcionNumerica();
                                            switch (respuesta) {
                                                case 1 -> {
                                                    System.out.println("El proyecto " + proyectos.eliminarProyecto(codigoProyecto, daoManager, username) + " se ha eliminado correctamente");
                                                }
                                                case 2 -> {
                                                    do {
                                                        menuModificarProyecto();
                                                        tercerSubmenu = leerOpcionNumerica();
                                                        System.out.println("Ingrese el nuevo valor (ejemplo fecha: yyyy-mm-dd):");
                                                        //if (proyectos.updateProyecto(tercerSubmenu, leerOpcionLiteral(), codigoProyecto, daoManager, username)) {
                                                        //}
                                                    } while (tercerSubmenu != 6);
                                                }
                                            }
                                        } else System.out.println("Proyecto no encontrado.");
                                    }
                                    case 3 -> System.out.println();
                                }
                            }
                            //case 2 -> buscarProyecto(proyectos);
                        }
                    } while (segundoSubmenu != 3);
                }
                case 3 -> modificarCuenta(username, usuarios, paraGuardar, daoManager);
                case 4 -> {
                    int opcion;
                    do {
                        menuConfiguracionPrograma();
                        opcion = leerOpcionNumerica();
                        switch (opcion) {
                            case 1 -> {
                                activarYDesactivarModoInvitado(paraGuardar);
                            }
                            case 2 -> {
                                try {
                                    BufferedReader bf = new BufferedReader(new FileReader(rutaArchivoUltimoLogin));
                                    String linea = bf.readLine();
                                    System.out.println("Últimos inicios de sesión:\n============================================");
                                    while (linea != null) {
                                        System.out.println(linea);
                                        linea = bf.readLine();
                                    }
                                    System.out.println("============================================");
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } while (opcion != 3);
                }
                case 5->{
                }
                case 6 -> System.out.println("Cerrando sesión.");
                default -> System.out.println("Opción invalida");
            }
        } while (primerSubmenu != 6);
    }

    /**
     * Muestra el menu del gestor
     * @param username como una cadena
     * @param usuarios como un objeto de la clase GestionUsuarios
     * @param proyectos como un objeto de la clase GestionProyectos
     * @param paraGuardar como un objeto de la clase GestionApp
     * @param daoManager como un objeto de la clase DAOManager
     */
    public static void menuGestor(String username, GestionUsuarios usuarios, GestionProyectos proyectos, GestionApp paraGuardar, DAOManager daoManager) throws SQLException {
        int primerSubmenu;
        do {
            menuProyectosGestor();
            primerSubmenu = leerOpcionNumerica();
            switch (primerSubmenu) {
                case 1 -> {
                    System.out.println("Seleccione el orden:\n1. Predeterminado 2. Ordenar por:");
                    int opcion = leerOpcionNumerica();
                    mostrarProyectos(opcion, 0, proyectos, daoManager);
                } //EL TIPO ES 0 PORQUE SI PONEMOS 1 SALE MAS INFO PARA EL ADMIN
                case 2 -> {
                    //boolean correcto = proyectos.insertarProyecto(cambiarNombreProyecto(), cambiarDescripcionProyecto(), cambiarTipoProyecto(), cambiarFechaFin(), cambiarCantidadNecesaria(), username, daoManager);
                    //System.out.println(correcto?"Proyecto insertado correctamente": "Hubo un problema al insertar el proyecto");
                }
                case 3 -> modificarCuenta(username, usuarios, paraGuardar, daoManager);
                case 4 -> System.out.println("Cerrando sesión.");
                default -> System.out.println("Invalid response");
            }
        } while (primerSubmenu != 4);
    }



    /**
     * Muestra el menu del Inversor
     * @param username como una cadena
     * @param usuarios como un objeto de la clase GestionUsuarios
     * @param proyectos como un objeto de la clase GestionProyectos
     * @param gestionInversiones como un array dinámico de objetos de la clase GestionInversiones
     * @param paraGuardar como un objeto de la clase GestionApp
     * @param daoManager como un objeto de la clase DAOManager
     */
    public static void menuInversor(String username, GestionUsuarios usuarios, GestionProyectos proyectos, ArrayList<GestionInversiones> gestionInversiones, GestionApp paraGuardar, DAOManager daoManager) throws SQLException {
        int primerSubmenu, opcionInversion, gestionIndividual = 0; //encuentraGestionInversiones(username);
        do {
            menuOpcinesInversor();
            primerSubmenu = leerOpcionNumerica();
            switch (primerSubmenu) {
                case 1 -> System.out.println("pollo");//gestionInversiones.get(gestionIndividual).devuelveMisInversiones());
                //case 2 -> buscarProyecto(proyectos);
                case 3 -> {
                    System.out.println("1. Nueva inversión\n2. Actualizar inversión\n3. Cancelar");
                    opcionInversion = leerOpcionNumerica();
                    switch (opcionInversion) {
                        case 1 -> {
                            nuevaInversion(usuarios, proyectos, gestionInversiones.get(gestionIndividual), daoManager);
                        }
                        case 2 -> {
                            actualizarInversion(usuarios, gestionInversiones.get(gestionIndividual), daoManager,paraGuardar);
                        }
                        case 3 -> System.out.println();
                        default -> System.out.println("Invalid response.");
                    }
                }
                case 4 -> {
                    mostrarYAniadirSaldo((Inversor) usuarios.devuelveUsuario(username));
                }
                case 5 -> modificarCuenta(username, usuarios, paraGuardar, daoManager);
                case 6 -> System.out.println("Cerrando sesión.");
                default -> System.out.println("Opción invalidad");
            }
        } while (primerSubmenu != 6);
    }
}
