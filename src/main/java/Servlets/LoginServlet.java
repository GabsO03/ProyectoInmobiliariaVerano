package Servlets;

import java.io.*;
import java.util.HashMap;

import DAO.DAOManager;
import Model.BusinessClases.Gestor;
import Model.BusinessClases.Inversor;
import Model.BusinessClases.Usuario;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
import Model.Managers.GestionUsuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "login-servlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    private DAOManager daoManager;
    private GestionApp gestionApp;
    public void init() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        gestionApp = new GestionApp(daoManager);
        GestionUsuarios gestionUsuariosAux = gestionApp.getGestionUsuarios();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HashMap<String, Boolean> errores = new HashMap<>();

        boolean logged = gestionUsuariosAux.correspondeUsuyContrasenia(username, password);
        if (logged) {
            HttpSession session = request.getSession();

            String classs = gestionUsuariosAux.averiguarClase(username);
            session.setAttribute("class", classs);
            boolean bloqueado = false;

            if (classs.equals("Inversor")) {
                Inversor aux = (Inversor) gestionUsuariosAux.devuelveUsuario(username);
                bloqueado = aux.isBloqueado();
            }
            else if (classs.equals("Gestor")) {
                Gestor aux = (Gestor) gestionUsuariosAux.devuelveUsuario(username);
                bloqueado = aux.isBloqueado();
            }
            if (bloqueado) {
                errores.put("isBloqueado", true);
                request.setAttribute("errores", errores);

                redirect("/Pages/Login.jsp", request, response);
            }
            else {
                Usuario aux = gestionUsuariosAux.devuelveUsuario(username);
                session.setAttribute("User", aux);
                session.setAttribute("UsersManager", gestionUsuariosAux);
                session.setAttribute("ultimoLogin", gestionUsuariosAux.recuperarUltimoInicioSesionUsuario(aux.getId()));
                gestionUsuariosAux.actualizarUltimoInicioSesionUsuario(aux.getId());
                gestionUsuariosAux.guardarIniciosSesionUsuarios(gestionApp.recuperaUbicacion("UltimosIniciosSesionUbicacion"));

                boolean invitadoHabilitado = gestionApp.devuelveModoInvitado();
                session.setAttribute("invitadoHabilitado", invitadoHabilitado);

                session.setAttribute("username", username);
                if (classs.equals("Inversor")) {
                    session.setAttribute("MisInversiones", gestionApp.consigueGestionInversion(daoManager, username).getInversiones());
                } else if (classs.equals("Admin"))
                    gestionApp.cargarGestionesInversiones(daoManager);

                redirect("/Pages/Proyects.jsp", request, response);
            }
        }
        else {
            errores.put("noCoincidence",true);
            request.setAttribute("errores", errores);
            redirect("/Pages/Login.jsp", request, response);
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