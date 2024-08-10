package Model.BusinessClases;

public class Usuario {
    private int id;
    private String name;
    private String username;
    private String contrasenia;
    private String email;


    public Usuario(String name, String username, String contrasenia, String email) {
        this.name = name;
        this.username = username;
        this.contrasenia = contrasenia;
        this.email = email;
    }
    public Usuario(int id, String name, String username, String contrasenia, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.contrasenia = contrasenia;
        this.email = email;
    }
    public Usuario (Usuario u) {
        this.id = u.getId();
        this.name = u.getName();
        this.username = u.getUsername();
        this.contrasenia = u.getContrasenia();
        this.email = u.getEmail();
    }

    public void setName(String name) { this.name = name; }
    public void setUsername(String username) { this.username = username; }
    public void setContrasenia (String contrasenia) { this.contrasenia = contrasenia; }
    public void setEmail (String email) { this.email = email; }
    public void setId(int id) { this.id = id; }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getContrasenia () { return contrasenia; }
    public String getEmail () { return email; }


    public String toString () {
        return "Nombre de usuario: " + username + " | Nombre: " + name;
    }
}
