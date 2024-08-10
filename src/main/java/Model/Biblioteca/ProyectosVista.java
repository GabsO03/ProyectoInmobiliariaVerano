package Model.Biblioteca;

import DAO.DAOManager;
import Model.Managers.GestionProyectos;

import java.time.LocalDate;

import static Model.Biblioteca.Lectura_De_Datos.*;

public class ProyectosVista {
    /**
     * Modifica el nombre de un proyecto
     * @return una cadena con el nuevo nombre
     */
    public static String cambiarNombreProyecto(){
        System.out.println("Escriba el nuevo nombre del proyecto: ");
        return leerOpcionLiteral();
    }
    /**
     * Modifica la descripcion de un proyecto
     * @return una cadena con la nueva descripcion
     */
    public static String cambiarDescripcionProyecto(){
        System.out.println("Escriba la nueva descripción del proyecto: ");
        return leerOpcionLiteral();
    }
    /**
     * Modifica el tipo de un proyecto
     * @return una cadena con el nuevo tipo
     */
    public static int cambiarTipoProyecto(){
        int tipo;
        System.out.println("Escriba el nuevo tipo del proyecto:\n1. Préstamo\n2. Plusvalía\n3. Alquiler)");
        do {
            tipo = leerOpcionNumerica();
            if (tipo<1||tipo>3)
                System.out.println("Ingrese un tipo valido.");
        } while (tipo<1||tipo>3);
        return tipo;
    }
    /**
     * Modifica la cantidad necesaria de un proyecto
     * @return un double con la nueva cantidad necesaria
     */
    public static double cambiarCantidadNecesaria (){
        System.out.println("Escriba la nueva cantidad necesaria");
        return leerOpcionDouble();
    }
    /**
     * Modifica la fecha final de un proyecto
     * @return una cadena con la nueva fecha final
     */
    public static String cambiarFechaFin(){
        System.out.println("Escriba la nueva fecha de fin (aaaa-mm-dd)");
        return leerOpcionLiteral();
    }

    /**
     * Muestra todos los proyectos
     * @param opcion como un entero
     * @param tipo como un entero
     * @param proyectos como un objeto de la clase GestionProyectos
     * @param daoManager como un objeto de la clase DAOManager
     */
    public static void mostrarProyectos(int opcion, int tipo, GestionProyectos proyectos, DAOManager daoManager) {
        if (opcion==2) ordenarProyectos(proyectos, daoManager);
        int i = 0, tamanio = proyectos.getCantidadProyectos();
        while (i < tamanio) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Código: " + proyectos.devuelveProyectoPos(i).getCodigo());
            System.out.println("Nombre: " + proyectos.devuelveProyectoPos(i).getNombre());
            System.out.println("Tipo: " + proyectos.devuelveProyectoPos(i).getTipo());
            System.out.println("Cantidad Necesaria: " + proyectos.devuelveProyectoPos(i).getCantidadNecesaria());
            System.out.println("Cantidad Financiada: " + proyectos.devuelveProyectoPos(i).getCantidadFinanciada());
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            i++;
        }
        System.out.println("Existen " + tamanio + " proyectos.");
        if (tipo == 1) System.out.println("1. Ver más detalles.\n2. Eliminar o modificar.\n3. Salir.");
    }


    /**
     * Funcion para ordenar los proyectos
     * @param proyectos como un objeto de la clase GestionProyectos
     * @param daoManager como un objeto de la clase DAOManager
     */
    public static void ordenarProyectos (GestionProyectos proyectos, DAOManager daoManager) {
        System.out.println("Seleccione el orden\n1. Fecha creación más reciente\n2. Fecha creación más antigua\n3. Fecha fin más cercana\n4. Fecha fin más lejana" +
                "\n5. Cantidad necesaria más baja\n6. Cantidad necesaria más alta\n7. Cantidad financiada más baja\n8. Cantidad financiada más alta");
        int orden = leerOpcionNumerica();
        proyectos.ordenarProyectos(orden, daoManager);
    }
    /**
     * Funcion para mostrar todos los proyectos con más detalles
     * @param proyectos como un objeto de la clase GestionProyectos
     */
    public static void proyectosDetallados(GestionProyectos proyectos) {
        for (int i = 0; i < proyectos.getCantidadProyectos(); i++) {
            System.out.println("Proyecto " + (i + 1));
            System.out.println("Nombre: " + proyectos.devuelveProyectoPos(i).getNombre() + "\nDescripción: " + proyectos.devuelveProyectoPos(i).getDescripcion() + "\nTipo: " + proyectos.devuelveProyectoPos(i).getTipo());
            System.out.println("Fecha Inicial: " + proyectos.devuelveProyectoPos(i).getFechaInicio() + "\nFecha Final: " + proyectos.devuelveProyectoPos(i).getFechaFin());
            System.out.println("Cantidad Necesaria: " + proyectos.devuelveProyectoPos(i).getCantidadNecesaria() + "\nCantidad Financiada: " + proyectos.devuelveProyectoPos(i).getCantidadFinanciada());
            System.out.println("Gráfico:");
            crearGrafico(proyectos.devuelveProyectoPos(i).getCantidadNecesaria(), proyectos.devuelveProyectoPos(i).getCantidadFinanciada());
            System.out.println();
        }
    }
    /**
     * Funcion para crear el grafo de cada proyecto
     * @param cantidadNecesaria como double
     * @param cantidadFinanciada como double
     */
    private static void crearGrafico(double cantidadNecesaria, double cantidadFinanciada) {
        int porcentaje = (int) ((int) (cantidadFinanciada * 100) / cantidadNecesaria);
        String caracterLleno = "#";
        String caracterVacio = "_";
        System.out.println("Cantidad financiada hasta el momento: ");
        System.out.print(caracterLleno.repeat(porcentaje));
        System.out.print(caracterVacio.repeat((100 - porcentaje)));
    }

    /**
     * Funcion para buscar un proyecto dentro de un rango
     * @param atributo como cadena
     * @param valorInicial como cadena
     * @param valorFinal como cadena
     * @param proyectos como un objeto de la clase GestionProyectos
     */
    public static void buscarProyectoRango(int atributo, String valorInicial, String valorFinal, GestionProyectos proyectos) {
        System.out.println("Listado completo de proyectos según " + atributo + " (desde " + valorInicial + " hasta " + valorFinal + "):");
        int tamanio = proyectos.getCantidadProyectos();
        switch (atributo) {
            case 1 -> {
                LocalDate date1 = Fechas.cadenaAfecha(valorInicial);
                LocalDate date2 = Fechas.cadenaAfecha(valorFinal);
                for (int i = 0; i < tamanio; i++) {
                    LocalDate fechaProyecto = Fechas.cadenaAfecha(proyectos.devuelveProyectoPos(i).getFechaInicio());
                    if (Fechas.esPosterior(fechaProyecto, date1) && Fechas.esAnterior(fechaProyecto, date2)) System.out.println(proyectos.devuelveProyectoPos(i));
                }
            }
            case 2 -> {
                LocalDate date1 = Fechas.cadenaAfecha(valorInicial);
                LocalDate date2 = Fechas.cadenaAfecha(valorFinal);
                for (int i = 0; i < tamanio; i++) {
                    LocalDate fechaProyecto = Fechas.cadenaAfecha(proyectos.devuelveProyectoPos(i).getFechaFin());
                    if (Fechas.esPosterior(fechaProyecto, date1) && Fechas.esAnterior(fechaProyecto, date2)) {
                        System.out.println(proyectos.devuelveProyectoPos(i));
                    }
                }
            }
            case 3 -> {
                double v1 = Double.parseDouble(valorInicial);
                double v2 = Double.parseDouble(valorFinal);
                for (int i = 0; i < tamanio; i++) {
                    if (proyectos.devuelveProyectoPos(i).getCantidadNecesaria() >= v1 && proyectos.devuelveProyectoPos(i).getCantidadNecesaria() <= v2)
                        System.out.println(proyectos.devuelveProyectoPos(i));
                }
            }
            case 4 -> {
                double v1 = Double.parseDouble(valorInicial);
                double v2 = Double.parseDouble(valorFinal);
                for (int i = 0; i < tamanio; i++) {
                    if (proyectos.devuelveProyectoPos(i).getCantidadFinanciada() >= v1 && proyectos.devuelveProyectoPos(i).getCantidadFinanciada() <= v2)
                        System.out.println(proyectos.devuelveProyectoPos(i));

                }
            }
            default -> System.out.println("Error en datos introducidos.");
        }
    }

    /**
     * Funcion para buscar un determinado proyecto
     * @param proyectos un objeto de la clase GestionProyectos
     */
    public static void buscarProyecto (GestionProyectos proyectos) {
        int tipoBusqueda, position;
        String valor, valorInicial, valorFinal;
        int atributo;
        do {
            System.out.println("Elija el tipo de busqueda.\n1. Por atributo simple.\n2. Por rango.\n3. Cancelar");
            tipoBusqueda = leerOpcionNumerica();
            switch (tipoBusqueda) {
                case 1 -> {
                    System.out.println("Introduzca el atributo del proyecto:\n1. Código\n2. Nombre\n3. Tipo\n4. Descripcion\n5. Fecha de inicio\n6. Fecha fin\n7. Cantidad necesaria\n8. Cantidad financiada");
                    atributo = leerOpcionNumerica();
                    System.out.println("Introduzca el valor (ejemplo: fecha yyyy-mm-dd):");
                    valor = leerOpcionLiteral();
                    position = proyectos.buscarProyecto(atributo, valor);
                    if (position >= 0)
                        System.out.println(proyectos.devuelveProyectoPos(position));
                    else System.out.println("Ese proyecto no existe.");
                }
                case 2 -> {
                    System.out.println("Introduzca el atributo del proyecto:\n1. Fecha de inicio\n2. Fecha fin\n3. Cantidad necesaria\n4. Cantidad financiada):");
                    atributo = leerOpcionNumerica();
                    System.out.println("Introduzca el valor inicial (ejemplo fecha: yyyy-mm-dd):");
                    valorInicial = leerOpcionLiteral();
                    System.out.println("Introduzca el valor final (ejemplo fecha: yyyy-mm-dd):");
                    valorFinal = leerOpcionLiteral();
                    buscarProyectoRango(atributo, valorInicial, valorFinal, proyectos);
                }
                case 3 -> System.out.println();
                default -> System.out.println("Invalid response.");
            }
        } while (tipoBusqueda != 3);
    }

}
