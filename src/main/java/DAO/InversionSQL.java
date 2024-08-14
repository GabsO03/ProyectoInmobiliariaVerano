package DAO;

import Model.BusinessClases.Inversion;
import Model.Managers.GestionProyectos;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InversionSQL {
    private String rutaFicheroLog;
    private PreparedStatement ps;

    public InversionSQL (String rutaFicheroLog) {
        this.rutaFicheroLog = rutaFicheroLog;
    }

    /**
     * Funcion para escribir el log
     * @param tipoTarea como una cadena
     * @param usuario como una cadena
     */
    public void escribirLog(String tipoTarea, String usuario) {
        try {
            //File log = new File(String.valueOf(this.getClass().getClassLoader().getResourceAsStream(rutaFicheroLog)));
            //TODO ARREGLAR RUTA
            String log = "C:\\Users\\pollo\\Programación T1DA_ejs\\Intellij\\ProyectoInmobiliaria\\src\\main\\resources\\FicherosDatosSistema\\log.txt";
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(log, true));
            bw.write(LocalDateTime.now()+ ";" + usuario + ";" + tipoTarea + "\n");
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero Log");
        } catch (IOException e) {
            System.out.println("No se puede leer el archivo");
        }
    }

    /**
     * Funcion para cargar en nuestro sistema las inversiones que se encuentran en la base de datos
     * @param id_inversor como una cadena
     * @param gestionProyectos como un objeto de la clase GestionProyectos
     * @param daoManager como una instancia de la clase DAOManager
     * @return un array dinámico con todas las inversiones
     */
    public ArrayList<Inversion> cargarInversiones(int id_inversor, GestionProyectos gestionProyectos, DAOManager daoManager) {
        ArrayList<Inversion> inversiones = new ArrayList<>();
        String select = "SELECT * FROM inversion where `id_inversor` = ?";
        try {
            ps = daoManager.getConn().prepareStatement(select);
            ps.setInt(1, id_inversor);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String fechaInicio = resultSet.getString("fechaInicio");
                    String unltimaActualizacion = resultSet.getString("ultimaActualizacion");
                    double cantidadParticipada = resultSet.getDouble("cantidadParticipada");
                    int codigoProyecto = resultSet.getInt("codigoProyecto");
                    Inversion aux = new Inversion(codigo, gestionProyectos.devuelveProyectoPorCodigo(codigoProyecto), cantidadParticipada, fechaInicio, unltimaActualizacion);
                    inversiones.add(aux);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inversiones;
    }

    /**
     * Funcion para insertar una inversion en la base de datos
     * @param inversion como un objeto de la clase inversion
     * @param userNameInversor como una cadena
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido insertar la inversion correctamente o false si no
     */
    public boolean insertar(Inversion inversion, String userNameInversor, int id, DAOManager daoManager) {
        String insert = "INSERT INTO `inversion`(`fechaInicio`, `ultimaActualizacion`, `cantidadParticipada`, `codigoProyecto`, `id_inversor`) VALUES (?,?,?,?,?)";
        try {
            ps = daoManager.getConn().prepareStatement(insert);
            ps.setString(1, inversion.getFechaInicio());
            ps.setString(2, inversion.getUltimaActualizacion());
            ps.setDouble(3, inversion.getCantidadParticipada());
            ps.setInt(4, inversion.getProyecto().getCodigo());
            ps.setInt(5, id);
            boolean okay = ps.executeUpdate()>0;
            if (okay) escribirLog("Nueva inversión realizada", userNameInversor);
            return okay;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funcion para actualizar una inversion en la base de datos
     * @param userName como cadena
     * @param atributoCambiar como cadena
     * @param nuevoValor como cadena
     * @param codigo como entero
     * @param daoManager como una instancia de la clase DAOManager
     * @return true si se ha podido modificar la inversion correctamente o false si no
     */
    public boolean update(String userName, String atributoCambiar, String nuevoValor, int codigo, DAOManager daoManager) {
        String update = "UPDATE `inversion` SET `" + atributoCambiar + "` = ? WHERE `inversion`.`codigo` = ?;";
        try {
            ps = daoManager.getConn().prepareStatement(update);
            ps.setString(1, nuevoValor);
            ps.setInt(2, codigo);
            boolean alright = ps.executeUpdate()>0;
            if (alright) escribirLog("Inversión actualizada - " + codigo, userName);
            return alright;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
