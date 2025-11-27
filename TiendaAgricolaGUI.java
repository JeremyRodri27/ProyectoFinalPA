import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 255, 240));

        // TÍTULO
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
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        inventario.values().forEach(p -> modelo.addRow(p.toRow()));
    }

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
        }
    }

    private void iniciarVenta() {
        facturaActual = new Factura();
        btnAñadir.setEnabled(true);
        btnFinalizar.setEnabled(true);
        btnIniciar.setEnabled(false);
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TiendaAgricolaGUI::new);
    }
}