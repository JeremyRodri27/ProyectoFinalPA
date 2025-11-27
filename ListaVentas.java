import java.util.ArrayList;
import java.util.List;

public class ListaVentas {
    private List<String> ventas = new ArrayList<>();

    public void agregar(String v) { ventas.add(v); }
    public int getTamaño() { return ventas.size(); }
    public List<String> getVentas() { return ventas; }
}
