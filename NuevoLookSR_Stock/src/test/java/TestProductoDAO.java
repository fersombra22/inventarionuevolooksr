import com.nuevolooksr.stock.dao.ProductoDAO;
import com.nuevolooksr.stock.database.DatabaseConnection;
import com.nuevolooksr.stock.model.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestProductoDAO {
    public static void main(String[] args) {
        try {
            Connection conexion = DatabaseConnection.conectar();
            ProductoDAO productoDAO = new ProductoDAO(conexion);

            // Agregar un nuevo producto de prueba
            Producto nuevoProducto = new Producto(0, "Peluca Natural", "Peluca de cabello natural", 15000.0, 10, 5, 1);
            productoDAO.agregarProducto(nuevoProducto);
            System.out.println("✅ Producto agregado correctamente.");

            // Consultar todos los productos en la BD
            System.out.println("📦 Listado de productos:");
            List<Producto> productos = productoDAO.obtenerProductos();
            for (Producto p : productos) {
                System.out.println("ID: " + p.getId() + " - Nombre: " + p.getNombre() + " - Categoría ID: " + p.getIdCategoria());
            }

            // Cerrar conexión
            DatabaseConnection.cerrarConexion(conexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

