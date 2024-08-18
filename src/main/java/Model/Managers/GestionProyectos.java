package Model.Managers;

import static Model.Biblioteca.Fechas.*;
import DAO.DAOManager;
import DAO.ProyectoSQL;
import Model.BusinessClases.Proyecto;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestionProyectos {
    private ArrayList<Proyecto> arrayProyectos;
    private ProyectoSQL proyectoSQL;
    private int lastCodigo;

    public GestionProyectos(String rutaFicheroLog) {
        arrayProyectos=new ArrayList<>();
        proyectoSQL = new ProyectoSQL(rutaFicheroLog);
    }

    public void cargarProyectos (DAOManager daoManager) {
        arrayProyectos = proyectoSQL.cargaProyectos("habilitado", "desc", daoManager);
        lastCodigo = proyectoSQL.cargaUltimoCodigo(daoManager);
    }
    public void cargarProyectos (int id_gestor, DAOManager daoManager) {
        arrayProyectos = proyectoSQL.cargaProyectos(id_gestor, "habilitado", "desc", daoManager);
        lastCodigo = proyectoSQL.cargaUltimoCodigo(daoManager);
    }

    public Proyecto devuelveProyectoPorCodigo(int codigo_proyecto, DAOManager daoManager) {
        return proyectoSQL.consigueProyecto(codigo_proyecto, daoManager);
    }
    public int getCantidadProyectos(){
        return arrayProyectos.size();
    }


    /**
     * Funcion para crear un nuevo proyecto
     * @param nombre como cadena
     * @param descripcion como cadena
     * @param tipo como cadena
     * @param fechaFin como una fecha
     * @param cantidadNecesaria como un double
     * @param daoManager como un objeto de la clase DAOManager
     * @return devuelve true si se ha podido crear el proyecto o false si no se ha podido
     */
    public boolean insertarProyecto(String nombre, String imagen, String descripcion, String tipo, String fechaInicio, String fechaFin, double cantidadNecesaria, String username, int id_gestor, DAOManager daoManager) {
        Proyecto aux = new Proyecto(nombre, imagen, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria);
        boolean correcto = proyectoSQL.insertar(aux, username, id_gestor, daoManager);
        return correcto;
    }


    /**
     * Funcion para poder modificar un proyecto ya creado
     * @param codigo como un entero
     * @param nombre como cadena
     * @param descripcion como cadena
     * @param tipo como cadena
     * @param fechaInicio como cadena
     * @param fechaFin como cadena
     * @param cantidadNecesaria como double
     * @param daoManager como un objeto de la clase DAOManager
     * @param username como una cadena
     * @return devuelve true si se ha podido modificar el proyecto o false si no se ha podido
     */
    public boolean updateProyecto(int codigo, String nombre, String imagen, String descripcion, String tipo, String fechaInicio, String fechaFin, double cantidadNecesaria, DAOManager daoManager, String username) {
        boolean correcto = false;
        Proyecto aux = devuelveProyectoPorCodigo(codigo, daoManager);
        if (!aux.getNombre().equals(nombre)) {
            correcto = proyectoSQL.update("nombre", nombre, codigo, daoManager, username);
        }
        if ((aux.getImagen()==null) || (!aux.getImagen().equals(imagen) && imagen!=null)) {
            correcto = proyectoSQL.update("imagen", imagen, codigo, daoManager, username);
        }
        if (!aux.getDescripcion().equals(descripcion)) {
            correcto = proyectoSQL.update("descripcion", descripcion, codigo, daoManager, username);
        }
        if (!aux.getTipo().equals(tipo)) {
            correcto = proyectoSQL.update("tipo", tipo, codigo, daoManager, username);
        }
        if (!aux.getFechaInicio().equals(fechaInicio)) {
            correcto = proyectoSQL.update("fechaInicio", fechaInicio, codigo, daoManager, username);
        }
        if (!aux.getFechaInicio().equals(fechaFin)) {
            correcto = proyectoSQL.update("fechaFin", fechaFin, codigo, daoManager, username);
        }
        if (aux.getCantidadNecesaria()!=cantidadNecesaria) {
            correcto = proyectoSQL.update("cantidadNecesaria", String.valueOf(cantidadNecesaria), codigo, daoManager, username);
        }
        return correcto;
    }
    public boolean updateProyecto (int codigoProyecto, double cantidadEntrante, DAOManager daoManager, String inversorUsername) {
        Proyecto aux = devuelveProyectoPorCodigo(codigoProyecto, daoManager);
        double cantidadTotal = cantidadEntrante + aux.getCantidadFinanciada();
        boolean correcto = proyectoSQL.update("cantidadFinanciada", String.valueOf(cantidadTotal), codigoProyecto, daoManager, inversorUsername);
        if (correcto) {
            if (aux.getCantidadNecesaria() == cantidadTotal) {
                proyectoSQL.update("habilitado", "0", codigoProyecto, daoManager, inversorUsername);
            }
        }
        return correcto;
    }

    /**
     * Funcion para eliminar un proyecto
     * @param codigo como entero
     * @param daoManager como un objeto de la clase DAOManager
     * @param userName como cadena
     * @return devuelve true si se ha podido eliminar el proyecto o false si no se ha podido
     */
    public boolean eliminarProyecto(int codigo, DAOManager daoManager, String userName) {
        boolean correcto;
        correcto = proyectoSQL.delete(codigo, daoManager, userName);
        return correcto;
    }


    /**
     * Funcion para buscar un proyecto dando el valor por parámetro
     * @param id como entero
     * @param orderBy como cadena
     * @param direccion como cadena
     * @param atributo como cadena
     * @param valor como cadena
     * @param daoManager como instancia de la clase DAOManager
     * @return un array con los proyectos que coincidan con la busqueda
     */
    public void buscarProyectos (int id, String orderBy, String direccion, String atributo, String valor, DAOManager daoManager) {
        if (id >= 0) arrayProyectos = proyectoSQL.buscaProyectos(id, orderBy, direccion, atributo, valor, daoManager);
        else arrayProyectos = proyectoSQL.buscaProyectos(orderBy, direccion, atributo, valor, daoManager);
    }

    /**
     * Funcion para ordenar los proyectos según el atributo recibido como parámetro
     * @param atributo como un entero
     * @param daoManager como una instancia de la clase DAOManager
     */
    public void ordenarProyectos (int atributo, DAOManager daoManager) {
        if (atributo>0&&atributo<9) arrayProyectos.clear();
        switch (atributo) {
            case 1 -> {
                arrayProyectos = proyectoSQL.cargaProyectos("fechaInicio", "desc", daoManager);
            }
            case 2 -> {
                arrayProyectos = proyectoSQL.cargaProyectos("fechaInicio", "asc", daoManager);
            }
            case 3 -> {
                arrayProyectos = proyectoSQL.cargaProyectos("fechaFin", "desc", daoManager);
            }
            case 4 -> {
                arrayProyectos = proyectoSQL.cargaProyectos("fechaFin", "asc", daoManager);
            }
            case 5 -> {
                arrayProyectos = proyectoSQL.cargaProyectos("cantidadNecesaria", "desc", daoManager);
            }
            case 6 -> {
                arrayProyectos =  proyectoSQL.cargaProyectos("cantidadNecesaria", "asc", daoManager);
            }
            case 7 -> {
                arrayProyectos =  proyectoSQL.cargaProyectos("cantidadFinanciada", "desc", daoManager);
            }
            case 8 -> {
                arrayProyectos =  proyectoSQL.cargaProyectos("cantidadFinanciada ", "asc", daoManager);
            }
            default -> System.out.println("Invalid response.");
        }
    }

    public ArrayList<Proyecto> getArrayProyectos() {
        return arrayProyectos;
    }

    public void setArrayProyectos(ArrayList<Proyecto> arrayProyectos) {
        this.arrayProyectos = arrayProyectos;
    }

    public ProyectoSQL getProyectoSQL() {
        return proyectoSQL;
    }

    public void setProyectoSQL(ProyectoSQL proyectoSQL) {
        this.proyectoSQL = proyectoSQL;
    }

    public int getLastCodigo() {
        return lastCodigo;
    }

    public void setLastCodigo(int lastCodigo) {
        this.lastCodigo = lastCodigo;
    }
}
