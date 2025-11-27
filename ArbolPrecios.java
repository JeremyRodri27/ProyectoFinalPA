<<<<<<< HEAD
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
=======
public class ArbolPrecios {
    private NodoArbol raiz;

    public void insertar(Producto p) {
        raiz = insertarRec(raiz, p);
    }

    private NodoArbol insertarRec(NodoArbol nodo, Producto p) {
        if (nodo == null) return new NodoArbol(p);
        if (p.getPrecio() < nodo.producto.getPrecio())
            nodo.izquierda = insertarRec(nodo.izquierda, p);
        else
            nodo.derecha = insertarRec(nodo.derecha, p);
        return nodo;
    }
}
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
