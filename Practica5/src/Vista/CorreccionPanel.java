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
    private final JButton okButton, corregirButton;
    private final Main prog;
    private final TreeMap<String, ArrayList<Candidato>> correcciones;
    ArrayList<String> palabrasIncorrectasOrdenadas = new ArrayList<>();
    private JLabel label;
    private JComboBox<String> comboBox;

    public CorreccionPanel(TreeMap<String, ArrayList<Candidato>> candidatas, Main prog, Vista vista) {
        this.setLayout(new BorderLayout());
        this.prog = prog;
        this.correcciones = candidatas;
        JPanel buttons = new JPanel();

        this.corregirButton = new JButton("Corregir");
        this.corregirButton.addActionListener(this);
        buttons.add(corregirButton);

        this.okButton = new JButton("OK");
        this.okButton.addActionListener(this);
        buttons.add(okButton);
        this.add(buttons, BorderLayout.SOUTH);

        // Añade las palabras incorrectas al arraylist
        for(String palabra : prog.getModelo().getTexto().getTextoOriginal().toLowerCase().split("(\\s|\\.|,|\n)+")) {
            if(correcciones.containsKey(palabra)){
                palabrasIncorrectasOrdenadas.add(palabra);
            }
        }

        // Muestra la primera palabra incorrecta
        if(!palabrasIncorrectasOrdenadas.isEmpty()) {
            mostrarCorreccion(palabrasIncorrectasOrdenadas.remove(0));
        } else {
            JOptionPane.showMessageDialog(this, "No hay palabras incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }

    }

    /**
     * Actualiza el panel del dialogo con la palabra incorrecta y sus correcciones
     * @param palabra Palabra incorrecta
     */
    private void mostrarCorreccion(String palabra) {
        label = new JLabel(palabra);
        comboBox = new JComboBox<>();

        // Añade las correcciones al combobox
        ArrayList<Candidato> candidatas = correcciones.get(palabra);
        for (Candidato candidata : candidatas) {
            comboBox.addItem(candidata.getPalabra());
        }


        this.add(label, BorderLayout.NORTH);
        this.add(comboBox, BorderLayout.CENTER);
        this.revalidate();
    }

    public void mostrar() {
        this.pack();
        this.setLocation(400, 400);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.corregirButton) {
            this.notificar();
            // Muestra la siguiente palabra incorrecta si hay
            if (!palabrasIncorrectasOrdenadas.isEmpty()) {
                this.remove(label);
                this.remove(comboBox);
                mostrarCorreccion(palabrasIncorrectasOrdenadas.remove(0));
            } else {
                JOptionPane.showMessageDialog(this, "No hay más palabras por corregir", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } else if (e.getSource() == this.okButton) {
            this.dispose();
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

    public boolean hayPalabrasIncorrectas() {
        return !palabrasIncorrectasOrdenadas.isEmpty();
    }
}