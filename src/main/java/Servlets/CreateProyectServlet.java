package Servlets;

import DAO.DAOManager;
import Model.Managers.GestionApp;
import Model.Managers.GestionProyectos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;

import static Model.Biblioteca.Fechas.*;

@WebServlet(name = "create-proyect-servlet", value = "/create-proyect-servlet")
@MultipartConfig(maxFileSize = 16177215)
public class CreateProyectServlet extends HttpServlet {
    DAOManager daoManager;
    public void init() throws ServletException {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        daoManager = new DAOManager();
        daoManager.open();
        GestionApp gestionApp = new GestionApp(daoManager);
        GestionProyectos gestionProyectos = gestionApp.getGestionProyectos();
        HttpSession session = request.getSession();
        InputStream inputStream = null;
        HashMap<String, String> errorres = new HashMap<>();

        String username = request.getParameter("username");
        Part filePart = request.getPart("imagen");
        String nombre = request.getParameter("nombre-proyecto");
        String tipo = request.getParameter("tipo-proyecto");
        String descripcion = request.getParameter("descripcion");
        LocalDate fechaIni = cadenaAfecha(request.getParameter("fechaInicio"));
        LocalDate fechaFin = cadenaAfecha(request.getParameter("fechaFin"));
        double cantidadNecesaria = Double.parseDouble(request.getParameter("cantidadNecesaria"));

        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }
        byte[] imageBytes = inputStream.readAllBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        gestionApp.getGestionUsuarios().buscarUsuarios("userName", username, "Usuario", "0", "always", daoManager);
        int id_gestor = gestionApp.getGestionUsuarios().devuelveUsuario(username).getId();
        gestionProyectos.buscarProyectos(-1, "codigo", "asc", "nombre", nombre, daoManager);

        if (!gestionProyectos.getArrayProyectos().isEmpty()) errorres.put("yaExisteNombre", "Lo sentimos, ya existe un proyecto con ese nombre.");
        if (!esPosterior(fechaFin, fechaIni)) errorres.put("fechaFinInvalida", "La fecha de finalización debe ser posterior a la fecha de inicio.");

        boolean correcto = false;
        if (errorres.isEmpty()) {
            correcto = gestionProyectos.insertarProyecto(nombre, base64Image, descripcion, tipo, fechaACadena(fechaIni),
                    String.valueOf(fechaFin), cantidadNecesaria, username, id_gestor, daoManager);
            if (correcto){
                gestionProyectos.buscarProyectos(-1, "codigo", "asc", "nombre", nombre, daoManager);
                session.setAttribute("proyecto", gestionProyectos.getArrayProyectos().getFirst());
                session.setAttribute("correcto", correcto);
                session.setAttribute("mostrarMensaje", correcto? "Proyecto " + nombre + " creado correctamente." : "Hubo un error al crear tu proyecto, intentalo de nuevo.");
                redirect("/Pages/Proyect.jsp", request, response);
                session.removeAttribute("proyecto");
                session.removeAttribute("mostrarMensaje");
                session.removeAttribute("correcto");
            }
        }
        else {
            session.setAttribute("correcto", correcto);
            if (errorres.get("fechaFinInvalida")!=null) session.setAttribute("mostrarMensaje", errorres.get("fechaFinInvalida"));
            if (errorres.get("yaExisteNombre")!=null) session.setAttribute("mostrarMensaje", errorres.get("yaExisteNombre"));
            redirect("/Pages/CreateEditProyect.jsp", request, response);
            session.removeAttribute("mostrarMensaje");
            session.removeAttribute("correcto");
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
