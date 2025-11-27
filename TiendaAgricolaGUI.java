import javax.swing.*;
import javax.swing.table.DefaultTableModel;
<<<<<<< HEAD
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiendaAgricolaGUI extends JFrame {
    private Map<String, Producto> inventario = new HashMap<>();
    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel labelEstado;
    private Factura facturaActual = null;
    private List<Factura> historial = new ArrayList<>();
    private ArbolPrecios arbol = new ArbolPrecios();
    private ListaVentas listaVentas = new ListaVentas();

    private JButton btnIniciar, btnAñadir, btnFinalizar, btnFactura;

    private class ItemCarrito {
        String producto;
        int cantidad;
        double subtotal, precio;
        ItemCarrito(String p, int c, double pr) {
            producto = p; cantidad = c; precio = pr; subtotal = c * pr;
        }
    }

    private class Factura {
        List<ItemCarrito> items = new ArrayList<>();
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        double total = 0;
        void agregar(ItemCarrito i) { items.add(i); total += i.subtotal; }
    }

    public TiendaAgricolaGUI() {
        setTitle("TIENDA AGRÍCOLA - EL CAMPO FELIZ");
        setSize(1200, 700);
=======
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

abstract class Producto {
    protected String nombre;
    protected double precio;
    protected int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

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
    public void reducirStock(int c) { stock -= c; }
}

class Fertilizante extends Producto { public Fertilizante(String n, double p, int s) { super(n, p, s); } }
class Insecticida extends Producto { public Insecticida(String n, double p, int s) { super(n, p, s); } }
class Semilla extends Producto { public Semilla(String n, double p, int s) { super(n, p, s); } }

// ====== ITEM DEL CARRITO ======
class ItemCarrito {
    String producto;
    int cantidad;
    double precioUnitario;
    double subtotal;

    public ItemCarrito(String producto, int cantidad, double precioUnitario) {
        this.producto = producto.toUpperCase();
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }
}

// ====== FACTURA COMPLETA ======
class Factura {
    List<ItemCarrito> items = new ArrayList<>();
    String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    double total = 0;

    public void agregarItem(ItemCarrito item) {
        items.add(item);
        total += item.subtotal;
    }
}

public class TiendaAgricolaGUI extends JFrame {
    private Map<String, Producto> inventario = new HashMap<>();
    private List<Factura> historial = new ArrayList<>();
    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel labelUltimaVenta;
    private Factura facturaActual = null;
    private JButton btnIniciar, btnAñadir, btnFinalizar, btnGenerarFactura;

    public TiendaAgricolaGUI() {
        setTitle("TIENDA AGRÍCOLA - EL CAMPO FELIZ");
        setSize(1200, 720);
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 255, 240));

        // TÍTULO
<<<<<<< HEAD
        JLabel titulo = new JLabel("TIENDA AGRÍCOLA - EL CAMPO FELIZ", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(new Color(34, 139, 34));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // TABLA
        modelo = new DefaultTableModel(new String[]{"Producto", "Precio", "Stock", "Estado"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(35);
        tabla.getTableHeader().setBackground(new Color(34, 139, 34));
        tabla.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        labelEstado = new JLabel("Listo para vender", SwingConstants.CENTER);
        labelEstado.setFont(new Font("Arial", Font.BOLD, 18));
        labelEstado.setForeground(new Color(0, 100, 0));
        add(labelEstado, BorderLayout.SOUTH);

        // BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelBotones.setBackground(new Color(245, 255, 245));

        panelBotones.add(crearBoton("AGREGAR PRODUCTO", new Color(76, 175, 80), e -> agregarProducto()));
        btnIniciar = crearBoton("INICIAR VENTA", new Color(30, 144, 255), e -> iniciarVenta());
        panelBotones.add(btnIniciar);

        btnAñadir = crearBoton("AÑADIR AL CARRITO", new Color(255, 193, 7), e -> añadirCarrito());
        btnAñadir.setEnabled(false);
        panelBotones.add(btnAñadir);

        btnFinalizar = crearBoton("FINALIZAR VENTA", new Color(255, 140, 0), e -> finalizarVenta());
        btnFinalizar.setEnabled(false);
        panelBotones.add(btnFinalizar);

        panelBotones.add(crearBoton("HISTORIAL", new Color(255, 193, 7), e -> mostrarHistorial()));
        panelBotones.add(crearBoton("ORDENAR POR PRECIO", new Color(148, 0, 211), e -> JOptionPane.showMessageDialog(this, "Árbol BST en desarrollo")));

        btnFactura = crearBoton("GENERAR FACTURA", new Color(220, 20, 60), e -> generarFactura());
        btnFactura.setEnabled(false);
        panelBotones.add(btnFactura);

        panelBotones.add(crearBoton("SALIR", new Color(139, 0, 0), e -> System.exit(0)));

        add(panelBotones, BorderLayout.EAST);
        setVisible(true);
    }

    private JButton crearBoton(String texto, Color color, ActionListener a) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.addActionListener(a);
        return b;
=======
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(34, 139, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel titulo = new JLabel("TIENDA AGRÍCOLA - EL CAMPO FELIZ", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // CENTRO: TABLA + ESTADO
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(Color.WHITE);
        centro.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 3));

        modelo = new DefaultTableModel(new String[]{"Producto", "Precio", "Stock", "Estado"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(40);
        tabla.setFont(new Font("Arial", Font.PLAIN, 16));
        tabla.getTableHeader().setBackground(new Color(34, 139, 34));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);

        labelUltimaVenta = new JLabel("Listo para vender", SwingConstants.CENTER);
        labelUltimaVenta.setFont(new Font("Arial", Font.BOLD, 17));
        labelUltimaVenta.setForeground(new Color(0, 100, 0));
        labelUltimaVenta.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        centro.add(labelUltimaVenta, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        // BOTONES (EXACTO COMO TU CAPTURA)
        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelBotones.setBackground(new Color(245, 255, 245));

        btnIniciar = crearBoton("INICIAR VENTA", new Color(30, 144, 255), e -> iniciarVenta());
        btnAñadir = crearBoton("AÑADIR AL CARRITO", new Color(255, 193, 7), e -> añadirAlCarrito());
        btnAñadir.setEnabled(false);
        btnFinalizar = crearBoton("FINALIZAR VENTA", new Color(255, 140, 0), e -> finalizarVenta());
        btnFinalizar.setEnabled(false);
        btnGenerarFactura = crearBoton("GENERAR FACTURA", new Color(220, 20, 60), e -> generarFactura());
        btnGenerarFactura.setEnabled(false);

        panelBotones.add(crearBoton("AGREGAR PRODUCTO", new Color(76, 175, 80), e -> agregarProducto()));
        panelBotones.add(btnIniciar);
        panelBotones.add(btnAñadir);
        panelBotones.add(btnFinalizar);
        panelBotones.add(crearBoton("HISTORIAL", new Color(255, 193, 7), e -> mostrarHistorial()));
        panelBotones.add(crearBoton("ORDENAR POR PRECIO", new Color(148, 0, 211), e -> mostrarOrdenPrecio()));
        panelBotones.add(btnGenerarFactura);
        panelBotones.add(crearBoton("SALIR", new Color(220, 20, 60), e -> System.exit(0)));

        add(panelBotones, BorderLayout.EAST);

        setVisible(true);
    }

    private JButton crearBoton(String texto, Color color, java.awt.event.ActionListener action) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.addActionListener(action);
        return btn;
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        inventario.values().forEach(p -> modelo.addRow(p.toRow()));
    }

<<<<<<< HEAD
    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        try {
            double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio:"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock:"));
            String tipo = JOptionPane.showInputDialog(this, "Tipo (Semilla/Fertilizante/Insecticida):").trim().toLowerCase();

            Producto p = switch (tipo) {
                case "fertilizante" -> new Fertilizante(nombre, precio, stock);
                case "insecticida" -> new Insecticida(nombre, precio, stock);
                default -> new Semilla(nombre, precio, stock);
            };

            inventario.put(nombre.toLowerCase(), p);
            arbol.insertar(p);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "¡Producto agregado!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos");
=======
    private void actualizarUltimaVenta(String texto) {
        labelUltimaVenta.setText(texto);
    }

    private void agregarProducto() {
        JTextField n = new JTextField(15), p = new JTextField(10), s = new JTextField(10);
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Semilla", "Fertilizante", "Insecticida"});
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 12));
        panel.add(new JLabel("Nombre:")); panel.add(n);
        panel.add(new JLabel("Precio:")); panel.add(p);
        panel.add(new JLabel("Stock:")); panel.add(s);
        panel.add(new JLabel("Tipo:")); panel.add(tipo);

        if (JOptionPane.showConfirmDialog(this, panel, "AGREGAR PRODUCTO", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String nombre = n.getText().trim();
                if (inventario.containsKey(nombre.toLowerCase())) {
                    JOptionPane.showMessageDialog(this, "Ya existe");
                    return;
                }
                double precio = Double.parseDouble(p.getText());
                int stock = Integer.parseInt(s.getText());
                Producto prod = switch ((String) tipo.getSelectedItem()) {
                    case "Fertilizante" -> new Fertilizante(nombre, precio, stock);
                    case "Insecticida" -> new Insecticida(nombre, precio, stock);
                    default -> new Semilla(nombre, precio, stock);
                };
                inventario.put(nombre.toLowerCase(), prod);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Producto agregado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
        }
    }

    private void iniciarVenta() {
<<<<<<< HEAD
=======
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos");
            return;
        }
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
        facturaActual = new Factura();
        btnAñadir.setEnabled(true);
        btnFinalizar.setEnabled(true);
        btnIniciar.setEnabled(false);
<<<<<<< HEAD
        labelEstado.setText("Venta iniciada - Añade productos");
    }

    private void añadirCarrito() {
        if (facturaActual == null) return;
        String nombre = JOptionPane.showInputDialog("Producto:");
        Producto p = inventario.get(nombre.toLowerCase());
        if (p == null || p.getStock() == 0) {
            JOptionPane.showMessageDialog(this, "No disponible");
            return;
        }
        int cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad (stock: " + p.getStock() + "):"));
        if (cant > p.getStock() || cant <= 0) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
            return;
        }
        facturaActual.agregar(new ItemCarrito(p.getNombre(), cant, p.getPrecio()));
        p.reducirStock(cant);
        actualizarTabla();
        labelEstado.setText("Añadido: " + cant + " x " + p.getNombre());
    }

    private void finalizarVenta() {
        if (facturaActual == null || facturaActual.items.isEmpty()) return;
        historial.add(facturaActual);
        listaVentas.agregar("Venta de " + facturaActual.items.size() + " productos - $" + facturaActual.total);
        btnAñadir.setEnabled(false);
        btnFinalizar.setEnabled(false);
        btnIniciar.setEnabled(true);
        btnFactura.setEnabled(true);
        labelEstado.setText("Venta finalizada - Total: $" + String.format("%.2f", facturaActual.total));
    }

    private void generarFactura() {
        if (facturaActual == null || facturaActual.items.isEmpty()) return;
        StringBuilder sb = new StringBuilder("=== FACTURA - EL CAMPO FELIZ ===\n");
        sb.append("Fecha: ").append(facturaActual.fecha).append("\n\n");
        for (ItemCarrito i : facturaActual.items) {
            sb.append(String.format("%dx %s → $%.2f\n", i.cantidad, i.producto, i.subtotal));
        }
        sb.append("\nTOTAL: $").append(String.format("%.2f", facturaActual.total));
        JOptionPane.showMessageDialog(this, sb.toString(), "Factura", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarHistorial() {
        JOptionPane.showMessageDialog(this, "Ventas realizadas: " + listaVentas.getTamaño());
=======
        btnGenerarFactura.setEnabled(false);
        actualizarUltimaVenta("Venta iniciada - Añade productos");
    }

    private void añadirAlCarrito() {
        if (facturaActual == null) return;

        String nombre = JOptionPane.showInputDialog("Producto:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        Producto prod = inventario.get(nombre.toLowerCase());
        if (prod == null) {
            JOptionPane.showMessageDialog(this, "No encontrado");
            return;
        }

        String cantStr = JOptionPane.showInputDialog("Cantidad (stock: " + prod.getStock() + "):");
        if (cantStr == null) return;
        try {
            int cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0 || cantidad > prod.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida");
                return;
            }

            ItemCarrito item = new ItemCarrito(prod.getNombre(), cantidad, prod.getPrecio());
            facturaActual.agregarItem(item);
            actualizarUltimaVenta("Añadido: " + item.cantidad + " x " + item.producto + " → $" + String.format("%.2f", item.subtotal));
            JOptionPane.showMessageDialog(this, "Añadido al carrito");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    private void finalizarVenta() {
        if (facturaActual == null || facturaActual.items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrito vacío");
            return;
        }

        // Aplicar al stock
        for (ItemCarrito item : facturaActual.items) {
            Producto p = inventario.get(item.producto.toLowerCase());
            if (p != null) p.reducirStock(item.cantidad);
        }

        historial.add(facturaActual);
        btnAñadir.setEnabled(false);
        btnFinalizar.setEnabled(false);
        btnIniciar.setEnabled(true);
        btnGenerarFactura.setEnabled(true);
        actualizarTabla();
        actualizarUltimaVenta("Venta finalizada - " + facturaActual.items.size() + " productos | Total: $" + String.format("%.2f", facturaActual.total));
    }

    private void generarFactura() {
        if (facturaActual == null || facturaActual.items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay factura");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        panel.setBackground(Color.WHITE);

        // Logo
        JLabel logo = new JLabel("EL CAMPO FELIZ", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 36));
        logo.setForeground(new Color(34, 139, 34));
        panel.add(logo, BorderLayout.NORTH);

        // Datos
        JLabel datos = new JLabel("<html><center><b>TIENDA AGRÍCOLA</b><br>"
                + "Av. Los Agricultores 123, Campo Verde<br>"
                + "Tel: (123) 456-7890 | NIT: 900123456-7<br>"
                + "Fecha: " + facturaActual.fecha + "</center></html>", SwingConstants.CENTER);
        datos.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(datos, BorderLayout.NORTH);

        // Tabla
        String[] cols = {"Producto", "Cant.", "P. Unit.", "Subtotal"};
        Object[][] data = new Object[facturaActual.items.size()][4];
        for (int i = 0; i < facturaActual.items.size(); i++) {
            ItemCarrito item = facturaActual.items.get(i);
            data[i] = new Object[]{item.producto, item.cantidad, "$" + String.format("%.2f", item.precioUnitario), "$" + String.format("%.2f", item.subtotal)};
        }
        JTable tablaFactura = new JTable(data, cols);
        tablaFactura.setFont(new Font("Arial", Font.PLAIN, 15));
        tablaFactura.getTableHeader().setBackground(new Color(34, 139, 34));
        tablaFactura.getTableHeader().setForeground(Color.WHITE);
        tablaFactura.setRowHeight(30);
        panel.add(new JScrollPane(tablaFactura), BorderLayout.CENTER);

        // Total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(new Color(240, 255, 240));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel total = new JLabel("TOTAL A PAGAR: $" + String.format("%.2f", facturaActual.total));
        total.setFont(new Font("Arial", Font.BOLD, 26));
        total.setForeground(new Color(0, 100, 0));
        totalPanel.add(total);
        panel.add(totalPanel, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(650, 700));

        JOptionPane.showMessageDialog(this, scroll, "FACTURA OFICIAL - EL CAMPO FELIZ", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarHistorial() {
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ventas");
            return;
        }
        StringBuilder sb = new StringBuilder("HISTORIAL DE VENTAS\n\n");
        for (int i = 0; i < historial.size(); i++) {
            Factura f = historial.get(i);
            sb.append((i+1) + ". " + f.items.size() + " productos → $" + String.format("%.2f", f.total) + " (" + f.fecha + ")\n");
        }
        JTextArea area = new JTextArea(sb.toString(), 15, 50);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "HISTORIAL", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarOrdenPrecio() {
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos");
            return;
        }
        StringBuilder sb = new StringBuilder("PRODUCTOS ORDENADOS POR PRECIO\n\n");
        inventario.values().stream()
            .sorted(Comparator.comparingDouble(Producto::getPrecio))
            .forEach(p -> sb.append("• ").append(p.getNombre()).append(" - $").append(String.format("%.2f", p.getPrecio())).append("\n"));
        JTextArea area = new JTextArea(sb.toString(), 15, 40);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "ORDEN POR PRECIO", JOptionPane.INFORMATION_MESSAGE);
>>>>>>> cf72dbc40d68b821e92b5f91da21e635fd7ce732
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TiendaAgricolaGUI::new);
    }
}