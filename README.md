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
