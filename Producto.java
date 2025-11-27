public abstract class Producto {
    protected String nombre;
    protected double precio;
    protected int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

<<<<<<< HEAD
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void reducirStock(int cantidad) {
        stock -= cantidad;
        if (stock < 0) stock = 0;
    }

    public Object[] toRow() {
        String estado = (stock > 0) ? "Disponible" : "Agotado";
        return new Object[]{ nombre, precio, stock, estado };
    }
}
=======
    public String getAlerta() {
        if (stock == 0) return "AGOTADO";
        if (stock <= 5) return "REPONER";
        return "Disponible";
    }

    public Object[] toRow() {
        return new Object[]{nombre, String.format("$%.2f", precio), stock, getAlerta()};
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void reducirStock(int cantidad) { stock -= cantidad; }
}
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
