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

        int id_gestor = gestionApp.getGestionUsuarios().devuelveUsuario(username).getId();
        gestionProyectos.buscarProyectos(id_gestor, "codigo", "asc", "nombre", nombre, daoManager);
        if (gestionProyectos.getArrayProyectos().isEmpty()) {
            boolean correcto = gestionProyectos.insertarProyecto(nombre, base64Image, descripcion, tipo, fechaACadena(fechaIni),
                    String.valueOf(fechaFin), cantidadNecesaria, username, id_gestor, daoManager);
            if (correcto){
                session.setAttribute("proyecto", gestionProyectos.getArrayProyectos().getLast());
                redirect("/Pages/Proyect.jsp", request, response);
                session.removeAttribute("proyecto");
            }
        }
        else {
            request.setAttribute("yaExisteNombre", true);
            redirect("/Pages/CreateEditProyect.jsp", request, response);
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
