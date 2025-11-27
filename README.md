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
