package Model.Biblioteca;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Fechas {
    /**
     * Funcion para comprobar si una fecha es anterior a otra
     * @param fecha1 como un LocalDate
     * @param fecha2 como un LocalDate
     * @return true or false dependiendo de si la fecha1 es anterior a la fecha2
     */
    public static boolean esAnterior(LocalDate fecha1,LocalDate fecha2){
        return fecha1.isBefore(fecha2);
    }

    /**
     * Funcion para comprobar si una fecha es posterior a otra
     * @param fecha1 como un LocalDate
     * @param fecha2 como un LocalDate
     * @return true or false, dependiendo de si la fecha1 es posterior a la fecha2
     */
    public static boolean esPosterior(LocalDate fecha1, LocalDate fecha2){
        return fecha1.isAfter(fecha2);
    }

    /**
     * Funcion para convertir una fecha a cadena
     * @param fecha como un LocalDate
     * @return un String con la fecha convertida en String
     */
    public static String fechaACadena (LocalDate fecha) {
        return String.valueOf(fecha);
    }

    /**
     * Funcion para convertir una cadena a una fecha
     * @param fecha como un String
     * @return un LocalDate con la fecha convertida en LocalDate
     */
    public static LocalDate cadenaAfecha (String fecha) {
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static int periodo (LocalDate inicio, LocalDate fin) {
        return fin.compareTo(inicio);
    }

}
