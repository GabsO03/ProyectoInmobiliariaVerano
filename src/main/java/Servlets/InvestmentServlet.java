package Servlets;

import DAO.DAOManager;
import Model.BusinessClases.Inversion;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Proyecto;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
import Model.Managers.GestionProyectos;
import Model.Managers.GestionUsuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "investment-servlet", value = "/investment-servlet")
public class InvestmentServlet extends HttpServlet {
    DAOManager daoManager;
    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        GestionApp gestionApp = new GestionApp(daoManager);
        GestionUsuarios gestionUsuariosAux = gestionApp.getGestionUsuarios();
        GestionProyectos gestionProyectosAux = gestionApp.getGestionProyectos();
        HttpSession session = request.getSession();
        int codigo_proyecto = Integer.parseInt(request.getParameter("codigo_proyecto"));
        String username =  request.getParameter("username");
        double cantidadEntrante = Double.parseDouble(request.getParameter("cantidadEntrante"));

        Proyecto proyecto = gestionProyectosAux.devuelveProyectoPorCodigo(codigo_proyecto, daoManager);
        GestionInversiones gestionInversionesAux = gestionApp.consigueGestionInversion(daoManager, username);
        boolean pagoRealizado =  gestionUsuariosAux.inversorPaga(username, cantidadEntrante, daoManager);

        if (pagoRealizado) {
            gestionProyectosAux.updateProyecto(codigo_proyecto, cantidadEntrante, daoManager, username);
            if (request.getParameter("codigo_inversion")!=null) {
                Inversion inversionAux = gestionInversionesAux.devuelveInversion(gestionUsuariosAux.devuelveUsuario(username).getId(), proyecto, daoManager);
                gestionInversionesAux.actualizarInversion(inversionAux.getCodigo(), inversionAux.getCantidadParticipada() + cantidadEntrante, daoManager);
                session.setAttribute("inversion", inversionAux);
            }
            else {
                gestionInversionesAux.nuevaInversion(proyecto, cantidadEntrante, daoManager);
                session.setAttribute("inversion", gestionInversionesAux.getInversiones().getLast());
            }
            session.setAttribute("MisInversiones", gestionInversionesAux.getInversiones());
            session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
        }
        session.setAttribute("proyecto", proyecto);
        // TODO avisar si se pudo o no invertir
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
