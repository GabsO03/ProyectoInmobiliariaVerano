package Model.Biblioteca;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FuncionesCadenas {

    /**
     * Función para comprobar si nuestra contraseña cumple con ciertos requisitos
     * @param pass como cadena
     * @return true si cumple los requisitos y false si no los cumple
     */
    public static boolean contraseniaEsSegura(String pass){
        char[] abecedarioMay={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] abecedarioMin={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] numeros={'1','2','3','4','5','6','7','8','9','0'};
        boolean mayuscula=false, minuscula=false, lenght = false, specialChar = false, numbers = false;
        if (pass.length()>=8&&pass.length()<=100) lenght=true;
        if (pass.contains("@")||pass.contains("-")||pass.contains("_")||pass.contains("*")||pass.contains("+")||pass.contains(".")||pass.contains("$")||pass.contains("#")) specialChar = true;
        int i, j = 0;
        while (!mayuscula && j< abecedarioMay.length) {
            i=0;
            while(i<pass.length() && !mayuscula) {
                if (pass.charAt(i) == abecedarioMay[j]) mayuscula = true;
                i++;
            }
            j++;
        }

        j=0;
        while (!minuscula && j< abecedarioMin.length) {
            i=0;
            while(i<pass.length() && !minuscula) {
                if (pass.charAt(i) == abecedarioMin[j]) minuscula = true;
                i++;
            }
            j++;
        }

        j=0;
        while (!numbers && j< numeros.length) {
            i=0;
            while(i<pass.length() && !numbers) {
                if (pass.charAt(i) == numeros[j]) numbers = true;
                i++;
            }
            j++;
        }

        return mayuscula && minuscula && lenght && specialChar && numbers;
    }

    public static boolean usernameValido (String username) {
        if (username.length()<6||username.length()>16) return false;
        if (username.startsWith("_")||username.startsWith(".")) return false;
        if (username.endsWith("_")||username.endsWith(".")) return false;
        if (username.contains("__")||username.contains("..")) return false;
        if (username.contains(" ")) return false;
        return true;
    }

    public static String limpiaInput (String cadena) {
        cadena = cadena.replaceAll("'", "")
        .replaceAll("\"", "")
        .replaceAll("\'", "")
        .replaceAll(";", "")
        .replaceAll("-", "")
        .replaceAll(" ", "")
        .replaceAll("\\*", "")
        .replaceAll("=", "")
        .replaceAll("\\(", "")
        .replaceAll("\\)", "")
        .replaceAll("(..)", "")
        .replaceAll("(__)", "")
        .replaceAll("(\"and\")", "");
        return cadena;
    }

    public static String extraeFecha (LocalDateTime fechaHora) {
        return String.valueOf(fechaHora.toLocalDate());
    }
    public static String extraeHora (LocalDateTime fechaHora) {
        String cadena = String.valueOf(fechaHora.toLocalTime());
        return cadena.substring(0, cadena.lastIndexOf("."));
    }

    /**
     * Funcion para cifrar la contraseña
     * @param passwordEnClaro como cadena
     * @return una cadena con la contraseña cifrada
     */
    public static StringBuilder cypherPassword(String passwordEnClaro) {
        StringBuilder resumenPassword = new StringBuilder();
        try {
            byte[] bytesOfMessage = passwordEnClaro.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] digest = md.digest(bytesOfMessage);
            for (byte b : digest) {
                resumenPassword.append(String.format("%02x", b)); // Hexadecimal
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return resumenPassword;
    }
}
