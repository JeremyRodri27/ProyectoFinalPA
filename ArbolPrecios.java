import java.util.ArrayList;
import java.util.List;

public class ArbolPrecios {
    // Por ahora solo guardamos para futuras funcionalidades (ordenar, buscar, etc.)
    private List<Producto> lista = new ArrayList<>();

    public void insertar(Producto p) {
        lista.add(p);
    }

    public List<Producto> getTodos() { return lista; }
}
