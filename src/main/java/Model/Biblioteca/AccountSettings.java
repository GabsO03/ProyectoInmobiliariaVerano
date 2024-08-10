package Model.Biblioteca;

import DAO.DAOManager;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Usuario;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
import Model.Managers.GestionUsuarios;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import static Model.Biblioteca.Lectura_De_Datos.*;
import static Model.Biblioteca.Menus.menuConfiguracion;
import static Model.Biblioteca.FuncionesCadenas.contraseniaEsSegura;


public class AccountSettings {

    /* ----------------------------------------------------Login----------------------------------------------------- */
    /**
     * Enviamos el correo a la dirección del destinatario con el asunto y cuerpo que deseemos
     * @param destinatario como una cadena
     * @param asunto como una cadena
     * @param cuerpo como una cadena
     */
    public static boolean enviarCorreo(String destinatario, String asunto, String cuerpo) {
        String remitente = "adrian.contreras.2706@fernando3martos.com";
        String clave = "chksotcvupynairz";
        // Propiedades de la conexion
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //Servidor de google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }

    /**
     * Función para mandar un correo con un archivo adjunto a el
     * @param destinatario como una cadena
     * @param asunto como una cadena
     * @param cuerpo como una cadena
     * @param adjuntoPath como una cadena
     */
    public static boolean enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpo, String adjuntoPath) {
        String remitente = "adrian.contreras.2706@fernando3martos.com";
        String clave = "chksotcvupynairz";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            message.setFileName(adjuntoPath);
            MimeBodyPart adjuntoPart = new MimeBodyPart();
            File archivo = new File(adjuntoPath);
            adjuntoPart.attachFile(archivo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    /* ----------------------------------------------------Change----------------------------------------------------- */

    /**
     * Funcion para cambiar el nombre de usuario del Usuario
     * @return una cadena con el nuevo nombre
     */
    public static String cambiarUsuario (HashMap<String, Usuario> usuarioHashMap){
        System.out.println("Escriba el nuevo usuario");
        String newUsername;
        do {
            newUsername = leerOpcionLiteral();
            if (usuarioHashMap.get(newUsername)!=null) System.out.println("Ese nombre de usuario ya existe, intenté otro.");
        } while (usuarioHashMap.get(newUsername)!=null);
        return newUsername;
    }

    /**
     * Funcion para cambiar la contraseña del Usuario
     * @return una cadena con la nueva contraseña
     */
    public static String cambiarcontrasenia (){
        String passNuevoUsuario, passRepetidaNuevoUsuario;
        do {
            System.out.println("Escriba su contraseña: ");
            passNuevoUsuario = leerOpcionLiteral();
        }while(!contraseniaEsSegura(passNuevoUsuario));
        do {
            System.out.println("Escriba su contraseña de nuevo: ");
            passRepetidaNuevoUsuario = leerOpcionLiteral();
            if (!passNuevoUsuario.equals(passRepetidaNuevoUsuario)) System.out.println("Las contraseñas no coinciden");
        }while(!passRepetidaNuevoUsuario.equals(passNuevoUsuario));
        return passNuevoUsuario;
    }
    /**
     * Funcion para cambiar el email del Usuario
     * @return una cadena con el nuevo email
     */
    public static String cambiarEmail(){
        System.out.println("Escriba el nuevo email");
        return leerOpcionLiteral();
    }
    public static String cambiarNombre() {
        System.out.println("Ingrese su nuevo nombre:");
        return leerOpcionLiteral();
    }

    /**
     * Funcion para modificar algún atributo de la cuenta del usuario
     * @param userName como cadena
     * @param usuarios como un objeto de la clase GestionUsuarios
     */
    public static void modificarCuenta (String userName, GestionUsuarios usuarios, GestionApp paraGuardar, DAOManager daoManager) throws SQLException {
        int opcion;
        do {
            menuConfiguracion();
            opcion = leerOpcionNumerica();
            String nuevoValor = switch (opcion) {
                case 1 -> cambiarcontrasenia();
                case 2 -> cambiarEmail();
                case 3 -> cambiarUsuario(usuarios.getHashMapUsuarios());
                case 4 -> cambiarNombre();
                default -> throw new IllegalStateException("Unexpected value: " + opcion);
            };
            /*boolean right = usuarios.modificarUsuario(opcion, nuevoValor, userName, daoManager);
            if (right){
                System.out.println("Se ha modificado correctamente");
            }*/
        } while (opcion != 3);
    }

    /**
     * Funcion para poder registrar a un nuevo Usuario
     * @param tipo como cadena
     * @param usuarios como un objeto de la clase GestionUsuarios
     * @param gestionInversiones un array dinamico de un objeto de la clase GestionInversiones
     * @return true si se ha conseguido registrar correctamente o false si no se ha podido registrar correctamente
     */
    public static boolean registroUsuarioNuevo(String tipo, GestionUsuarios usuarios, ArrayList<GestionInversiones> gestionInversiones, GestionApp paraGuardar, DAOManager daoManager) throws SQLException {
        System.out.println("Escriba su nombre completo: ");
        String nombre = leerOpcionLiteral(), passNuevoUsuario, passRepetidaNuevoUsuario, correoNuevoUsuario, nuevoUsuario;
        do {
            System.out.println("Escriba su nombre de usuario: ");
            nuevoUsuario = leerOpcionLiteral();
            if (usuarios.existeNombreUsuario(nuevoUsuario)) System.out.println("Ese nombre de usuario ya existe");
        } while (usuarios.existeNombreUsuario(nuevoUsuario));
        do {
            System.out.println("Escriba su contraseña: ");
            passNuevoUsuario = leerOpcionLiteral();
            if (contraseniaEsSegura(passNuevoUsuario)) {
                System.out.println("Vuelva a escribir su contraseña: ");
                do {
                    passRepetidaNuevoUsuario = leerOpcionLiteral();
                    if (!passNuevoUsuario.equalsIgnoreCase(passRepetidaNuevoUsuario))
                        System.out.println("Error, las contraseñas deben de ser iguales\nVuelva a intentarlo");
                } while (!passNuevoUsuario.equalsIgnoreCase(passRepetidaNuevoUsuario));
            }
        } while (!contraseniaEsSegura(passNuevoUsuario));
        System.out.println("Escriba su email: ");
        correoNuevoUsuario = leerOpcionLiteral();
        int codigoEnviado, codigoUsuario;
        do {
            codigoEnviado = (int) (Math.random() * 99999) + 10000;
            System.out.println(codigoEnviado);
            System.out.println("Se está enviando un código de verificacion...");
            enviarCorreo(correoNuevoUsuario, "Correo de verificación", "Su código de verificación es: " + codigoEnviado);
            System.out.println("Revise su bandeja de entrada y escriba el código");
            codigoUsuario = leerOpcionNumerica();
            if (codigoEnviado != codigoUsuario) System.out.println("ERROR, el código no es correcto");
        } while (codigoEnviado != codigoUsuario);
        passNuevoUsuario = String.valueOf(cypherPassword(passNuevoUsuario));
        if (tipo.equalsIgnoreCase("G"))
            usuarios.insertarUsuarioGestor(nombre, nuevoUsuario, passNuevoUsuario, correoNuevoUsuario, daoManager);
        else {
            boolean correcto = usuarios.insertarUsuarioInversor(nombre, nuevoUsuario, passNuevoUsuario, correoNuevoUsuario, daoManager);
            if (correcto) gestionInversiones.add(new GestionInversiones((Inversor) usuarios.devuelveUsuario(nuevoUsuario), "caca"));
        }
        return true;
    }

    /**
     * Funcion para cifrar la contraseña del usuario
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
