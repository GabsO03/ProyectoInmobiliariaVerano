package Model.BusinessClases;

import Model.Interfaces.Bloqueable;

public class Inversor  extends Usuario implements Bloqueable {
    private double saldo;
    private boolean bloqueado;


    public Inversor(String nombre, String usuario, String contrasenia, String email) {
        super(nombre, usuario, contrasenia, email);
        this.saldo = 0;
        bloqueado = false;
    }

    public Inversor(int id, String nombre, String username, String contrasenia, String email, double saldo, boolean bloqueado) {
        super(id, nombre, username, contrasenia, email);
        this.saldo = saldo;
        this.bloqueado = bloqueado;
    }


    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Funcion para comprobar si el inversor puede invertir en un proyecto con cierta cantidad o no
     * @param cantidad como double
     * @return true si si puede invertir o false si no
     */
    public boolean puedePagarInversor (double cantidad) {
        return saldo >= cantidad;
    }

    public double getSaldo() {
        return saldo;
    }
    public boolean isBloqueado() { return bloqueado; }

    @Override
    public void bloqueo() {
        this.bloqueado = true;
    }
    @Override
    public void desbloqueo() {
        this.bloqueado = false;
    }
    public String toString () {
        return super.toString() + (bloqueado?"Bloqueado":"");
    }

}
