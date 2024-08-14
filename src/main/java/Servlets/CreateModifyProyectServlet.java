package Servlets;

import DAO.DAOManager;
import Model.Managers.GestionApp;
import Model.Managers.GestionProyectos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "create-modify-proyect-servlet", value = "/create-modify-proyect-servlet")
public class CreateModifyProyectServlet extends HttpServlet {
    DAOManager daoManager;
    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        GestionApp gestionApp = new GestionApp(daoManager);
        GestionProyectos gestionProyectos = gestionApp.getGestionProyectos();
        HttpSession session = request.getSession();

        if (request.getParameter("codigo_proyecto")!=null) {
            String username = request.getParameter("username");
            int codigo_proyecto = Integer.parseInt(request.getParameter("codigo_proyecto"));

            session.setAttribute("username", username);
            session.setAttribute("proyecto", gestionProyectos.devuelveProyectoPorCodigo(codigo_proyecto));
            session.setAttribute("modifyProyect", true);
        }
        else session.setAttribute("modifyProyect", false);
        redirect("/Pages/CreateEditProyect.jsp", request, response);
        session.removeAttribute("proyecto");
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
