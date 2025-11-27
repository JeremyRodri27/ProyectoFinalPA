import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// ========================================
// 1. CLASE BASE: Producto (Herencia)
// ========================================
abstract class Producto {
    protected String nombre;
    protected double precio;
    protected int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getAlertaStock() {
        if (stock == 0) return "AGOTADO";
        if (stock <= 5) return "REPONER YA";
        return "Disponible";
    }

    public String toString() {
        return String.format("%-25s $%-8.2f %-10d [%s]", nombre, precio, stock, getAlertaStock());
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void reducirStock(int cantidad) { this.stock -= cantidad; }
}

// ========================================
// 2. HERENCIA: Tipos de productos
// ========================================
class Fertilizante extends Producto {
    public Fertilizante(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
    }
    @Override
    public String getAlertaStock() {
        if (stock <= 10) return "¡Reponer fertilizante!";
        return super.getAlertaStock();
    }
}

class Insecticida extends Producto {
    public Insecticida(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
    }
    @Override
    public String getAlertaStock() {
        if (stock <= 3) return "¡URGENTE!";
        return super.getAlertaStock();
    }
}

class Semilla extends Producto {
    public Semilla(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
    }
}

// ========================================
// 3. NODO PARA LISTA ENLAZADA
// ========================================
class NodoLista {
    String venta;
    NodoLista siguiente;
    public NodoLista(String venta) {
        this.venta = venta;
        this.siguiente = null;
    }
}

// ========================================
// 4. LISTA ENLAZADA: Historial de ventas
// ========================================
class ListaVentas {
    private NodoLista cabeza;
    private int tamaño = 0;

    public void agregar(String venta) {
        NodoLista nuevo = new NodoLista(venta);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
    }

    public void mostrar() {
        System.out.println("\n--- HISTORIAL DE VENTAS (" + tamaño + ") ---");
        if (cabeza == null) {
            System.out.println("Sin ventas aún.");
        } else {
            NodoLista actual = cabeza;
            int i = 1;
            while (actual != null) {
                System.out.println(i++ + ". " + actual.venta);
                actual = actual.siguiente;
            }
        }
        System.out.println("-----------------------------------\n");
    }
}

// ========================================
// 5. NODO PARA ÁRBOL BST
// ========================================
class NodoArbol {
    Producto producto;
    NodoArbol izquierda, derecha;
    public NodoArbol(Producto producto) {
        this.producto = producto;
        this.izquierda = this.derecha = null;
    }
}

// ========================================
// 6. ÁRBOL BST: Ordenado por precio
// ========================================
class ArbolPrecios {
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

    public void mostrarInOrden() {
        System.out.println("\n--- PRODUCTOS ORDENADOS POR PRECIO ---");
        if (raiz == null) {
            System.out.println("Sin productos.");
        } else {
            mostrarInOrdenRec(raiz);
        }
        System.out.println("--------------------------------------\n");
    }

    private void mostrarInOrdenRec(NodoArbol nodo) {
        if (nodo != null) {
            mostrarInOrdenRec(nodo.izquierda);
            System.out.println(nodo.producto);
            mostrarInOrdenRec(nodo.derecha);
        }
    }
}

// ========================================
// 7. TIENDA PRINCIPAL - VACÍA AL INICIO
// ========================================
public class TiendaAgricola {
    private Map<String, Producto> inventario = new HashMap<>();
    private ListaVentas historial = new ListaVentas();
    private ArbolPrecios arbolPrecios = new ArbolPrecios();

    // CONSTRUCTOR VACÍO → TÚ AGREGAS TODO
    public TiendaAgricola() {
        System.out.println("TIENDA VACÍA. Usa opción 5 para agregar productos.");
    }

    private void agregarProducto(Producto p) {
        String clave = p.getNombre().toLowerCase();
        if (inventario.containsKey(clave)) {
            System.out.println("ERROR: Ya existe '" + p.getNombre() + "'");
            return;
        }
        inventario.put(clave, p);
        arbolPrecios.insertar(p);
        System.out.println("AGREGADO: " + p.getNombre());
    }

    public void vender(String nombre, int cantidad) {
        Producto p = inventario.get(nombre.toLowerCase());
        if (p == null) {
            System.out.println("ERROR: Producto '" + nombre + "' no existe.");
            return;
        }
        if (p.getStock() < cantidad) {
            System.out.printf("STOCK INSUFICIENTE: Solo hay %d de '%s'%n", p.getStock(), p.getNombre());
            return;
        }

        p.reducirStock(cantidad);
        double total = cantidad * p.getPrecio();
        String venta = String.format("%d x %s → $%.2f", cantidad, p.getNombre(), total);
        historial.agregar(venta);

        System.out.printf("VENDIDO: %s%nTotal: $%.2f%nQuedan: %d%n", venta, total, p.getStock());
        System.out.println(p.getAlertaStock());
    }

    public void mostrarCatalogo() {
        System.out.println("\n=== CATÁLOGO ===");
        if (inventario.isEmpty()) {
            System.out.println("SIN PRODUCTOS. Usa opción 5.");
        } else {
            System.out.println("Producto                  Precio    Stock     Estado");
            System.out.println("----------------------------------------------------------");
            for (Producto p : inventario.values()) {
                System.out.println(p);
            }
        }
        System.out.println("==========================================================\n");
    }

    // MENÚ
    public static void main(String[] args) {
        TiendaAgricola tienda = new TiendaAgricola();
        Scanner sc = new Scanner(System.in);
        int op;

        do {
            System.out.println("\nTIENDA AGRÍCOLA - EL CAMPO FELIZ");
            System.out.println("1. Ver catálogo");
            System.out.println("2. Vender producto");
            System.out.println("3. Ver historial de ventas");
            System.out.println("4. Ver productos por precio (BST)");
            System.out.println("5. Agregar producto");
            System.out.println("6. Salir");
            System.out.print("Opción: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> tienda.mostrarCatalogo();
                case 2 -> {
                    System.out.print("Producto: ");
                    String prod = sc.nextLine();
                    System.out.print("Cantidad: ");
                    int cant = sc.nextInt();
                    tienda.vender(prod, cant);
                }
                case 3 -> tienda.historial.mostrar();
                case 4 -> tienda.arbolPrecios.mostrarInOrden();
                case 5 -> {
                    System.out.print("Nombre: ");
                    String n = sc.nextLine();
                    System.out.print("Precio: ");
                    double pr = sc.nextDouble();
                    System.out.print("Stock: ");
                    int st = sc.nextInt();
                    System.out.print("Tipo (1=Semilla, 2=Fertilizante, 3=Insecticida): ");
                    int tipo = sc.nextInt();
                    Producto p = switch (tipo) {
                        case 2 -> new Fertilizante(n, pr, st);
                        case 3 -> new Insecticida(n, pr, st);
                        default -> new Semilla(n, pr, st);
                    };
                    tienda.agregarProducto(p);
                }
                case 6 -> System.out.println("¡Gracias!");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 6);
        sc.close();
    }
}