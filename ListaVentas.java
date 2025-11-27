<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

public class ListaVentas {
    private List<String> ventas = new ArrayList<>();

    public void agregar(String v) { ventas.add(v); }
    public int getTamaño() { return ventas.size(); }
    public List<String> getVentas() { return ventas; }
}
=======
public class ListaVentas {
    private NodoLista cabeza;
    private int tamaño = 0;

    public void agregar(String venta) {
        NodoLista nuevo = new NodoLista(venta);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
    }

    public int getTamaño() { return tamaño; }
}
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
