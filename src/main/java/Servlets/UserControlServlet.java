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

import java.io.IOException;

@WebServlet(name = "user-control-servlet", value = "/user-control-servlet")
public class UserControlServlet extends HttpServlet {

    DAOManager daoManager;
    GestionApp gestionApp;
    GestionUsuarios gestionUsuariosAux;
    HttpSession session;
    public void init(HttpServletRequest request) {
        daoManager = new DAOManager();
        daoManager.open();
        gestionApp = new GestionApp(daoManager);
        session = request.getSession();
        gestionUsuariosAux = gestionApp.getGestionUsuarios();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init(request);
        String username = request.getParameter("username");
        gestionUsuariosAux.cargarUsuarios(daoManager);
        gestionUsuariosAux.bloquearDesbloquearUsuario(username, daoManager);
        session.setAttribute("UsersManager", gestionUsuariosAux);
        redirect("/Pages/Users.jsp", request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init(request);
        String parameter =  request.getParameter("parameter");
        String tipo_nombre = request.getParameter("tipo_nombre");
        String tipo = request.getParameter("tipo");
        String estado = request.getParameter("estado");
        String lastLogin = request.getParameter("lastLogin");
        gestionUsuariosAux.buscarUsuarios(tipo_nombre, parameter, tipo, estado, lastLogin, daoManager);
        session.setAttribute("UsersManager", gestionUsuariosAux);
        session.setAttribute("busqueda", true);
        redirect("/Pages/Users.jsp", request, response);
        session.removeAttribute("busqueda");
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
