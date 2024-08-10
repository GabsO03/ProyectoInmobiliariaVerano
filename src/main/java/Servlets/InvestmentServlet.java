package Servlets;

import DAO.DAOManager;
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
        boolean yaInvirtio = request.getParameter("codigo_inversion")!=null;
        int codigo_proyecto = Integer.parseInt(request.getParameter("codigo_proyecto"));
        int codigo_inversion = 0;
        if (yaInvirtio) codigo_inversion = Integer.parseInt(request.getParameter("codigo_inversion"));
        String username =  request.getParameter("username");
        double cantidadEntrante = Double.parseDouble(request.getParameter("cantidadEntrante"));
        GestionInversiones gestionInversionesAux = gestionApp.cargarGestionInversion(daoManager, username);
        boolean pagoRealizado =  gestionUsuariosAux.inversorPaga(username, cantidadEntrante, daoManager);
        int pos = gestionInversionesAux.buscaInversionPorProyecto(codigo_proyecto);
        if (pagoRealizado) {
            gestionProyectosAux.updateProyecto(codigo_proyecto, cantidadEntrante, daoManager, username);
            if (yaInvirtio) gestionInversionesAux.actualizarInversion(pos, cantidadEntrante, daoManager);
            else {
                gestionInversionesAux.nuevaInversion(gestionProyectosAux.devuelveProyectoPorCodigo(codigo_proyecto), cantidadEntrante, daoManager);
                session.setAttribute("inversion", gestionInversionesAux.getInversiones().getLast());
            }
            session.setAttribute("MisInversiones", gestionApp.cargarGestionInversion(daoManager, username));
            session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
        }
        session.setAttribute("proyecto", gestionProyectosAux.devuelveProyectoPorCodigo(codigo_proyecto));
        // TODO avisar si se pudo o no invertir
        if (yaInvirtio) session.setAttribute("inversion", gestionInversionesAux.devuelveInversion(codigo_inversion));
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
