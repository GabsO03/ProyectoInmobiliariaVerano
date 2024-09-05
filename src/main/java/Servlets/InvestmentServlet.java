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
        Inversion inversionAux = null;
        GestionInversiones gestionInversionesAux = gestionApp.consigueGestionInversion(daoManager, username);
        boolean pagoRealizado =  gestionUsuariosAux.inversorPaga(username, cantidadEntrante, daoManager);
        boolean correcto = false;

        if (request.getParameter("codigo_inversion") != null) {
            inversionAux = gestionInversionesAux.devuelveInversion(gestionUsuariosAux.devuelveUsuario(username).getId(), proyecto, daoManager);
            session.setAttribute("inversion", inversionAux);
        }
        if (cantidadEntrante >= proyecto.getMinimo() && cantidadEntrante <= proyecto.getMaximo()) {
            if (pagoRealizado) {
                gestionProyectosAux.updateProyecto(codigo_proyecto, cantidadEntrante, daoManager, username);
                if (inversionAux != null) {
                    correcto = gestionInversionesAux.actualizarInversion(inversionAux.getCodigo(), inversionAux.getCantidadParticipada() + cantidadEntrante, daoManager);
                    inversionAux.setCantidadParticipada(inversionAux.getCantidadParticipada() + cantidadEntrante);
                    session.setAttribute("inversion", inversionAux);
                } else {
                    correcto = gestionInversionesAux.nuevaInversion(proyecto, cantidadEntrante, daoManager);
                    session.setAttribute("inversion", gestionInversionesAux.devuelveInversion(gestionUsuariosAux.devuelveUsuario(username).getId(), proyecto, daoManager));
                }
                gestionInversionesAux.cargarInversiones(gestionProyectosAux, daoManager);
                session.setAttribute("MisInversiones", gestionInversionesAux.getInversiones());
                session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
            }
        }
        proyecto = gestionProyectosAux.devuelveProyectoPorCodigo(codigo_proyecto, daoManager);
        session.setAttribute("proyecto", proyecto);
        session.setAttribute("correcto", correcto);
        session.setAttribute("mostrarMensaje", correcto? "Haz invertido correctamente" : "Hubo un error, asegurate que tienes saldo para invertir y que la cantidad que ingresaste es correcta");
        redirect("/Pages/Proyect.jsp", request, response);
        session.removeAttribute("proyecto");
        session.removeAttribute("mostrarMensaje");
        session.removeAttribute("correcto");
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
