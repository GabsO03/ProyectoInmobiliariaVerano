package Servlets;

import DAO.DAOManager;
import Model.Managers.GestionApp;
import Model.Managers.GestionUsuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static Model.Biblioteca.AccountSettings.enviarCorreo;
import static Model.Biblioteca.FuncionesCadenas.*;

@WebServlet(name = "signin-servlet", value = "/signin-servlet")
public class SinginServlet extends HttpServlet {
    private DAOManager daoManager;
    private GestionApp gestionApp;
    public void init() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        gestionApp = new GestionApp(daoManager);
        GestionUsuarios gestionUsuarios = gestionApp.getGestionUsuarios();

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeat_password = request.getParameter("repeat-password");
        String tipo_usuario = request.getParameter("tipo-usuario");
        HashMap<String, Boolean> errores = new HashMap<>();

        boolean existeNombreUsuario = gestionUsuarios.existeNombreUsuario(username);
        if (!usernameValido(username)) errores.put("usernameInvalido", true);
        if (existeNombreUsuario) errores.put("yaExisteUserName", true);
        if (!contraseniaEsSegura(password)) errores.put("notSafe", true);
        if (!password.equals(repeat_password)) errores.put("noCoincidence",true);

        if (errores.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("isSigningIn", true);
            session.setAttribute("name", name);
            session.setAttribute("username", username);
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            session.setAttribute("tipo-usuario", tipo_usuario);
            int generatedCode = (int) (Math.random() * 99999) + 10000;
            session.setAttribute("generatedCode", generatedCode);
            enviarCorreo(email, "Correo de verificación", "Su código de verificación es: " + generatedCode);
            redirect("/Pages/EmailComprobation.jsp", request, response);
        } else {
             request.setAttribute("errores", errores);
             redirect("/Pages/SignIn.jsp", request, response);
        }
    }

    public void redirect(String pagina, HttpServletRequest request, HttpServletResponse response) {
        try {
            daoManager.close();
            getServletContext().getRequestDispatcher(pagina).forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}