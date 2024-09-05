package Servlets;

import DAO.DAOManager;
import Model.BusinessClases.Inversor;
import Model.Managers.GestionApp;
import Model.Managers.GestionInversiones;
import Model.Managers.GestionUsuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import static Model.Biblioteca.AccountSettings.enviarCorreo;

@WebServlet(name = "email-servlet", value = "/email-servlet")
public class ComprobarEmailServlet extends HttpServlet {
    private DAOManager daoManager;
    private GestionApp gestionApp;
    public void init() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        gestionApp = new GestionApp(daoManager);
        GestionUsuarios gestionUsuariosAux = gestionApp.getGestionUsuarios();

        boolean isSigningIn = Boolean.parseBoolean(request.getParameter("isSigningIn"));

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        int generatedCode = Integer.parseInt(request.getParameter("generatedCode"));
        int enteredCode = Integer.parseInt(request.getParameter("enteredCode"));
        HttpSession session = request.getSession();
        HashMap<String, Boolean> errores = new HashMap<>();

        gestionUsuariosAux.buscarUsuarios("userName", username, "Usuario", "0", "always", daoManager);

        if (generatedCode == enteredCode) {
            boolean correcto;

            if (isSigningIn) {
                String name = request.getParameter("name");
                String password = request.getParameter("password");
                String tipo_usuario = request.getParameter("tipo-usuario");
                if (tipo_usuario.equals("Inversor")) correcto = gestionUsuariosAux.insertarUsuarioInversor(name, username, password, email, daoManager);
                else correcto = gestionUsuariosAux.insertarUsuarioGestor(name, username, password, email, daoManager);
                if (correcto) {
                    if (tipo_usuario.equals("Inversor")) session.setAttribute("class", "Inversor");
                    else session.setAttribute("class", "Gestor");

                    gestionUsuariosAux.actualizarUltimoInicioSesionUsuario(gestionUsuariosAux.devuelveUsuario(username).getId());
                    gestionUsuariosAux.guardarIniciosSesionUsuarios(gestionApp.recuperaUbicacion("UltimosIniciosSesionUbicacion"));
                    session.setAttribute("ultimoLogin", LocalDateTime.now());
                    session.removeAttribute("password");
                    session.removeAttribute("tipo-usuario");
                    session.removeAttribute("generatedCode");
                    session.removeAttribute("isSigningIn");

                    boolean invitadoHabilitado = gestionApp.devuelveModoInvitado();
                    session.setAttribute("invitadoHabilitado", invitadoHabilitado);
                    session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));

                    redirect("/Pages/Proyects.jsp", request, response);
                }
                else {
                    errores.put("noRegistrado", true);
                    request.setAttribute("errores", errores);
                    redirect("/Pages/SignIn.jsp", request, response);
                }
            }
            else {
                correcto = gestionUsuariosAux.modificarUsuario("email", email, username, daoManager);
                if (correcto) {
                    session.setAttribute("correcto", correcto);
                    session.setAttribute("mostrarMensaje", correcto? "Haz cambiado tu correo electrónico correctamente" : "No pudimos cambiar tu  correo electrónico, intenta de nuevo");
                    session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
                    session.removeAttribute("isSigningIn");
                    session.removeAttribute("generatedCode");
                }
                redirect("/Pages/Account.jsp", request, response);
            }

        }
        else {
            generatedCode = (int) (Math.random() * 99999) + 10000;
            session.setAttribute("generatedCode", generatedCode);
            enviarCorreo(email, "Correo de verificación", "Su código de verificación es: " + generatedCode);

            errores.put("wrongNumber", true);
            request.setAttribute("errores", errores);
            redirect("/Pages/EmailComprobation.jsp", request, response);
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