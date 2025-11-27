public class NodoArbol {
    Producto producto;
    NodoArbol izquierda, derecha;
    
    public NodoArbol(Producto p) {
        producto = p;
        izquierda = derecha = null;
    }
}