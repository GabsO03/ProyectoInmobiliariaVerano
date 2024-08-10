package DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager implements Serializable {
    private Connection conn;
    private final String URL;
    private final String USER;
    private final String PASS;
    public DAOManager() {
        this.conn = null;
        this.URL = "jdbc:mysql://127.0.0.1:3306/Proyecto_verano";
        this.USER = "root";
        this.PASS = "root";
    }

    /**
     * Funcion para abrir la conexion con la base de datos
     * @throws Exception
     */
    public void open() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funcion para obterner la conexion con la bade de datos
     * @return la propia conexion
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Funcion para cerrar la conexion con la base de datos
     * @throws Exception
     */
    public void close() throws Exception {
        if(conn!=null) conn.close();
    }
}

