import com.nuevolooksr.stock.utils.DatabaseConnection;


import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        try {
            Connection conexion = DatabaseConnection.conectar();
            if (conexion != null) {
                System.out.println("✅ Conexión exitosa a la base de datos.");
                DatabaseConnection.cerrarConexion();
            } else {
                System.out.println("⚠️ Error al conectar con la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}