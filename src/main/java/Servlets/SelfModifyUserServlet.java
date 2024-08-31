package Servlets;

import DAO.DAOManager;
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
import java.util.HashMap;

import static Model.Biblioteca.AccountSettings.enviarCorreo;
import static Model.Biblioteca.FuncionesCadenas.contraseniaEsSegura;
import static Model.Biblioteca.FuncionesCadenas.usernameValido;

@WebServlet(name = "self-modify-user-servlet", value = "/self-modify-user-servlet")

public class SelfModifyUserServlet extends HttpServlet {
    public void init() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        DAOManager daoManager = new DAOManager();
        daoManager.open();
        GestionApp gestionApp = new GestionApp(daoManager);
        GestionUsuarios gestionUsuariosAux = gestionApp.getGestionUsuarios();
        HttpSession session = request.getSession();
        HashMap<String, Boolean> errores = new HashMap<>();

        String username = request.getParameter("username");
        String action = request.getParameter("action");
        String newValue, password;
        boolean correcto = false;

        gestionUsuariosAux.buscarUsuarios("userName", username, "Usuario", "0", "always", daoManager);

        switch (action) {
            case "change-name" -> {
                newValue = request.getParameter("newName");
                correcto = gestionUsuariosAux.modificarUsuario("name", newValue, username, daoManager);
                session.setAttribute("correcto", correcto);
                session.setAttribute("mostrarMensaje", correcto? "Haz cambiado tu nombre con éxito" : "Hubo un error al cambiar tu nombre");
                session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
                redirect("/Pages/Account.jsp", request, response);
                session.removeAttribute("correcto");
                session.removeAttribute("mostrarMensaje");
            }
            case "change-username" ->{
                newValue = request.getParameter("newUsername");
                password = request.getParameter("password");
                boolean existeNombreUsuario = gestionUsuariosAux.existeNombreUsuario(newValue, daoManager);
                if (!gestionUsuariosAux.correspondeUsuyContrasenia(username, password, daoManager)) errores.put("notLogged",true);
                if (!usernameValido(newValue)) errores.put("usernameInvalido", true);
                if (existeNombreUsuario) errores.put("yaExisteUserName", true);
                if (errores.isEmpty()) {
                    correcto = gestionUsuariosAux.modificarUsuario("username", newValue, username, daoManager);
                    session.setAttribute("username", newValue);
                    session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(newValue));
                    session.setAttribute("correcto", correcto);
                    session.setAttribute("mostrarMensaje", correcto? "Haz cambiado tu nombre de usuario" : "No pudimos cambiar tu nombre de usuario, intenta de nuevo");
                }
                else request.setAttribute("errores", errores);
                redirect("/Pages/Account.jsp", request, response);
                session.removeAttribute("correcto");
                session.removeAttribute("mostrarMensaje");
            }
            case "change-email" -> {
                newValue = request.getParameter("newEmail");
                password = request.getParameter("password");
                if (gestionUsuariosAux.correspondeUsuyContrasenia(username, password, daoManager)) {
                    session.setAttribute("isSigningIn", false);
                    session.setAttribute("email", newValue);
                    session.setAttribute("tipo-usuario", gestionUsuariosAux.devuelveUsuario(username).getClass());
                    int generatedCode = (int) (Math.random() * 99999) + 10000;
                    session.setAttribute("generatedCode", generatedCode);
                    enviarCorreo(newValue, "Correo de verificación", "Su código de verificación es: " + generatedCode);
                    redirect("/Pages/EmailComprobation.jsp", request, response);
                }
                else {
                    errores.put("notLogged", true);
                    request.setAttribute("errores", errores);
                    redirect("/Pages/Account.jsp", request, response);
                }
            }
            case "change-password" -> {
                String old_password = request.getParameter("old-password"), repeat_password = request.getParameter("repeat-password");
                newValue = request.getParameter("new-password");
                if (!gestionUsuariosAux.correspondeUsuyContrasenia(username, old_password, daoManager)) errores.put("notLogged",true);
                if (!contraseniaEsSegura(newValue)) errores.put("notSafe", true);
                if (!newValue.equals(repeat_password)) errores.put("noCoincidence",true);

                if (errores.isEmpty()) {
                    correcto = gestionUsuariosAux.modificarUsuario("password", newValue, username, daoManager);
                    session.setAttribute("correcto", correcto);
                    session.setAttribute("mostrarMensaje", correcto? "Haz cambiado tu contraseña correctamente" : "No pudimos cambiar tu contraseña, intenta de nuevo");
                    session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
                }
                else request.setAttribute("errores", errores);
                redirect("/Pages/Account.jsp", request, response);
                session.removeAttribute("correcto");
                session.removeAttribute("mostrarMensaje");
            }

            case "add-money" -> {
                double monto = Double.parseDouble(request.getParameter("monto"));
                gestionUsuariosAux.agregarMontoInversor(username, monto, daoManager);
                session.setAttribute("User", gestionUsuariosAux.devuelveUsuario(username));
                redirect("/Pages/CarteraVirtual.jsp", request, response);
            }
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
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
