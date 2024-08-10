package Servlets;

import DAO.DAOManager;
import Model.BusinessClases.Inversion;
import Model.Managers.GestionApp;
import Model.Managers.GestionProyectos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "proyectos-servlet", value = "/proyectos-servlet")
public class ProyectosServlet extends HttpServlet {

    DAOManager daoManager;

    @Override
    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        GestionApp gestionApp = new GestionApp(daoManager);
        GestionProyectos gestionProyectos = gestionApp.getGestionProyectos();
        int id_inversion;

        HttpSession session = request.getSession();

        if (request.getParameter("codigo_inversion")!=null) {
            String username = request.getParameter("username");
            id_inversion = Integer.parseInt(request.getParameter("codigo_inversion"));
            Inversion inversion = gestionApp.cargarGestionInversion(daoManager, username).devuelveInversion(id_inversion);
            session.setAttribute("inversion", inversion);
        }
        session.setAttribute("invitadoHabilitado", gestionApp.devuelveModoInvitado());
        int codigo_proyecto = Integer.parseInt(request.getParameter("codigo_proyecto"));
        session.setAttribute("proyecto", gestionProyectos.devuelveProyectoPorCodigo(codigo_proyecto));
        redirect("/Pages/Proyect.jsp", request, response);
        session.removeAttribute("proyecto");
        session.removeAttribute("inversion");
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

