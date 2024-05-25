package ClienteProducto;

import Producto.Producto;
import java.util.List;

public class ClienteProducto {
    private int id;
    private List<Producto> productos;

    public ClienteProducto(int id, List<Producto> productos) {
        this.id = id;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
