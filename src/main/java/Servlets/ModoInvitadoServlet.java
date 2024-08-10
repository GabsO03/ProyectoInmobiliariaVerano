package Servlets;

import DAO.DAOManager;
import Model.BusinessClases.Usuario;
import Model.Managers.GestionApp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@WebServlet(name = "modo-invitado-servlet", value = "/modo-invitado-servlet")
public class ModoInvitadoServlet extends HttpServlet {

    private DAOManager daoManager;
    private GestionApp gestionApp;
    public void init() {
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        daoManager = new DAOManager();
        daoManager.open();
        gestionApp = new GestionApp(daoManager);
        boolean invitadoHabilitado = gestionApp.devuelveModoInvitado();
        gestionApp.actualizarPropiedades("ModoInvitado", invitadoHabilitado?"false":"true");
        gestionApp.guardarPropiedades();
        HttpSession session = request.getSession();
        session.setAttribute("invitadoHabilitado", gestionApp.devuelveModoInvitado());
        redirect("/Pages/Proyects.jsp", request, response);
    }

    public void redirect(String pagina, HttpServletRequest request, HttpServletResponse response) {
        try {
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