import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class visitante {

    Calendario calendario;
    int ID;
    String tipoDocumento, identificacion, nombres, residente, motivo,fecha;
    
    public visitante() {
        calendario = new Calendario();
    }
    
    public visitante(Calendario calendario, int ID, String tipoDocumento, String identificacion, 
            String nombres, String residente, String motivo, String fecha) {
        this.calendario = calendario;
        this.ID = ID;
        this.tipoDocumento = tipoDocumento;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.residente = residente;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public int getID() {
        return ID;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public String getResidente() {
        return residente;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setResidente(String residente) {
        this.residente = residente;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    public boolean agregar() {
        String sql = "INSERT INTO visitante VALUES(?, ?, ?, ?, ?, ?, ?);";
        AccesoBD.conectarBD();
        try {
            PreparedStatement ps = AccesoBD.conexion.prepareStatement(sql);

            ps.setInt(1, ID);
            ps.setString(2, tipoDocumento);
            ps.setString(3, identificacion);
            ps.setString(4, nombres);
            ps.setString(5, calendario.getFecha());
            ps.setString(6, residente);
            ps.setString(7, motivo);

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar el registro");
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(int id) {
        String sql = "UPDATE visitante SET Tipo_documento=?, Identificacion=?, Nombres=?, Fecha=?, Residente=?, "
                + "Motivo=? WHERE id = " + id + ";";
        AccesoBD.conectarBD();

        try {
            PreparedStatement ps = AccesoBD.conexion.prepareStatement(sql);

            ps.setString(1, tipoDocumento);
            ps.setString(2, identificacion);
            ps.setString(3, nombres);
            ps.setString(4, fecha);
            ps.setString(5, residente);
            ps.setString(6, motivo);

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar registro");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int identificador) {
        String sql = "DELETE FROM visitante WHERE id = " + identificador + ";";
        AccesoBD.conectarBD();

        try {
            PreparedStatement ps = AccesoBD.conexion.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar registro");
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet getvisitante() {
        String sql = "SELECT * FROM visitante;";
        AccesoBD.conectarBD();
        ResultSet resultado = AccesoBD.ejecutarSQLSelect(sql);
        return resultado;
    }

    
    public int obtenerID() {
        int valor = 0;
        String sql = "SELECT id FROM visitante;";
        AccesoBD.conectarBD();
        try {
            PreparedStatement ps = AccesoBD.conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                valor = rs.getRow();
            }
        } catch (SQLException ex) {
        }
        return valor;
    }

}
