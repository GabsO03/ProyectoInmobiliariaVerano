package Model.Biblioteca;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


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
}
