package Model.BusinessClases;

import java.text.DecimalFormat;
import java.time.LocalDate;

import static Model.Biblioteca.Fechas.*;

public class Proyecto {

    //ATRIBUTOS
    private int codigo;
    private String nombre;
    private String imagen;
    private String descripcion;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private double cantidadNecesaria;
    private double cantidadFinanciada;
    private boolean habilitado;


    //MÉTODOS

    //constructores
    public Proyecto(int codigo, String nombre, String imagen, String descripcion, String tipo, String fechaInicio, String fechaFin, double cantidadNecesaria, double cantidadFinanciada, boolean habilitado) {
        this.codigo = codigo;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadNecesaria = cantidadNecesaria;
        this.cantidadFinanciada = cantidadFinanciada;
        this.habilitado = habilitado;
    }

    public Proyecto(int codigo, String nombre, String imagen, String descripcion, String tipo, String fechaInicio, String fechaFin, double cantidadNecesaria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadNecesaria = cantidadNecesaria;
        this.cantidadFinanciada = 0;
        this.habilitado = cadenaAfecha(fechaInicio).equals(LocalDate.now());
    }

    //setters
    public void setNombre (String nombre) { this.nombre = nombre; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setDescripcion (String descripcion) { this.descripcion = descripcion; }
    public void setTipo (String tipo) { this.tipo = tipo; }
    public void setFechaInicio (String fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin (String fechaFin) { this.fechaFin = fechaFin; }
    public void setCantidadNecesaria (double cantidadNecesaria) { this.cantidadNecesaria = cantidadNecesaria; }
    public void setCantidadFinanciada (double cantidadFinanciada) {
        this.cantidadFinanciada = cantidadFinanciada;
        if (cantidadFinanciada==cantidadNecesaria) this.habilitado=false;
    }
    public void setHabilitado(boolean habilitado) { this.habilitado = habilitado; }
    //getters

    public int getCodigo () {
        return codigo;
    }
    public String getNombre () { return nombre; }
    public String getImagen() { return imagen; }
    public String getDescripcion () { return descripcion; }
    public String getTipo () { return tipo; }
    public String getFechaInicio () { return fechaInicio; }
    public String getFechaFin () { return fechaFin; }
    public double getCantidadNecesaria () { return cantidadNecesaria; }
    public double getCantidadFinanciada () { return cantidadFinanciada; }
    public String getPorcentaje () {
        DecimalFormat df = new DecimalFormat("##.00");
        String porcentajeFormateado = df.format((cantidadFinanciada/cantidadNecesaria)*100);
        return porcentajeFormateado;
    }
    public float getPlazo () {
        return periodo(LocalDate.now(), cadenaAfecha(fechaFin));
    }
    public double getMinimo () {
        if (cantidadNecesaria-cantidadFinanciada>=(cantidadNecesaria*0.001)) return cantidadNecesaria*0.001;
        return (cantidadNecesaria-cantidadFinanciada)*0.5;
    }
    public boolean isHabilitado() { return habilitado; }

    public String toString () {
        return "Código:" + codigo + "Nombre: " + nombre + "\nDescripcion: " + descripcion + "\nTipo: " + tipo + "\nCantidad Necesaria: " + cantidadNecesaria + "\nCantidad Financiada: " + cantidadFinanciada + "\nHabilitado:" + habilitado + "\n";
    }
}
