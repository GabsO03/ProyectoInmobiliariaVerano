package Servlets;

import DAO.DAOManager;
import Model.Managers.GestionApp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "send-investments-servlet", value = "/send-investments-servlet")

public class SendInvestmentsServlet extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOManager daoManager = new DAOManager();
        daoManager.open();
        GestionApp gestionApp = new GestionApp(daoManager);
        gestionApp.getGestionUsuarios().cargarUsuarios(daoManager);
        gestionApp.cargarGestionesInversiones(daoManager);
        boolean correcto = gestionApp.enviarInversiones();
        HttpSession session = request.getSession();
        session.setAttribute("correcto", correcto);
        session.setAttribute("mostrarMensaje", correcto? "Inversiones enviadas correctamente" : "Hubo un error, revisa si todos los correos electrónicos son correctos");
        redirect("/Pages/Proyects.jsp", request, response);
        session.removeAttribute("correcto");
        session.removeAttribute("mostrarMensaje");
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
