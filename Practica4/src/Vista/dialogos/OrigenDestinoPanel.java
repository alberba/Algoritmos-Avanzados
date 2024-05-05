package Vista.dialogos;

import Modelo.Grafo;
import Modelo.Poblacion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class OrigenDestinoPanel extends JPanel {

    private final JComboBox<String> origenComboBox;
    private final JComboBox<String> destinoComboBox;

    public OrigenDestinoPanel(Grafo grafo) {
        HashMap<String, Poblacion> poblaciones = grafo.getPoblaciones();

        // Obtener nombres de poblaciones
        ArrayList<String> nombresPoblaciones = new ArrayList<>(poblaciones.keySet());

        // Crear lista desplegable de origen
        origenComboBox = new JComboBox<>(nombresPoblaciones.toArray(new String[0]));
        JLabel origenLabel = new JLabel("Origen:");
        this.add(origenLabel);
        this.add(origenComboBox);

        // Crear lista desplegable de destino
        destinoComboBox = new JComboBox<>(nombresPoblaciones.toArray(new String[0]));
        JLabel destinoLabel = new JLabel("Destino:");
        this.add(destinoLabel);
        this.add(destinoComboBox);

        // No se puede poner el mismo poblado como origen y destino a la vez
        origenComboBox.addActionListener(e -> {
            String origenSeleccionado = (String) origenComboBox.getSelectedItem();
            destinoComboBox.removeAllItems();
            for (String nombre : nombresPoblaciones) {
                if (!nombre.equals(origenSeleccionado)) {
                    destinoComboBox.addItem(nombre);
                }
            }
        });

        destinoComboBox.addActionListener(e -> {
            String destinoSeleccionado = (String) destinoComboBox.getSelectedItem();
            origenComboBox.removeAllItems();
            for (String nombre : nombresPoblaciones) {
                if (!nombre.equals(destinoSeleccionado)) {
                    origenComboBox.addItem(nombre);
                }
            }
        });
    }

    public String getOrigenSeleccionado() {
        return (String) origenComboBox.getSelectedItem();
    }

    public String getDestinoSeleccionado() {
        return (String) destinoComboBox.getSelectedItem();
    }
}