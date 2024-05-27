package Vista;

import Main.Main;
import Modelo.Candidato;
import Notification.NotiEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

public class CorreccionPanel extends JDialog implements ActionListener {
    private final JButton okButton = new JButton("OK");
    private final Main prog;
    private final TreeMap<String, ArrayList<Candidato>> correcciones;
    ArrayList<String> palabrasIncorrectasOrdenadas = new ArrayList<>();
    private JLabel label;
    private JComboBox<String> comboBox;

    public CorreccionPanel(TreeMap<String, ArrayList<Candidato>> candidatas, Main prog) {
        this.setLayout(new BorderLayout());
        this.prog = prog;
        this.correcciones = candidatas;

        for(String palabra : prog.getModelo().getTexto().getTextoOriginal().split(" ")) {
            if(correcciones.containsKey(palabra)){
                palabrasIncorrectasOrdenadas.add(palabra);
            }
        }
        this.okButton.addActionListener(this);
        mostrarCorreccion(palabrasIncorrectasOrdenadas.remove(0));

    }

    private void mostrarCorreccion(String palabra) {
        label = new JLabel(palabra);

        comboBox = new JComboBox<>();
        ArrayList<Candidato> candidatas = correcciones.get(palabra);
        for (Candidato candidata : candidatas) {
            comboBox.addItem(candidata.getPalabra());
        }


        this.add(label, BorderLayout.NORTH);
        this.add(comboBox, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);
        mostrar();
    }

    public void mostrar() {
        this.pack();
        this.setLocation(400, 400);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.okButton) {
            this.notificar();
            this.getContentPane().removeAll();
            this.dispose();
            if (!palabrasIncorrectasOrdenadas.isEmpty()) {
                mostrarCorreccion(palabrasIncorrectasOrdenadas.remove(0));
            }
        }
    }

    public void notificar() {
        if (comboBox != null && label != null) {
            String palabraOriginal = label.getText();
            String palabraSeleccionada = (String) comboBox.getSelectedItem();
            String mensaje = palabraOriginal + "$" + palabraSeleccionada;
            this.prog.getModelo().notificar(NotiEnum.CORREGIR, mensaje);
        }
    }
}