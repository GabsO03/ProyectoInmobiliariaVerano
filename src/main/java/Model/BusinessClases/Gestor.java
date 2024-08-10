package Model.BusinessClases;

import Model.Interfaces.Bloqueable;

public class Gestor extends Usuario implements Bloqueable {
    private boolean bloqueado;

    public Gestor(String nombre, String usuario, String contrasenia, String email) {
        super(nombre, usuario, contrasenia, email);
        bloqueado = false;
    }
    public Gestor(int id, String nombre, String usuario, String contrasenia, String email, boolean bloqueado) {
        super(id, nombre, usuario, contrasenia, email);
        this.bloqueado = bloqueado;
    }
    public boolean isBloqueado() { return bloqueado; }
    public String toString () {
        return super.toString() + (bloqueado?"Bloqueado":"");
    }

    @Override
    public void bloqueo() {
        this.bloqueado = true;
    }
    @Override
    public void desbloqueo() {
        this.bloqueado = false;
    }

}
