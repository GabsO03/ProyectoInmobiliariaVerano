package Model.Biblioteca;

import java.util.Scanner;

public class Lectura_De_Datos {

    /**
     * Leemos un número entero introducido por pantalla
     * @return un número entero introducido por pantalla
     */
    public static int leerOpcionNumerica () {
        Scanner escaner = new Scanner(System.in);
        return Integer.parseInt(escaner.nextLine());
    }

    /**
     * Leemos un número decimal introducido por pantalla
     * @return un número decimal introducido por pantalla
     */
    public static double leerOpcionDouble () {
        Scanner escaner = new Scanner(System.in);
        return Double.parseDouble(escaner.nextLine());
    }

    /**
     * Leemos una cadena introducida por pantalla
     * @return una cadena introducida por pantalla
     */
    public static String leerOpcionLiteral () {
        Scanner escaner = new Scanner(System.in);
        String cadena = escaner.nextLine();
        cadena = cadena.replace(";", " ");
        cadena = cadena.replace("--", " ");
        return cadena;
    }
}
