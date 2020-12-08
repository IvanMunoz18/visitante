import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class AccesoBD {

    public static Connection conexion = null;

    public AccesoBD() {

    }

    public static boolean conectarBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9380849","sql9380849","p7A4dIjzT7");
        } catch (SQLException ex) {
            System.out.println("Error al establecer conexion" + ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "2" + ex.getCause());
            return false;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver" + ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "2" + ex.getCause());
            return false;

        }
        return true;
    }


    public static boolean ejecutarSQLUpdate(String sql) {
        try {
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.println("Error en instruccion" + ex);
            return false;
        }
    }

    public static ResultSet ejecutarSQLSelect(String sql) {
        try {
            Statement sentencia = conexion.createStatement();
            return sentencia.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar instruccion" + ex);
            return null;
        }
    }

    public static void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexion");
        }
    }

}
