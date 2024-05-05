package Vista.dialogos;

import Modelo.Grafo;
import Modelo.Poblacion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrigenDestinoPanel extends JPanel {

    private final JComboBox<String> origenComboBox;
    private final JComboBox<String> destinoComboBox;

    public OrigenDestinoPanel(Grafo grafo) {
        HashMap<String, Poblacion> poblaciones = grafo.getPoblaciones();

        // Obtener nombres de poblaciones
        List<String> nombresPoblaciones =  new ArrayList<>(poblaciones.keySet());

        // Formatear nombres de poblaciones
        List<String> nombresPoblacionesFormateados = IntStream.range(0, nombresPoblaciones.size())
                .mapToObj(i -> (i + 1) + ". " + nombresPoblaciones.get(i))
                .toList();

        // Crear lista desplegable de origen
        origenComboBox = new JComboBox<>(nombresPoblacionesFormateados.toArray(new String[0]));
        JLabel origenLabel = new JLabel("Origen:");
        this.add(origenLabel);
        this.add(origenComboBox);

        // Crear lista desplegable de destino
        destinoComboBox = new JComboBox<>(nombresPoblacionesFormateados.toArray(new String[0]));
        JLabel destinoLabel = new JLabel("Destino:");
        this.add(destinoLabel);
        this.add(destinoComboBox);
    }

    public String getOrigenSeleccionado() {
        return (String) origenComboBox.getSelectedItem();
    }

    public String getDestinoSeleccionado() {
        return (String) destinoComboBox.getSelectedItem();
    }
}