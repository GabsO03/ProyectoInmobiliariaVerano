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
        arrayProyectos = proyectoSQL.cargaProyectos(daoManager);
        lastCodigo = proyectoSQL.cargaUltimoCodigo(daoManager);
    }
    public void cargarProyectos (int id_gestor, DAOManager daoManager) {
        arrayProyectos = proyectoSQL.cargaProyectos(id_gestor, daoManager);
        lastCodigo = proyectoSQL.cargaUltimoCodigo(daoManager);
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
        Proyecto aux = new Proyecto(++lastCodigo, nombre, imagen, descripcion, tipo, fechaInicio, fechaFin, cantidadNecesaria);
        boolean correcto = proyectoSQL.insertar(aux, username, id_gestor, daoManager);
        if (correcto) {
            arrayProyectos.add(aux);
        }
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
        int pos = buscarProyecto(1, String.valueOf(codigo));
        Proyecto aux = arrayProyectos.get(pos);
        if (!aux.getNombre().equals(nombre)) {
            correcto = proyectoSQL.update("nombre", nombre, codigo, daoManager, username);
            if (correcto) aux.setNombre(nombre);
        }
        if ((aux.getImagen()==null) || (!aux.getImagen().equals(imagen))) {
            correcto = proyectoSQL.update("imagen", imagen, codigo, daoManager, username);
            if (correcto) aux.setImagen(imagen);
        }
        if (!aux.getDescripcion().equals(descripcion)) {
            correcto = proyectoSQL.update("descripcion", descripcion, codigo, daoManager, username);
            if (correcto) aux.setDescripcion(descripcion);
        }
        if (!aux.getTipo().equals(tipo)) {
            correcto = proyectoSQL.update("tipo", tipo, codigo, daoManager, username);
            if (correcto)aux.setTipo(tipo);
        }
        if (!aux.getFechaInicio().equals(fechaInicio)) {
            correcto = proyectoSQL.update("fechaInicio", fechaInicio, codigo, daoManager, username);
            if (correcto) aux.setFechaInicio(fechaInicio);
        }
        if (!aux.getFechaInicio().equals(fechaFin)) {
            correcto = proyectoSQL.update("fechaFin", fechaFin, codigo, daoManager, username);
            if (correcto) aux.setFechaInicio(fechaFin);
        }
        if (aux.getCantidadNecesaria()!=cantidadNecesaria) {
            correcto = proyectoSQL.update("cantidadNecesaria", String.valueOf(cantidadNecesaria), codigo, daoManager, username);
            if (correcto) {
                aux.setCantidadNecesaria(cantidadNecesaria);
            }
        }
        return correcto;
    }
    public boolean updateProyecto (int codigoProyecto, double cantidadEntrante, DAOManager daoManager, String inversorUsername) {
        int pos = buscarProyecto(1, String.valueOf(codigoProyecto));
        Proyecto aux = arrayProyectos.get(pos);
        boolean correcto = proyectoSQL.update("cantidadFinanciada", String.valueOf(cantidadEntrante + aux.getCantidadFinanciada()), codigoProyecto, daoManager, inversorUsername);
        if (correcto) {
            aux.setCantidadFinanciada(aux.getCantidadFinanciada()+cantidadEntrante);
            if (!aux.isHabilitado()) {
                proyectoSQL.update("habilitado", "0", codigoProyecto, daoManager, inversorUsername);
                System.out.println("En gestion proyectos: el proyecto ya no está habilitado");
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
        if (correcto) {
            int pos = buscarProyecto(1, String.valueOf(codigo));
            arrayProyectos.remove(pos);
        }
        return correcto;
    }


    /**
     * Funcion para buscar un proyecto dando el valor por parámetro
     * @param atributo como cadena
     * @param valor como cadena
     * @return un entero con la posicion donde se encuentra el proyecto o -1 si no lo ha encontrado
     */
    public int buscarProyecto(int atributo, String valor) {
        int codigo;
        valor = valor.toLowerCase();
        switch (atributo) {
            case 1 -> {
                codigo = Integer.parseInt(valor);
                for (int i = 0; i < arrayProyectos.size(); i++) {
                    if (arrayProyectos.get(i).getCodigo() == codigo) return i;
                }
            }
            case 2 -> {
                for (int i = 0; i < arrayProyectos.size(); i++) {
                    if (arrayProyectos.get(i).getNombre().equalsIgnoreCase(valor)) return i;
                }
            }
            case 3 -> {
                for (int i = 0; i < arrayProyectos.size(); i++) {
                    if (arrayProyectos.get(i).getDescripcion().equalsIgnoreCase(valor)) return i;
                }
            }
            case 4 -> {
                for (int i = 0; i < arrayProyectos.size(); i++) {
                    if (arrayProyectos.get(i).getTipo().equalsIgnoreCase(valor)) return i;
                }
            }
            case 5 -> {
                LocalDate aux = cadenaAfecha(valor);
                for (int i = 0; i < arrayProyectos.size(); i++) {
                    LocalDate fechaProyecto = cadenaAfecha(arrayProyectos.get(i).getFechaInicio());
                    if (fechaProyecto == aux) return i;
                }
            }
            case 6 -> {
                LocalDate aux = cadenaAfecha(valor);
                for (int i = 0; i < arrayProyectos.size(); i++) {
                    LocalDate fechaProyecto = cadenaAfecha(arrayProyectos.get(i).getFechaFin());
                    if (fechaProyecto == aux) return i;
                }
            }
            default -> System.out.println("Ese parámetro no existe.");
        }
        return -1;
    }

    /**
     * Funcion que devuelve un proyecto con un código en expecífico
     * @param codigo como entero
     * @return un objeto de la clase proyecto
     */
    public Proyecto devuelveProyectoPorCodigo(int codigo) {
        return arrayProyectos.get(buscarProyecto(1, String.valueOf(codigo)));
    }
    public Proyecto devuelveProyectoPos(int pos) {
        return arrayProyectos.get(pos);
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
