package Servlets;

import DAO.DAOManager;
import Model.BusinessClases.Inversion;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Proyecto;
import Model.BusinessClases.Usuario;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
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
    GestionApp gestionApp;
    HttpSession session;

    public void init(HttpServletRequest request) throws ServletException {
        daoManager = new DAOManager();
        daoManager.open();
        gestionApp = new GestionApp(daoManager);
        session = request.getSession();
        session.setAttribute("invitadoHabilitado", gestionApp.devuelveModoInvitado());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init(request);
        int codigo_proyecto = Integer.parseInt(request.getParameter("codigo_proyecto"));
        Proyecto proyecto = gestionApp.getGestionProyectos().devuelveProyectoPorCodigo(codigo_proyecto, daoManager);
        session.setAttribute("proyecto", proyecto);

        if (request.getParameter("username")!=null) {
            String username = request.getParameter("username");
            gestionApp.getGestionUsuarios().buscarUsuarios("userName", username, "Usuario", "0", "always", daoManager);
            if (gestionApp.getGestionUsuarios().devuelveUsuario(username).getClass().getSimpleName().equals("Inversor")) {
                Inversor inversor = (Inversor) gestionApp.getGestionUsuarios().devuelveUsuario(username);
                Inversion inversion = gestionApp.consigueGestionInversion(daoManager, username).devuelveInversion(inversor.getId(), proyecto, daoManager);
                session.setAttribute("inversion", inversion);
            }
        }

        redirect("/Pages/Proyect.jsp", request, response);
        session.removeAttribute("proyecto");
        session.removeAttribute("inversion");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init(request);
        String page = request.getParameter("page");
        String parametro = request.getParameter("parametro");
        String cadenaAux = request.getParameter("orderBy");
        String ordenarPor = cadenaAux.substring(0, cadenaAux.indexOf("_"));
        String direccion = cadenaAux.substring(cadenaAux.indexOf("_")+1);
        String atributo = request.getParameter("atributo");
        String username = null;

        int id_usuario = -1;
        if (request.getParameter("username")!=null) {
            username = request.getParameter("username");
            gestionApp.getGestionUsuarios().buscarUsuarios("userName", username, "Usuario", "0", "always", daoManager);
            id_usuario = gestionApp.getGestionUsuarios().devuelveUsuario(username).getId();
        }

        if (page.equals("/Pages/Proyects.jsp")) {
            GestionProyectos gestionProyectosAux = gestionApp.getGestionProyectos();
            gestionProyectosAux.buscarProyectos(id_usuario, ordenarPor, direccion, atributo, parametro, daoManager);
            session.setAttribute("resultados", gestionProyectosAux);
        }
        else {
            GestionInversiones gestionInversionesAux = gestionApp.consigueGestionInversion(daoManager, username);
            gestionInversionesAux.buscaInversiones(ordenarPor, direccion, atributo, parametro, gestionApp.getGestionProyectos(), daoManager);
            session.setAttribute("resultados", gestionInversionesAux.getInversiones());
        }
        redirect(page, request, response);
        session.removeAttribute("resultados");
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

