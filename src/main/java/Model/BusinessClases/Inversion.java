package Model.BusinessClases;

import Model.Interfaces.Inversible;

import java.time.LocalDate;

import static Model.Biblioteca.Colores.*;
import static Model.Biblioteca.Fechas.*;

public class Inversion implements Inversible {

    //ATRIBUTOS
    private int codigo;
    private Proyecto proyecto;
    private double cantidadParticipada;
    private String fechaInicio;
    private String ultimaActualizacion;
    //GETTERS
    public double getCantidadParticipada() {
        return cantidadParticipada;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getUltimaActualizacion() {
        return ultimaActualizacion;
    }
    public Proyecto getProyecto() {
        return proyecto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCantidadParticipada(double cantidadParticipada) {
        this.cantidadParticipada = cantidadParticipada;
    }

    //MÉTODOS
    public Inversion (Proyecto proyecto, double cantidadEntrante) {
        this.proyecto = proyecto;
        this.cantidadParticipada = cantidadEntrante;
        fechaInicio = fechaACadena(LocalDate.now());
        ultimaActualizacion = fechaACadena(LocalDate.now());
    }

    public Inversion(int codigo, Proyecto proyecto, double cantidadParticipada, String fechaInicio, String ultimaActualizacion) {
        this.codigo = codigo;
        this.proyecto = proyecto;
        this.cantidadParticipada = cantidadParticipada;
        this.fechaInicio = fechaInicio;
        this.ultimaActualizacion = ultimaActualizacion;
    }
    //Métodos

    public String toString (){
        return RED+"ID: " + codigo +
                "\n : " + proyecto.getNombre() + GREEN+
                "\nTipo: "+ proyecto.getTipo() + CYAN +
                "\nCantidad con la que ha participado: " + cantidadParticipada +
                "\nFecha de la primera inversión: " + fechaInicio +
                "\nFecha de la última inversión: " + ultimaActualizacion + RESET;
    }

    /**
     * Funcion para aumentar la inversion de un inversor
     * @param cantidadEntrante como un double
     */
    @Override
    public void aumentaInversion(double cantidadEntrante) {
        proyecto.setCantidadFinanciada(proyecto.getCantidadFinanciada() + cantidadEntrante);
        cantidadParticipada += cantidadEntrante;
        this.ultimaActualizacion = fechaACadena(LocalDate.now());
    }
    public double getPorcentajeParticipado() {
        return (cantidadParticipada/proyecto.getCantidadNecesaria())*100;
    }
}
